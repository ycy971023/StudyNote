package com.example.mycomicapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycomicapplication.activity.ChapterActivity;
import com.example.mycomicapplication.activity.ResultActivity;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.javabean.ComicWeb;
import com.example.mycomicapplication.until.ComicContral;
import com.example.mycomicapplication.until.ComicSaveUtils;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private Context mContext;
    private List<Comic> mComicList;
    private ComicSaveUtils comicSaveUtils;
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
    public ComicAdapter(List<Comic> comicList){
        mComicList=comicList;
    }
    @NonNull
    @Override
    public ComicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.comic_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Comic comic=mComicList.get(position);
                Intent intent =new Intent(mContext, ChapterActivity.class);
                int id=ComicwebAdapter.Setcomicid(comic);
                String comicurl=comic.getComicurl();
                String comicname=comic.getName();
                intent.putExtra("id",id);
                intent.putExtra("comicurl",comicurl);
                intent.putExtra("comicname",comicname);
                mContext.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=holder.getAdapterPosition();
                Comic comic=mComicList.get(position);
                int id=ComicwebAdapter.Setcomicid(comic);
                comic.setId(id);
                Toast.makeText(mContext,comic.getName()+"已加入收藏",Toast.LENGTH_SHORT).show();
                ComicContral.InsertComic(comic,mContext);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapter.ViewHolder holder, int position) {
        Comic comic=mComicList.get(position);
        holder.comicName.setText(comic.getName());
        Glide.with(mContext).load(comic.getCoverurl()).dontAnimate().apply(
        RequestOptions.centerCropTransform()
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .fallback(R.drawable.default_error))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(0.1f)
                .into(holder.comicImage);
    }

    @Override
    public int getItemCount() {
        return mComicList.size();
    }
}
