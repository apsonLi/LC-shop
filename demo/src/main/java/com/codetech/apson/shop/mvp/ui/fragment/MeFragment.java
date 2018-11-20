package com.codetech.apson.shop.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.codetech.apson.shop.R;
import com.codetech.apson.shop.mvp.model.entity.Address;
import com.codetech.apson.shop.mvp.presenter.MainPresenter;
import com.codetech.apson.shop.mvp.ui.activity.AddressActivity;
import com.codetech.apson.shop.mvp.ui.activity.MainActivity;
import com.codetech.apson.shop.mvp.ui.activity.loginActivity;

import butterknife.BindView;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.http.imageloader.glide.GlideArt;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends BaseFragment<MainPresenter> implements IView{
    @Nullable@BindView(R.id.nickname)
    TextView textView;
    @Nullable@BindView(R.id.logout)
    TextView logout;
    @Nullable@BindView(R.id.head)
    ImageView head;
    @Nullable@BindView(R.id.login)
    Button login;
    @Nullable@BindView(R.id.refresh_me)
    SwipeRefreshLayout refresh_me;
    @Nullable@BindView(R.id.addr_layout)
    LinearLayout addr_layout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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

                ? inflater.inflate(R.layout.not_loginlayout, container, false)
                : inflater.inflate(R.layout.fragment_me, container, false);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(head!=null && refresh_me!=null && addr_layout!=null && logout!=null){
            GlideArt.with(this).load(R.drawable.account).circleCrop().into(head);
            refresh_me.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh_me.setRefreshing(false);
                }
            });
            addr_layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddressActivity.class);
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                   SharedPreferences sp =  getActivity().getSharedPreferences("userinfo",0);
//                    mPresenter.getName(sp.getString("userID",null)+"",sp.getString("userKEY",null)+"",sp.getString("userID",null)+"");
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userID",null);
                    editor.putString("userKEY",null);
                    editor.apply();
                    MainActivity m = (MainActivity)getActivity();
                    m.initViewPage();

                }
            });

        }
        if(login !=null){
            login.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),loginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Nullable
    @Override
    public MainPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void handleMessage(@NonNull Message message) {

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
