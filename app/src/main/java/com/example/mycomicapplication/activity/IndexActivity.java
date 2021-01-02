package com.example.mycomicapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;


import com.example.mycomicapplication.fragment.MainFragment;
import com.example.mycomicapplication.R;
import com.example.mycomicapplication.fragment.TypeFragment;
import com.example.mycomicapplication.until.CheckPermissionsActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private String[] tabs ={"热门","类型"};
    private ArrayList<Fragment> fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        Intent intent=getIntent();
        String weburl=intent.getStringExtra("weburl");
        int id=intent.getIntExtra("id",0);
        for (int i=0;i<tabs.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
        }
        fragments.add(MainFragment.newInstance(weburl,id));
        fragments.add(TypeFragment.newInstance(weburl,id));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });

        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager,false);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        View view = this.getWindow().getDecorView();
        view=null;
    }


}