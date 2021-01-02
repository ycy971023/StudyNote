package com.example.mycomicapplication.until;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ComicSaveUtils extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE_Comics="create table COMIC("
            +"id integer primary key autoincrement,"
            +"name text UNIQUE,"
            +"url text,"
            +"flag integer,"
            +"cover text)";
    public static final String CREATE_Chapter="create table CHAPTER("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"url text,"
            +"flag integer,"
            +"ComicName text UNIQUE)";
    public ComicSaveUtils(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_Comics);
            db.execSQL(CREATE_Chapter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists COMIC");
            db.execSQL("drop table if exists CHAPTER");
            onCreate(db);
    }
}
