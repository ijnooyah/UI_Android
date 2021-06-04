package com.kh.ui_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tbl_student (" +
                "      sno char(8) primary key," +
                "    sname char(10) not null," +
                "    syear int not null," +
                "    gender char(3) not null," +
                "    major char(10) not null," +
                "    score int not null default 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists tbl_student";
        db.execSQL(sql);
        onCreate(db);
    }
}
