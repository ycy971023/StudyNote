package com.example.mycomicapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycomicapplication.activity.ChapterActivity;
import com.example.mycomicapplication.activity.ResultActivity;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.until.ComicContral;
import com.example.mycomicapplication.until.ComicSaveUtils;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context mContext;
    private List<Comic> mComicList;
    private OnItemClickListener mOnItemClickListener;
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
    public SearchAdapter(List<Comic> comicList){
        mComicList=comicList;
    }
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.comic_item,parent,false);
        final SearchAdapter.ViewHolder holder=new SearchAdapter.ViewHolder(view);
        return holder;
    }
    //监听接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
    //实现接口的方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolder holder, int position) {
        Comic comic=mComicList.get(position);
        holder.comicName.setText(comic.getName());
        Glide.with(mContext).load(comic.getCoverurl()).dontAnimate().apply(
                RequestOptions.centerCropTransform()
                        .placeholder(R.drawable.default_error)
                        .error(R.drawable.default_error)
                        .fallback(R.drawable.default_error))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(0.1f)
                .into(holder.comicImage);// 0.5略缩图into(holder.comicImage);
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();//当前Item所处位置
                    mOnItemClickListener.onItemClick(holder.cardView, pos);
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.cardView, pos);
                    return true;
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return mComicList.size();
    }
}
