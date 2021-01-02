package com.example.mycomicapplication.activity.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mycomicapplication.ComicwebAdapter;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.activity.ChapterActivity;
import com.example.mycomicapplication.activity.FirstActivity;
import com.example.mycomicapplication.javabean.Chapter;
import com.example.mycomicapplication.javabean.ComicWeb;
import com.example.mycomicapplication.until.ChapterContral;
import com.example.mycomicapplication.until.ScrollBottomScrollView;

import java.util.ArrayList;

public class WatchActivity extends AppCompatActivity   {
    String picurl;
    private WebView mWebView;
    ScrollBottomScrollView scrollView;
    TextView textView;
    Chapter chapter;
    int id;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        mWebView = (WebView) findViewById(R.id.webview);
        scrollView=(ScrollBottomScrollView) findViewById(R.id.scrollView);
        scrollView.onScrollViewScrollToBottom(new ScrollBottomScrollView.OnScrollBottomListener() {
            @Override
            public void scrollToBottom() {
                Toast.makeText(WatchActivity.this,"我也是有底线的",Toast.LENGTH_SHORT).show();

            }
        });
        textView=(TextView)findViewById(R.id.info);
        chapter=(Chapter)getIntent().getSerializableExtra("chapter");
        ChapterContral.DeleteComic(chapter,WatchActivity.this);
        textView.setText(chapter.getChaptername());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        id=chapter.getId();
        picurl = FirstActivity.getweburl(id)+chapter.getChapterurl();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });//禁用点击
        mWebView.loadUrl(picurl);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
        ChapterContral.InsertComic(chapter,WatchActivity.this);
    }
    public void destroyWebView() {
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(false);
        }
    }


}