package com.example.yy.notepad.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a123 on 17/4/14.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context); // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        db = helper.getReadableDatabase();
    }

    public boolean insertNote(MessageNote message) {
        boolean b = false;
        db.beginTransaction();
        try{
            Cursor c = db.rawQuery("select * from MessageNote where title=?",new String[]{message.title});
            if (c==null||c.getCount()<=0){
                db.execSQL("insert into MessageNote values(null,?,?,?)",new Object[]{message.date,message.title,message.content});
                b = true;
            }
            c.close();
            db.setTransactionSuccessful();
        }catch (SQLException e){
            db.endTransaction();
            b = false;
        }finally {
            db.endTransaction();
        }
        return b;
    }

    public List<MessageNote> getAll() {
        List<MessageNote> models = new ArrayList<MessageNote>();
        MessageNote p = null ;
        Cursor c = db.rawQuery("select * from MessageNote" , null) ;
        while (c.moveToNext()) {
            p = new MessageNote();
            p.id = c.getInt(c.getColumnIndex("id"));
            p.date = c.getString(c.getColumnIndex("date"));
            p.title = c.getString(c.getColumnIndex("title"));
            p.content = c.getString(c.getColumnIndex("content"));
            models.add(p);
        }
        c.close() ;
        return models ;
    }

    public boolean deleteByTitle(String title) {
        boolean b = false;
        try {
            db.execSQL("delete from MessageNote where title=?",
                    new String[] { title });
            b = true;
        }catch (SQLException e) {
            b = false ;
        }
        return b;
    }

    public MessageNote getByTitle(String title) {
        MessageNote p = null ;
        Cursor c = db.rawQuery("select * from MessageNote where title=?" , new String[] { title }) ;
        while (c.moveToNext()) {
            p = new MessageNote();
            p.id = c.getInt(c.getColumnIndex("id"));
            p.date = c.getString(c.getColumnIndex("date"));
            p.title = c.getString(c.getColumnIndex("title"));
            p.content = c.getString(c.getColumnIndex("content"));
        }
        c.close() ;
        return p ;
    }



    public boolean updateNoteContentByTitle(String date,String title,String content){
        boolean b = false;
        try{
            db.execSQL("update MessageNote set date=?,content=? where title=?",new String[]{date,content,title});
        }catch (SQLException e){
            b = false;
        }
        return b;
    }

}
