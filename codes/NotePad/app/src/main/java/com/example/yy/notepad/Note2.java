package com.example.yy.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by a123 on 17/4/15.
 */

public class Note2 extends Activity {

    // 控件
    TextView date;
    EditText title,content;
    Button save;
    Button cancle;

    // 自定义变量

    //
    void init(){
        date = (TextView) findViewById(R.id.date2);
        title = (EditText) findViewById(R.id.title2);
        content = (EditText) findViewById(R.id.content2);
        save = (Button) findViewById(R.id.save2);
        cancle = (Button) findViewById(R.id.cancle2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note2);
        //
        init();

        final Intent intent1 = getIntent();
        Bundle message = intent1.getExtras();
        final int now_position = message.getInt("position");
        final String now_date = message.getString("date");
        final String now_title = message.getString("title");
        String now_content = message.getString("content");

        date.setText(now_date);
        title.setText(now_title);
        content.setText(now_content);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                String Date = year+"/"+month+"/"+day+" "+hour+":"+min;

                if(!Date.equals(now_date)) {
                    date.setText(Date);
                }

                String newDate = date.getText().toString();

                String newContent = content.getText().toString();
                intent1.putExtra("position",now_position);
                intent1.putExtra("date",newDate);
                intent1.putExtra("title",now_title);
                intent1.putExtra("content", newContent);

                Note2.this.setResult(1,intent1);
                Note2.this.finish();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Note2.this,MyListView.class);
                startActivity(intent);
                Note2.this.finish();
            }
        });
    }
}
