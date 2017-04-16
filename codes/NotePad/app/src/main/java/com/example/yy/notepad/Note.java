package com.example.yy.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yy.notepad.db.DBManager;
import java.util.Calendar;


/**
 * Created by a123 on 17/4/15.
 */

public class Note extends Activity {

    // 控件
    TextView date;
    EditText title,content;
    Button save;
    Button cancle;

    // 自定义变量

    //
    void init(){
        date = (TextView) findViewById(R.id.date);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        save = (Button) findViewById(R.id.save);
        cancle = (Button) findViewById(R.id.cancle);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);

        //
        init();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String time_now = year+"/"+month+"/"+day+" "+hour+":"+min;

        date.setText(time_now);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newdate = date.getText().toString();
                String newtitle = title.getText().toString();
                String newcontent = content.getText().toString();
                Intent intent = getIntent();
                intent.putExtra("date", newdate);
                intent.putExtra("title", newtitle);
                intent.putExtra("content", newcontent);
                Note.this.setResult(1,intent);
                Note.this.finish();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Note.this,MyListView.class);
                startActivity(intent);
                Note.this.finish();
            }
        });

    }

}
