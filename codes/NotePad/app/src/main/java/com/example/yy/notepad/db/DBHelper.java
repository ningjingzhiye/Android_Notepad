package com.example.yy.notepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a123 on 17/4/14.
 */

public class DBHelper extends SQLiteOpenHelper {
    //数据库 名字 自己取
    private static final String DATABASE_NAME = "xx1.db";
    private static final int DATABASE_VERSION = 1;

    //构造方法
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建person表
        String messageTable = "create table if not exists MessageNote" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "date VARCHAR(255), " +
                "title VARCHAR(255), " +
                "content TEXT )";
        db.execSQL(messageTable);
    }

    // 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE MessageNote ADD COLUMN other STRING");
    }


}
