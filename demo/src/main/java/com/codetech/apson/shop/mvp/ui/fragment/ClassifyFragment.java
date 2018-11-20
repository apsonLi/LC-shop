package com.codetech.apson.shop.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.app.utils.MyUtils;
import com.codetech.apson.shop.mvp.model.entity.Classify_Goods;
import com.codetech.apson.shop.mvp.model.entity.MyBaseResponse;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.activity.GoodsInfoActivity;
import com.codetech.apson.shop.mvp.ui.activity.SearchActivity;
import com.codetech.apson.shop.mvp.ui.adapter.LeftClassifyAdapter;
import com.codetech.apson.shop.mvp.ui.adapter.RightClassifyAdapter;

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
 * {@link ClassifyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassifyFragment extends BaseFragment<MainPresenter> implements IView {
    @BindView(R.id.left_classify)
    RecyclerView left_classify;
    @BindView(R.id.right_classify)
    RecyclerView right_classify;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.search_edit)
    EditText editText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClassifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassifyFragment newInstance(String param1, String param2) {
        ClassifyFragment fragment = new ClassifyFragment();
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
    public float getStatusBarHeight() {
        float result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimension(resourceId);
        }
        return result;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
            appBarLayout.setPadding(0,(int)getStatusBarHeight(),0,0);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getActivity(),SearchActivity.class);
                    startActivity(intent);
                }
            });



        mPresenter.getClassify(Message.obtain(this));
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

    }

    @Override
    public void handleMessage(@NonNull Message message) {
        ArrayList<Classify_Goods>  list = message.getData().getParcelableArrayList("classify_goods");
        if(list != null && left_classify !=null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ArtUtils.configRecyclerView(left_classify,linearLayoutManager);
            LeftClassifyAdapter leftClassifyAdapter =  new LeftClassifyAdapter(list);
            RightClassifyAdapter rightClassifyAdapter =new RightClassifyAdapter(list.get(0).getChild());
            rightClassifyAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<Classify_Goods.ChildBean>() {
                @Override
                public void onItemClick(View view, int viewType, Classify_Goods.ChildBean data, int position) {
                    Intent intent =new Intent(getActivity(), GoodsInfoActivity.class);
                    intent.putExtra("goodsid",data.getGoods_id());
                    startActivity(intent);
                }
            });
            right_classify.setAdapter(rightClassifyAdapter);
            ArtUtils.configRecyclerView(right_classify,new GridLayoutManager(getContext(),2));
            leftClassifyAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<Classify_Goods>() {
                @Override
                public void onItemClick(View view, int viewType, Classify_Goods data, int position) {
                    rightClassifyAdapter.update(data.getChild());
//                    Toast.makeText(getContext(),data.getChild().size()+"",Toast.LENGTH_SHORT).show();
                }
            });

            left_classify.setAdapter(leftClassifyAdapter);

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
