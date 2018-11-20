package com.codetech.apson.shop.mvp.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.Area;
import com.codetech.apson.shop.mvp.model.entity.MyArea;
import com.codetech.apson.shop.mvp.ui.adapter.DialogAddressListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.art.base.DefaultAdapter;

import me.jessyan.art.utils.ArtUtils;

public class AddressDialog extends Dialog  {
    protected ItemListener listener;
    @BindView(R.id.address_list)
    RecyclerView address_list;
    @BindView(R.id.provice)
    TextView province;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.distict)
    TextView distict;
    @BindView(R.id.close)
    ImageView close;
    Context context;
    private List<MyArea> province_list;
    private List<MyArea> city_list= new ArrayList<>();
    private List<MyArea> district_list = new ArrayList<>();
    public AddressDialog(@NonNull Context context) {
        super(context);
    }

    public AddressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address_layout);
        ButterKnife.bind(this);

        Gson gson  = new Gson();
        Area area = gson.fromJson( getContext().getResources().getString(R.string.area),Area.class);
        province_list = MyUtils.getArea(area);
        province.setText("请选择");
        province.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ArtUtils.configRecyclerView(address_list,linearLayoutManager);


        DialogAddressListAdapter adapter = new DialogAddressListAdapter(province_list);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<MyArea>() {
            @Override
            public void onItemClick(View view, int viewType, MyArea data, int position) {

                SharedPreferences sp =  getContext().getSharedPreferences("temaddress",0);
                SharedPreferences.Editor editor =sp.edit();
                if(data.getFlag().equals("province")){//如果现在点击的是省份 那么 获取对应的城市 并且渲染
                    province.setTextColor(getContext().getResources().getColor(R.color.black));
                    province.setText(data.getName());
                    city.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));//城市高亮
                    city.setVisibility(View.VISIBLE);//城市tab出现
                    city.setText("请选择");
                    city_list.clear();//清空之前的数据
                    distict.setVisibility(View.GONE);//区tab 消失

                    for(Area.ResBean.SubBeanX city : area.getRes().get(position).getSub()){//获取对应的城市列表
                        city_list.add(new MyArea(city.getName(),city.getCode(),"city"));
                    }
                    editor.putString(data.getFlag()+"Code",data.getCode());
                    editor.putString(data.getFlag()+"Name",data.getName());

                    editor.apply();
                    adapter.update(city_list);//刷新
                }

                if(data.getFlag().equals("city")){//如果现在点击的是城市 那么 获取对应的区 并且渲染
                    city.setTextColor(getContext().getResources().getColor(R.color.black));
                    city.setText(data.getName());
                    distict.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));//区高亮
                    distict.setVisibility(View.VISIBLE);//区tab出现
                    distict.setText("请选择");
                    district_list.clear();//清空之前的数据
                   //通过城市名获取选择的是第几个省份
                    for(int i = 0; i < province_list.size();i++){

                        if(province_list.get(i).getName() . equals(province.getText().toString())){
                            //通过省份位置 城市位置 获取区域
                            for(Area.ResBean.SubBeanX.SubBean district:area.getRes().get(i).getSub().get(position).getSub()){
                                district_list.add(new MyArea(district.getName(),district.getCode(),"district"));
                            }
                        }
                    }

                    editor.putString(data.getFlag()+"Code",data.getCode());
                    editor.putString(data.getFlag()+"Name",data.getName());

                    editor.apply();
                    adapter.update(district_list);//刷新
                }
                if(data.getFlag().equals("district")){
                    editor.putString(data.getFlag()+"Code",data.getCode());
                    editor.putString(data.getFlag()+"Name",data.getName());
                    editor.apply();
                    distict.setText(data.getName());
                    Intent intent = new Intent();
                    intent.setAction("top.codetech.apson.dialogdismiss");
                    getContext().sendBroadcast(intent);
                    AddressDialog.this.dismiss();

                }



            }
        });
        province.setOnClickListener(new View.OnClickListener() {//点击省份tab
            @Override
            public void onClick(View view) {
                //获取所有省份 渲染
                province_list.clear();
                city.setTextColor(getContext().getResources().getColor(R.color.black));
                distict.setTextColor(getContext().getResources().getColor(R.color.black));
                province.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                province_list = MyUtils.getArea(area);
                adapter.update(province_list);
            }
        });
        city.setOnClickListener(new View.OnClickListener() {//点击城市tab
            @Override
            public void onClick(View view) {
                //获取所有城市 渲染
                city_list.clear();
                province.setTextColor(getContext().getResources().getColor(R.color.black));
                distict.setTextColor(getContext().getResources().getColor(R.color.black));
                city.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                //获得对应省份对应的位置

                for(int i = 0 ; i < province_list.size();i++){
                    if(province_list.get(i).getName().equals(province.getText().toString())){
                        for(Area.ResBean.SubBeanX city : area.getRes().get(i).getSub()){//获取对应的城市列表
                            city_list.add(new MyArea(city.getName(),city.getCode(),"city"));
                        }
                    }
                }

                adapter.update(city_list);
            }
        });
        distict.setOnClickListener(new View.OnClickListener() {//点击区域tab
            @Override
            public void onClick(View view) {
                district_list.clear();
                province.setTextColor(getContext().getResources().getColor(R.color.black));
                city.setTextColor(getContext().getResources().getColor(R.color.black));
                distict.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                for(int i = 0 ; i < province_list.size();i++){ //获得对应省份对应的位置
                    if(province_list.get(i).getName().equals(province.getText().toString())){
                        for(Area.ResBean.SubBeanX city_list : area.getRes().get(i).getSub()){//获取对应的城市列表
                            if(city_list.getName().equals(city.getText().toString())){
                                for(Area.ResBean.SubBeanX.SubBean district_args:city_list.getSub()){//获取对应的区域列表
                                    district_list.add(new MyArea(district_args.getName(),district_args.getCode(),"district"));
                                }
                                adapter.update(district_list);
                                return;
                            }
                        }
                    }
                }
            }
        });

        address_list.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressDialog.this.dismiss();
            }
        });
    }


    protected AddressDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.height= getWindow().getWindowManager().getDefaultDisplay().getHeight()*3/5;
        layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
    public interface ItemListener{
           void onclick(View v);
    }
    public void setItemListener(ItemListener listener){
        this.listener = listener;
    }


}
