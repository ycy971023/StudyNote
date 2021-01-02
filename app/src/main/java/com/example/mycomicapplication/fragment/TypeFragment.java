package com.example.mycomicapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycomicapplication.R;
import com.example.mycomicapplication.activity.FirstActivity;
import com.example.mycomicapplication.activity.TypeActivity;
import com.example.mycomicapplication.javabean.Type;
import com.example.mycomicapplication.spidermanhuadb.GetType;
import com.example.mycomicapplication.spidermanhuax.GetXmanhuaType;
import com.example.mycomicapplication.until.OkHttpUtils;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;
import java.util.List;


public class TypeFragment extends Fragment {
    private static String ARG_PARAM1="param1";
    private static String ARG_PARAM2="id";
    private int id;
    private String mParam1;
    ListView listView;
    private ArrayList<String> typenames=new ArrayList<>();
    private List<Type> typeList=new ArrayList<>();
    private Handler handler;
    protected Activity mActivity;
    private String html;
    TextView textView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
    public static TypeFragment newInstance(String param1,int id) {
        TypeFragment fragment = new TypeFragment();
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
        inittype();
    }
    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_type, null);
        listView=view.findViewById(R.id.listview);
        textView=view.findViewById(R.id.text);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        final  FrameLayout frameLayout=(FrameLayout) view.findViewById(R.id.framelayout_type);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long Id) {
                Type type=typeList.get(position);
                Intent intent=new Intent(mActivity, TypeActivity.class);
                String Type= FirstActivity.getweburl(id) +type.getTypeurl();
                intent.putExtra("id",id);
                intent.putExtra("typeurl",Type);
                startActivity(intent);

            }
        });
        handler=new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                mActivity, android.R.layout.simple_list_item_1, typenames);
                        listView.setAdapter(adapter);
                        frameLayout.removeView(progressBar);
                        break;
                    case 2:
                        textView.setText("该网站无分类");
                        frameLayout.removeView(progressBar);
                        break;
                    default:
                        break;
                }
            }
        };
        // Inflate the layout for this fragment
        return view;

    }

    private  void inittype(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                Log.e("id", String.valueOf(id));
                switch (id){
                    case 0:
                        html = OkHttpUtils.OkGetArt(mParam1,mActivity);
                        typeList= GetXmanhuaType.SpriderType(html);
                        message.what=1;
                         break;
                    case 1:
                        html = OkHttpUtils.OkGetArt(mParam1,mActivity);
                        typeList= GetType.SpriderType(html);
                        message.what=1;
                        break;
                    case 2:
                        html = OkHttpUtils.OkGetArt(mParam1,mActivity);
                        typeList= com.example.mycomicapplication.spiderikanmanhua.GetType.SpriderType(html);
                        message.what=1;
                    default:
                        break;
                }
                for (Type type:typeList)
                    typenames.add(type.getTypename());
                handler.sendMessage(message);
            }
        }).start();
    }
}