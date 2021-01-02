package com.example.mycomicapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.mycomicapplication.SearchAdapter;
import com.example.mycomicapplication.until.ComicContral;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.javabean.Comic;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {
    private SearchAdapter adapter;
    private List<Comic> comicList=new ArrayList<>();
    private RecyclerView recyclerView;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new SearchAdapter(comicList);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;

            }
                setLister();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        initcomic();
    }
    private void initcomic(){
        new Thread(){
            public void run(){
                comicList = ComicContral.SelectComic(MyActivity.this);
                //发送信息给handler用于更新UI界面
                Message message =new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setLister() {//事件监听
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent =new Intent(MyActivity.this, ChapterActivity.class);
                Comic comic=comicList.get(position);
                int id=comic.getId();
                String comicname=comic.getName();
                intent.putExtra("comicname",comicname);
                String comicurl=comic.getComicurl();
                intent.putExtra("id",id);
                intent.putExtra("comicurl",comicurl);
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, final int position) {

                new AlertDialog.Builder(MyActivity.this)
                        .setTitle("确定要删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Comic comic=comicList.get(position);
                                ComicContral.DeleteComic(comic,MyActivity.this);
                                initcomic();
                            }
                        }).show();
            }
        });
    }
}