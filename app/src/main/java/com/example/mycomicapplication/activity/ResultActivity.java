package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycomicapplication.ComicAdapter;
import com.example.mycomicapplication.ComicwebAdapter;
import com.example.mycomicapplication.SearchAdapter;
import com.example.mycomicapplication.spidermanhuadb.GetSearchResult;
import com.example.mycomicapplication.until.ComicContral;
import com.example.mycomicapplication.until.OkHttpUtils;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.spidermanhuax.GetSearchResoult;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private List<Comic> comicList = new ArrayList<>();
    private SearchAdapter adapter;
    private String search = "http://www.xmanhua.com/search?title=";
    private RecyclerView recyclerView;
    int i=0;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    TextView textView;
    String url;
    String html;
    SwipeRefreshLayout swip_refresh_layout;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    frameLayout.removeView(progressBar);
                    StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    showResult(comicList);
                    textView.setText(FirstActivity.getwebname(i));
                    setLister();
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
        setContentView(R.layout.activity_result);
        textView=(TextView)findViewById(R.id.result_text);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        frameLayout=(FrameLayout) findViewById(R.id.framelayout_result);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swip_refresh_layout = findViewById(R.id.swipelayout);
        swip_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    public void run() {
                        i++;
                        if (i>=FirstActivity.getweblenth()){
                            i=FirstActivity.getweblenth()-1;
                        }
                        Log.e("i", String.valueOf(i));
                        Intent intent = getIntent();
                        switch (i) {
                            case 1:
                                search = "https://www.manhuadb.com/search?q=";
                                url = search + intent.getStringExtra("search_text");
                                html = OkHttpUtils.OkGetArt(url,ResultActivity.this);
                                comicList = GetSearchResult.Spridercomics(html);
                                break;
                            case 2:
                                search = "http://www.ikanmh.top/search?keyword=";
                                url = search + intent.getStringExtra("search_text");
                                html = OkHttpUtils.OkGetArt(url,ResultActivity.this);
                                comicList = com.example.mycomicapplication.spiderikanmanhua.GetSearchResult.Spridercomics(html);
                            default:
                                break;
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
        initcomic();
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
                Intent intent = getIntent();
                url = search + intent.getStringExtra("search_text");
                html = OkHttpUtils.OkGetArt(url,ResultActivity.this);
                comicList = GetSearchResoult.Spridercomics(html);
                //发送信息给handler用于更新UI界面
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void setLister() {//事件监听
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent =new Intent(ResultActivity.this, ChapterActivity.class);
                Comic comic=comicList.get(position);
                String comicurl=comic.getComicurl();
                intent.putExtra("id",i);
                String comicname=comic.getName();
                intent.putExtra("comicname",comicname);
                Log.e("id", String.valueOf(i));
                intent.putExtra("comicurl",comicurl);
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position) {
                Comic comic=comicList.get(position);
                comic.setId(i);
                ComicContral.InsertComic(comic,ResultActivity.this);
                Toast.makeText(ResultActivity.this,comic.getName()+"已加入收藏",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initAinm() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(ResultActivity.this, R.anim.reclerview_anim);
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
                adapter = new SearchAdapter(comics);
                ResultActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //为列表设置数据
                        recyclerView.setAdapter(adapter);
                    }
                });
                //UI线程 刷新条目
                ResultActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
        }
    }
}