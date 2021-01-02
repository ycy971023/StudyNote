package com.example.mycomicapplication.until;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycomicapplication.javabean.Chapter;

import java.util.ArrayList;

public class ChapterContral {
    public static void InsertComic(Chapter chapter, Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",chapter.getChaptername());
        values.put("url",chapter.getChapterurl());
        values.put("flag",chapter.getId());
        values.put("ComicName",chapter.getComicname());
        db.insert("CHAPTER",null,values);
        values.clear();
    }
//    public static void UpdateComic(Chapter chapter, Context context){
//        ComicSaveUtils comicSaveUtils;
//        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
//        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("name",chapter.getChaptername());
//        values.put("url",chapter.getChapterurl());
//        values.put("flag",chapter.getId());
//        values.put("ComicName",chapter.getComicname());
//        db.insert("CHAPTER",null,values);
//        values.clear();
//    }
    public static void DeleteComic(Chapter chapter,Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        db.delete("CHAPTER","ComicName=?",new String[]{chapter.getComicname()});
    }
    public static void UpdateComic(Chapter chapter,Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",chapter.getChaptername());
        values.put("url",chapter.getChapterurl());
        values.put("flag",chapter.getId());
        db.update("CHAPTER",values,"ComicName=?",new String[]{chapter.getComicname()});
        values.clear();
    }
    public static ArrayList<Chapter> SelectComic(Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        ArrayList<Chapter> chapters=new ArrayList<>();
        Cursor cursor=db.query("CHAPTER",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                String comic_name=cursor.getString(cursor.getColumnIndex("ComicName"));
                int flag=cursor.getInt(cursor.getColumnIndex("flag"));
                Chapter chapter=new Chapter();
                chapter.setComicname(comic_name);
                chapter.setChaptername(name);
                chapter.setChapterurl(url);
                chapter.setId(flag);
                chapters.add(chapter);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return chapters;
    }
}
