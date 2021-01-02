package com.example.mycomicapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycomicapplication.activity.IndexActivity;
import com.example.mycomicapplication.javabean.Chapter;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.javabean.ComicWeb;

import java.util.List;

public class ComicwebAdapter extends RecyclerView.Adapter<ComicwebAdapter.ViewHolder> {
    private Context mContext;
    private List<ComicWeb> mComicList;
    public static int  id;
    private String comicurl;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView comicImage;
        TextView comicName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView) view;
            comicImage=(ImageView)view.findViewById(R.id.comic_image);
            comicName=(TextView) view.findViewById(R.id.comic_name);
        }
    }
    public ComicwebAdapter(List<ComicWeb> comicwebList){
        mComicList=comicwebList;
    }
    @NonNull
    @Override
    public ComicwebAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.comic_item,parent,false);
        final ComicwebAdapter.ViewHolder holder=new ComicwebAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                ComicWeb comicWeb=mComicList.get(position);
                Intent intent =new Intent(mContext, IndexActivity.class);
                comicurl=comicWeb.getWeburl();
                id=comicWeb.getId();
                intent.putExtra("weburl",comicurl);
                intent.putExtra("id",id);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ComicwebAdapter.ViewHolder holder, int position) {
        ComicWeb comicweb=mComicList.get(position);
        holder.comicName.setText(comicweb.getWebname());
        Glide.with(mContext).load(comicweb.getWebcover()).into(holder.comicImage);
    }

    @Override
    public int getItemCount() {
        return mComicList.size();
    }
    public  static int Setcomicid(Comic comic){
        comic.setId(id);
        int comic_id=comic.getId();
        return comic_id;

    }
    public  static int Setchapterid(Chapter chapter){
        chapter.setId(id);
        int comic_id=chapter.getId();
        return comic_id;

    }
}
