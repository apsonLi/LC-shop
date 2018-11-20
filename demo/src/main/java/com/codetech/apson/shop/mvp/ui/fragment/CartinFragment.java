package com.codetech.apson.shop.mvp.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.model.entity.CartinItem;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.activity.PayActivity;
import com.codetech.apson.shop.mvp.ui.activity.loginActivity;
import com.codetech.apson.shop.mvp.ui.adapter.CartinAdapter;
import com.codetech.apson.shop.mvp.ui.view.FullScreenLayout;
import com.codetech.apson.shop.mvp.ui.view.dialog.CenterDialog;
import com.codetech.apson.shop.mvp.ui.view.dialog.Goods_dialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.base.DefaultAdapter;

import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartinFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartinFragment extends BaseFragment<MainPresenter> implements IView {
    @Nullable @BindView(R.id.cartin_refresh)
    SwipeRefreshLayout cartin_refresh;
    @Nullable@BindView(R.id.cartin_recycle)
    RecyclerView cartin_recycle;
    @Nullable@BindView(R.id.login)
    Button login;
    @Nullable@BindView(R.id.fullscreen)
    FullScreenLayout fullScreenLayout;
    @Nullable@BindView(R.id.select_all)
    ImageView select_all;
    @Nullable@BindView(R.id.cartin_sum)
    TextView cartin_sum;
    @Nullable@BindView(R.id.cartin_tip)
    ImageView cartin_tip;
    @Nullable@BindView(R.id.cartin_pay_layout)
    LinearLayout cartin_pay_layout;
    @Nullable@BindView(R.id.pay)
    Button pay;
    private RefreshReciver refreshReciver;

    @Nullable
    public LinearLayout getCartin_pay_layout() {
        return cartin_pay_layout;
    }

    @Nullable
    public ImageView getSelect_all() {
        return select_all;
    }

    @Nullable
    public TextView getCartin_sum() {
        return cartin_sum;
    }

    @Nullable
    public ImageView getCartin_tip() {
        return cartin_tip;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CartinAdapter cartinAdapter;
    private ArrayList<CartinItem> cartin_list;
    private Object[] objects;
    private CenterDialog centerDialog;
    private  int isSelectAll;
    private Goods_dialog goods_dialog;

    public CartinFragment() {
        // Required empty public constructor
    }
    public CenterDialog getDialog(){
        if(centerDialog!=null)
        return centerDialog;
        return null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartinFragment newInstance(String param1, String param2) {
        CartinFragment fragment = new CartinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getActivity()==null)throw new NullPointerException("getactivity can not be null");

        return getActivity().getSharedPreferences("userinfo",0).getString("userID",null)==null ||
                getActivity().getSharedPreferences("userinfo",0).getString("userKEY",null)==null

                ? inflater.inflate(R.layout.layout_not_login, container, false)
                : inflater.inflate(R.layout.fragment_cartin, container, false);

//        return inflater.inflate(R.layout.fragment_cartin, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(getActivity()==null)return;
        if(getContext()==null)return;
        if(cartin_refresh!=null && select_all !=null && cartin_sum !=null){
            refreshReciver = new RefreshReciver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.codetech.apson.refresh");

            getActivity().registerReceiver(refreshReciver,intentFilter);
            objects = new Object[]{getActivity().getSharedPreferences("userinfo",0).getString("userID",null),getActivity().getSharedPreferences("userinfo",0).getString("userKEY",null)};
            mPresenter.getCartin(Message.obtain(this, objects));
            cartin_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.getCartin(Message.obtain(CartinFragment.this, objects));
                }
            });

            centerDialog = new CenterDialog(getContext(),R.style.dialog_custom);
            centerDialog.setCanceledOnTouchOutside(true);// 点击Dialog外部消失
            goods_dialog = new Goods_dialog(getContext());
            goods_dialog.setCanceledOnTouchOutside(true);// 点击Dialog外部消失
            select_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoading();
                   //全选逻辑 0代表没有全选
                    if((Integer)select_all.getTag() == 0) isSelectAll = 1;
                    else isSelectAll = 0;
                    mPresenter.refreshcartin(objects[0]+"",objects[1]+"",objects[0]+"","2","","","","","0",isSelectAll==1?"-1":"-2","")
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {

                                    hideLoading();
                                }
                            })
                            .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(ArtUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler()) {
                                @Override
                                public void onNext(List<CartinItem> list) {
                                    select_all.setImageResource( MyUtils.isCartrinSelectAll(list,select_all) ? R.mipmap.cartin_select : R.mipmap.cartin_not_select);
                                    cartin_sum.setText(String.format(getResources().getString(R.string.sum),MyUtils.sumCartin(list)));
                                    cartinAdapter.update(list);
                                }
                            });
                }
            });
        }
        if(login !=null){
            login.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"跳转登录",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getActivity(),loginActivity.class);
                    startActivity(intent);

                }
            });
        }
        if(pay!=null)
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoading();
                    Intent intent = new Intent(CartinFragment.this.getContext(), PayActivity.class);
                    Bundle bundle = new Bundle();
                    mPresenter.getRealCartin(objects[0]+"",objects[1]+"")
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                           .concatMap(new Function<List<CartinItem>,Observable<CartinItem>>() {
                               @Override
                               public Observable<CartinItem> apply(List<CartinItem> list) throws Exception {
                                    return Observable.fromIterable(list);
                               }
                           })
                            .filter(new Predicate<CartinItem>() {
                                @Override
                                public boolean test(CartinItem cartinItem) throws Exception {
                                    if(cartinItem.getStatus().equals("1"))return true;
                                    return false;
                                }
                            })
                            .toList()
                            .map(new Function<List<CartinItem>, List<CartinItem>>() {
                                @Override
                                public List<CartinItem> apply(List<CartinItem> list) {
                                    return list;
                                }
                            })
                           .flatMapObservable(new Function<List<CartinItem>, Observable<ArrayList<Address>>>() {
                               @Override
                               public Observable<ArrayList<Address>> apply(List<CartinItem> list) throws Exception {
                                   ArrayList<CartinItem> arrayList = new ArrayList<>();
                                   arrayList.addAll(list);
                                   bundle.putParcelableArrayList("cartin_ready",arrayList);


                                   return   mPresenter.getrealAddresss(Message.obtain(CartinFragment.this,objects));
                               }
                           })
                            .concatMap(new Function<ArrayList<Address>, Observable<Address>>() {
                                @Override
                                public Observable<Address> apply(ArrayList<Address> addresses) throws Exception {
                                    return Observable.fromIterable(addresses);
                                }
                            })
                            .filter(new Predicate<Address>() {
                                @Override
                                public boolean test(Address address) throws Exception {
                                    Log.e("address_filter",address.getConsignee()+address.getDefaultAddr());
                                    return address.getDefaultAddr().equals("1");

                                }
                            })
                            .toList()
                            .flatMapObservable(new Function<List<Address>, Observable<List<Address>>>() {
                                @Override
                                public Observable<List<Address>> apply(List<Address> addresses) throws Exception {
                                    if(addresses.size()==0)return null;
                                    for (Address address: addresses
                                         ) {
                                        Log.e("address_each",address.getConsignee()+address.getDefaultAddr());
                                    }
                                    bundle.putParcelable("default_address",addresses.get(0));
                                    return Observable.just(addresses);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ErrorHandleSubscriber<List<Address>>(ArtUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler()) {
                             @Override
                                public void onNext(List<Address> addresses) {
                                 if(addresses!=null){
                                     intent.putExtras(bundle);

//                                startActivity(intent);
                                startActivityForResult(intent,1);

                            }
                        }
                    });
                }
            });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                ArtUtils.snackbarText("取消支付");
                hideLoading();
                break;
            case 2:
              refresh();
                break;
        }
    }

    public  Goods_dialog getNumberDialog(){
        if(goods_dialog==null)return null;

        return goods_dialog;
    }
    public void setSum(List<CartinItem> list){
        if(cartin_sum!=null)
            cartin_sum.setText(String.format(getResources().getString(R.string.sum),MyUtils.sumCartin(list)));
    }
    public void setSelect_all(List<CartinItem> list){
        if(select_all !=null)
            select_all.setImageResource( MyUtils.isCartrinSelectAll(list,select_all) ? R.mipmap.cartin_select : R.mipmap.cartin_not_select);

    }
    public void refresh(){
        mPresenter.getCartin(Message.obtain(CartinFragment.this, objects));
    }

    @Nullable
    @Override
    public MainPresenter obtainPresenter() {
        return new MainPresenter(ArtUtils.obtainAppComponentFromContext(getContext()));
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (fullScreenLayout != null && cartin_refresh != null) {
        fullScreenLayout.setVisibility(View.VISIBLE);
        cartin_refresh.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (fullScreenLayout != null && cartin_refresh != null) {
            fullScreenLayout.setVisibility(View.GONE);
            cartin_refresh.setRefreshing(false);
        }
    }
    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void handleMessage(@NonNull Message message) {

        if(message.what ==6){
            ArtUtils.snackbarText("请重新登录");
        }
            if(message.what == 5){

                    cartin_list = message.getData().getParcelableArrayList("cartin_list");
                    if(getContext()!=null && select_all !=null && cartin_sum !=null) {
                        cartin_sum.setText(String.format(getResources().getString(R.string.sum),MyUtils.sumCartin(cartin_list)));
                        select_all.setImageResource( MyUtils.isCartrinSelectAll(cartin_list,select_all) ? R.mipmap.cartin_select : R.mipmap.cartin_not_select);

                    }
                    MyUtils.tip(cartin_list,cartin_tip,cartin_pay_layout);
                    cartinAdapter = new CartinAdapter(cartin_list,obtainPresenter(),CartinFragment.this);
                    cartinAdapter.setOnItemLongClickListener(new DefaultAdapter.OnRecyclerViewItemLongClickListener<CartinItem>() {
                        @Override
                        public void onItemLongClick(View view, int viewType, CartinItem data, int position) {
                            if(centerDialog != null) {
                                centerDialog.show();
                                centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                                    @Override
                                    public void OnCenterItemClick(CenterDialog dialog, View view) {
                                        if(view.getId() == R.id.dialog_sure){
                                            showLoading();

                                            mPresenter.refreshcartin(objects[0]+"",objects[1]+"",objects[0]+"","1",data.getGoods_id(),"","","","","","")
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .doFinally(new Action() {
                                                        @Override
                                                        public void run() throws Exception {
                                                            hideLoading();
                                                        }
                                                    })
                                                    .subscribe(new ErrorHandleSubscriber<List<CartinItem>>(ArtUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler()) {
                                                        @Override
                                                        public void onNext(List<CartinItem> list) {
                                                            if(getContext()!=null && select_all!=null && cartin_sum !=null){
                                                                MyUtils.tip(list,cartin_tip,cartin_pay_layout);
                                                                select_all.setImageResource( MyUtils.isCartrinSelectAll(list,select_all) ? R.mipmap.cartin_select : R.mipmap.cartin_not_select);
                                                                cartin_sum.setText(String.format(getResources().getString(R.string.sum),MyUtils.sumCartin(list)));
                                                            }
                                                            cartinAdapter.update(list);
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }
                    });
                    if(cartin_recycle == null) return;
                    cartin_recycle.setAdapter(cartinAdapter);
                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                  cartin_recycle.setLayoutManager(linearLayoutManager);
                    ArtUtils.configRecyclerView(cartin_recycle,linearLayoutManager);

//                    Log.e("message5","i do message");
//                    cartinAdapter.update(cartin_list);
            }


    }

    @Override
    public void onDestroy() {
        if(refreshReciver!=null)
            getActivity().unregisterReceiver(refreshReciver);
        super.onDestroy();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class RefreshReciver extends BroadcastReceiver {
        CartinFragment cartinFragment = null;
       public  RefreshReciver (CartinFragment cartinFragment){
            this.cartinFragment = cartinFragment;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            cartinFragment.refresh();
        }
    }

}
