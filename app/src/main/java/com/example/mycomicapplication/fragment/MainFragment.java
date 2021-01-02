package com.example.mycomicapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.example.mycomicapplication.ComicAdapter;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.spidermanhuadb.Getcover;
import com.example.mycomicapplication.spidermanhuax.GetCover;
import com.example.mycomicapplication.until.MobileOkHttpUtils;
import com.example.mycomicapplication.until.OkHttpUtils;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static String ARG_PARAM1="mParam1";
    private static String ARG_PARAM2="id";
    private String mParam1;
    private int id;
    private List<Comic> comicList=new ArrayList<>();
    private RecyclerView recyclerView;
    private Handler handler;
    private  String html;
    protected Activity mActivity;
    ComicAdapter adapter;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
    public static MainFragment newInstance(String param1,int id) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2,id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            id=getArguments().getInt(ARG_PARAM2);


        }
        initcomic();


    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_main, null);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        initAinm();
        progressBar.setIndeterminateDrawable(wave);
        final CoordinatorLayout coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.fragment_main);
        handler=new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        showResult(comicList);
                        coordinatorLayout.removeView(progressBar);
                        break;
                    default:
                        break;
                }
            }
        };
        return view;
    }

    private void initcomic(){
        new Thread(){
            public void run(){
                switch (id){
                    case 0:
                        html = OkHttpUtils.OkGetArt(mParam1,mActivity);
                        comicList = GetCover.SpriderComic(html);
                        break;
                    case 1:
                        html = OkHttpUtils.OkGetArt(mParam1,mActivity);
                        comicList = Getcover.SpriderComic(html);
                        break;
                    case 2:
                        html=OkHttpUtils.OkGetArt(mParam1,mActivity);
                        comicList= com.example.mycomicapplication.spiderikanmanhua.GetCover.SpriderComic(html);
                    default:
                        break;
                }
                //发送信息给handler用于更新UI界面
                Message message =new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void initAinm() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.reclerview_anim);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.2f);
        //为ListView设置LayoutAnimationController属性；
        recyclerView.setLayoutAnimation(lac);
    }
    public void showResult(List<Comic> comics) {

        //调用动画方法   一定要在设置数据之前
        initAinm();

        if(comics!=null&&comics.size()>0){
            if(adapter == null){
                adapter = new ComicAdapter(comics);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //为列表设置数据
                        recyclerView.setAdapter(adapter);
                    }
                });
            }else {
                //UI线程 刷新条目
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }


    }