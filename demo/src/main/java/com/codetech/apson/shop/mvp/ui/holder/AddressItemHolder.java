package com.codetech.apson.shop.mvp.ui.holder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.presenter.AddressPresenter;
import com.codetech.apson.shop.mvp.ui.activity.AddressActivity;
import com.codetech.apson.shop.mvp.ui.activity.AddressChangeActivity;
import com.codetech.apson.shop.mvp.ui.adapter.AddressAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class AddressItemHolder extends BaseHolder<Address> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.edit_address)
    ImageView edit_address;
    @BindView(R.id.is_default)
    ImageView is_default;
    AddressAdapter addressAdapter ;
    public AddressItemHolder(View itemView, AddressAdapter addressAdapter) {

        super(itemView);
        this.addressAdapter = addressAdapter;
    }

    @Override
    public void setData(Address data, int position) {
        address.setText(data.getProvince_addr()+data.getCity_addr()+data.getDistrict_addr()+data.getStreet());
        number.setText(data.getTelephone());
        name.setText(data.getConsignee().length()>3?data.getConsignee().substring(0,3):data.getConsignee());
        edit_address.setTag(""+position);
        if(data.getDefaultAddr() .equals("0")){
            is_default.setImageResource(android.R.drawable.checkbox_off_background);
        }
        else is_default.setImageResource(android.R.drawable.checkbox_on_background);

        edit_address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(itemView.getContext(), AddressChangeActivity.class);
                intent.putExtra("addressid",data.getAddress_id());
                itemView.getContext().startActivity(intent);
            }
        });
        is_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = itemView.getContext().getSharedPreferences("userinfo",0);
                String userID = sp.getString("userID",null);
                String userKEY = sp.getString("userKEY",null);
                ((AddressActivity)itemView.getContext()).showLoading();
                AddressPresenter  addressPresenter= new AddressPresenter(ArtUtils.obtainAppComponentFromContext(itemView.getContext()));
                addressPresenter.setDefaultAddress(Message.obtain( ((AddressActivity)itemView.getContext()),new Object[]{userID,userKEY,data.getAddress_id()}));

//                addressPresenter.getrealAddresss(Message.obtain( ((AddressActivity)itemView.getContext()),new Object[]{userID,userKEY})).subscribeOn(Schedulers.io())
//                        .map(new Function<ArrayList<Address>, ArrayList<Address>>() {
//                            @Override
//                            public ArrayList<Address> apply(ArrayList<Address> addresses) {
//
//                                return addresses;
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new ErrorHandleSubscriber<ArrayList<Address>>(ArtUtils.obtainAppComponentFromContext(itemView.getContext()).rxErrorHandler()) {
//                            @Override
//                            public void onNext(ArrayList<Address> addresses) {
//                                addressAdapter.update(addresses);//刷新被删除后的结果
//                                ((AddressActivity)itemView.getContext()).hideLoading();
//                            }
//                        });
            }
        });

    }
}
