package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycomicapplication.R;
import com.example.mycomicapplication.activity.webview.WatchActivity;
import com.example.mycomicapplication.javabean.Chapter;
import com.example.mycomicapplication.until.ChapterContral;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private List<Chapter> chapterList=new ArrayList<>();
    private ArrayList<String> comicnames=new ArrayList<>();
    String comicname;
    ListView listView;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            HistoryActivity.this, android.R.layout.simple_list_item_1,comicnames);
                    listView.setAdapter(adapter);
                    break;
                default:
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView=(ListView)findViewById(R.id.listview);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter=chapterList.get(position);
                ChapterContral.DeleteComic(chapter,HistoryActivity.this);
                comicnames.clear();
                initcomic();
                return true;
            }
        });
        initcomic();
    }
    private void initcomic(){
        new Thread(){
            public void run(){
                chapterList = ChapterContral.SelectComic(HistoryActivity.this);
                //发送信息给handler用于更新UI界面
                for (Chapter chapter:chapterList){
                    comicname=chapter.getComicname()+" --> "+chapter.getChaptername();
                    comicnames.add(comicname);
                }
                Message message =new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }
}