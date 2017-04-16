package com.example.yy.notepad.db;

/**
 * Created by a123 on 17/4/14.
 */

public class MessageNote {
    int id;
    public String date;
    public String title;
    public String content;

    public MessageNote()
    {
    }

    public MessageNote(String date,String title, String content)
    {
        this.date = date;
        this.title = title;
        this.content = content;
    }

}
