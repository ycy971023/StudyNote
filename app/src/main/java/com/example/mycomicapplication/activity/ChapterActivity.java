package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mycomicapplication.activity.recycleview.watchActivity;
import com.example.mycomicapplication.activity.webview.WatchActivity;
import com.example.mycomicapplication.until.OkHttpUtils;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.Chapter;
import com.example.mycomicapplication.spidermanhuax.GetChapter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {

    private ArrayList<String> chapternames=new ArrayList<>();
    private ArrayList<Chapter> chapters=new ArrayList<>();
    private ListView listView;
    SwipeRefreshLayout swip_refresh_layout;
    String comicurl;
    String html;
    String comicname;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;
    int id;
    ArrayAdapter<String> adapter;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                     showResult(chapternames);
                    coordinatorLayout.removeView(progressBar);
            }

        }
    };
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        listView=(ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.activity_chapter);
        progressBar.setIndeterminateDrawable(wave);
        swip_refresh_layout=findViewById(R.id.swipelayout);
        swip_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                        Collections.reverse(chapters);
                        Collections.reverse(chapternames);
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                swip_refresh_layout.setRefreshing(false);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long Id) {
                Intent intent;
                Chapter chapter=chapters.get(position);
                chapter.setComicname(comicname);
                switch (id){
                    case 0:
                    case 2:
                        intent=new Intent(ChapterActivity.this, WatchActivity.class);
                        intent.putExtra("chapter",chapter);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(ChapterActivity.this, watchActivity.class);
                        intent.putExtra("chapter",chapter);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                Toast.makeText(ChapterActivity.this,chapter.getChaptername(),Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Chapter chapter=chapters.get(position);
                    Toast.makeText(ChapterActivity.this,"开始下载"+"["+chapter.getChaptername()+"]",Toast.LENGTH_SHORT).show();
                    return true;
            }
        });
        initchapter();
    }
    private void initchapter(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent=getIntent();
                id=intent.getIntExtra("id",0);
                comicname=intent.getStringExtra("comicname");
                switch (id){
                    case 0:
                        comicurl=FirstActivity.getweburl(id)+intent.getStringExtra("comicurl");
                        html = OkHttpUtils.OkGetArt(comicurl,ChapterActivity.this);
                        chapters = GetChapter.SpriderChapter(html);
                        break;
                    case 1:
                        comicurl=FirstActivity.getweburl(id)+intent.getStringExtra("comicurl");
                        html = OkHttpUtils.OkGetArt(comicurl,ChapterActivity.this);
                        chapters = com.example.mycomicapplication.spidermanhuadb.GetChapter.SpriderChapter(html);
                        break;
                    case 2:
                        comicurl=FirstActivity.getweburl(id)+intent.getStringExtra("comicurl");
                        html = OkHttpUtils.OkGetArt(comicurl,ChapterActivity.this);
                        chapters = com.example.mycomicapplication.spiderikanmanhua.GetChapter.SpriderChapter(html);
                        break;
                    default:
                        break;
                }
                for (Chapter chapter:chapters)
                    chapternames.add(chapter.getChaptername());
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        View view = this.getWindow().getDecorView();
        view=null;
    }
    private void initAinm() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(ChapterActivity.this, R.anim.reclerview_anim);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.2f);
        //为ListView设置LayoutAnimationController属性；
        listView.setLayoutAnimation(lac);
    }
    public void showResult(List<String> comics) {

        //调用动画方法   一定要在设置数据之前
//      initAinm();
        if(comics!=null&&comics.size()>0){
            adapter = new ArrayAdapter<String>(
                    ChapterActivity.this, android.R.layout.simple_list_item_1, comics);
            ChapterActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //为列表设置数据
                    listView.setAdapter(adapter);
                }
            });
            //UI线程 刷新条目
            ChapterActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}