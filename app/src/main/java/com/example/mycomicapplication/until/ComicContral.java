package com.example.mycomicapplication.until;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mycomicapplication.ComicwebAdapter;
import com.example.mycomicapplication.javabean.Comic;
import com.example.mycomicapplication.until.ComicSaveUtils;

import java.util.ArrayList;

public class ComicContral {

    public static void InsertComic(Comic comic , Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",comic.getName());
        values.put("url",comic.getComicurl());
        values.put("cover",comic.getCoverurl());
        values.put("flag",comic.getId());
        db.insert("COMIC",null,values);
        values.clear();
    }
    public static void DeleteComic(Comic comic,Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        db.delete("COMIC","name=?",new String[]{comic.getName()});
    }

    public static ArrayList<Comic> SelectComic(Context context){
        ComicSaveUtils comicSaveUtils;
        comicSaveUtils=new ComicSaveUtils(context,"Comics.db",null,2);
        SQLiteDatabase db=comicSaveUtils.getWritableDatabase();
        ArrayList<Comic> comics=new ArrayList<>();
        Cursor cursor=db.query("COMIC",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                String cover=cursor.getString(cursor.getColumnIndex("cover"));
                int flag=cursor.getInt(cursor.getColumnIndex("flag"));
                Comic comic=new Comic(name,url,cover);
                comic.setId(flag);
                comics.add(comic);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return comics;
    }
}
