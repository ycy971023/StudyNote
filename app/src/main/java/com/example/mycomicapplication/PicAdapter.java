package com.example.mycomicapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycomicapplication.javabean.Pic;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter .ViewHolder> {
    private Context mContext;
    private List<Pic> picList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView comicImage;
        public ViewHolder(View view){
            super(view);
            comicImage=(ImageView)view.findViewById(R.id.img_pic);
        }
    }
    public PicAdapter(List<Pic> piclist){
        picList=piclist;
    }
    @NonNull
    @Override
    public PicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.pic_item,parent,false);
        final PicAdapter.ViewHolder holder=new PicAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicAdapter.ViewHolder holder, int position) {
        Pic pic=picList.get(position);
        Glide.with(mContext).load(pic.getUrl()).dontAnimate().apply(
                RequestOptions.centerCropTransform()
                        .placeholder(R.drawable.default_error)
                        .error(R.drawable.default_error)
                        .fallback(R.drawable.default_error))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(0.1f)
                .into(holder.comicImage);// 0.5略缩图into(holder.comicImage);
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }
}
