package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.example.mycomicapplication.ComicAdapter;
import com.example.mycomicapplication.spiderikanmanhua.GetTypeInfo;
import com.example.mycomicapplication.spidermanhuadb.GetmanhuadbTypeinfo;
import com.example.mycomicapplication.until.OkHttpUtils;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.spidermanhuax.GetXmanhuaTypeInfo;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends AppCompatActivity {
    private List<Comic> comicList = new ArrayList<>();
    private ComicAdapter adapter;
    private RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    SwipeRefreshLayout swip_refresh_layout;
    int i=1;
    String url;
    String html;
    ProgressBar progressBar;
    int id;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    showResult(comicList);
                    coordinatorLayout.removeView(progressBar);
                    break;
                default:
                    break;
            }
            swip_refresh_layout.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swip_refresh_layout=findViewById(R.id.swipelayout);
        swip_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.activity_type);
        initcomic();
        Intent intent = getIntent();
        url =intent.getStringExtra("typeurl");
        id=intent.getIntExtra("id",0);
        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                        i++;
                        switch (id){
                            case 0:
                                if(i<=2){
                                    url=url.substring(0,url.length()-1);
                                    url =url+"-p"+i;
                                }else url=url.substring(0,url.length()-1)+i;

                                initcomic();
                                break;
                            case 1:
                                if(i<=2){
                                    url=url.substring(0,url.length()-5);
                                }else url=url.substring(0,url.length()-12);
                                url =url+"-page-"+i+".html";
                                initcomic();
                                break;
                            case 2:
                                if (i<=2) {
                                    url = url + "&page=" + i;
                                }else url=url.substring(0,url.length()-1)+i;
                                initcomic();
                                break;
                        }
                Log.e("url", url );
                    }
            });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        View view = this.getWindow().getDecorView();
        view=null;
    }
    private void initcomic() {
        new Thread() {
            public void run() {
                switch (id){
                    case 0:
                        html = OkHttpUtils.OkGetArt(url,TypeActivity.this);
                        comicList = GetXmanhuaTypeInfo.SpriderComic(html);
                        break;
                    case 1:
                        html = OkHttpUtils.OkGetArt(url,TypeActivity.this);
                        comicList= GetmanhuadbTypeinfo.SpriderComic(html);
                        break;
                    case 2:
                        html = OkHttpUtils.OkGetArt(url,TypeActivity.this);
                        comicList= GetTypeInfo.SpriderComic(html);
                }
                //发送信息给handler用于更新UI界面
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void initAinm() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(TypeActivity.this, R.anim.reclerview_anim);
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
                adapter = new ComicAdapter(comics);
                TypeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //为列表设置数据
                        recyclerView.setAdapter(adapter);
                    }
                });
                //UI线程 刷新条目
                TypeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
        }
    }

}