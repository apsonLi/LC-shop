package com.codetech.apson.shop.mvp.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.GlideImageLoader;
import com.codetech.apson.shop.mvp.model.entity.BannerImage;
import com.codetech.apson.shop.mvp.model.entity.Goods;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.activity.GoodsInfoActivity;
import com.codetech.apson.shop.mvp.ui.adapter.GoodsAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.ArtUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndexFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends BaseFragment<MainPresenter> implements IView {
    @BindView(R.id.goods_list)
    RecyclerView recyclerView;
    @BindView(R.id.main_banner)
    Banner banner;
    @BindView(R.id.index_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private GoodsAdapter goodsAdapter;

    public IndexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndexFragment newInstance(String param1, String param2) {
        IndexFragment fragment = new IndexFragment();
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

        View v =inflater.inflate(R.layout.fragment_index,container,false);

        return v;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHotSale(Message.obtain(IndexFragment.this));

            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        });

       mPresenter.getBannerImage(Message.obtain(this));

       hideLoading();
    }
    public void showLoading(){

        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getHotSale(Message.obtain(IndexFragment.this));
    }
    public void hideLoading(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public  void initbanner(List<String> bannerImages,List<String> bannerImageID){
        if(banner!=null){
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent =new Intent(getActivity(),GoodsInfoActivity.class);
                    intent.putExtra("goodsid",bannerImageID.get(position));
                    startActivity(intent);
                }
            });
            banner.isAutoPlay(true);
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(bannerImages);
            banner.start();
        }

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
    public void showMessage(@NonNull String message) {
        Toast.makeText(getContext(),"bannerclick"+message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        if(message.what ==0){
            ArrayList<String> list = message.getData().getStringArrayList("banner_image");
            if(list !=null ){
                initbanner(list,message.getData().getStringArrayList("banner_image_id"));
            }
        }
        if(message.what == 1) {
            ArrayList<Goods> list = message.getData().getParcelableArrayList("hotSale_list");
            if (list != null) {
                goodsAdapter = new GoodsAdapter(list);

               goodsAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<Goods>() {
                   @Override
                   public void onItemClick(View view, int viewType, Goods data, int position) {
                       Intent intent =new Intent(getActivity(), GoodsInfoActivity.class);
                       intent.putExtra("goodsid",data.getGoods_id());
                       startActivity(intent);
                   }
               });
               if(recyclerView != null){
                   recyclerView.setAdapter(goodsAdapter);
                   ArtUtils.configRecyclerView(recyclerView, new GridLayoutManager(getContext(), 2));
               }

            }
        }
        if(message.what ==3){
            hideLoading();
            if(goodsAdapter !=null)
            goodsAdapter.update(message.getData().getParcelableArrayList("hotSale_list"));
        }
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


}
