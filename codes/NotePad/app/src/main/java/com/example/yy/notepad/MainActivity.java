package com.example.yy.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.yy.notepad.db.DBManager;

public class MainActivity extends AppCompatActivity {

    // xml控件
    Button btn;

    // 自定义的变量
    DBManager dbmg;

    // 初始化方法
    void init(){
        dbmg = new DBManager(getApplicationContext());

        btn = (Button) findViewById(R.id.btn);
        startFlick(btn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始空间和 变量
        init();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyListView.class);
                startActivity(intent);

//                MessageNote m = new MessageNote("4/14","note","aaaaaaaaa");
//                dbmg.insertNote(m);
//                MessageNote m2 = new MessageNote("4/11","web","abcabcabc");
//                dbmg.insertNote(m2);
//                dbmg.updateNoteContentByTitle("bbbbbbbbb","web");
//                List<MessageNote> l = dbmg.getAll();
//                //dbmg.deleteByTitle("note");
//                String tmp = "";
//                MessageNote msg1 = l.get(1);
//                tmp = "时间:"+ msg1.date + "标题"+ msg1.title +"内容:" + msg1.content;
//                Toast.makeText(getApplicationContext(),tmp, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startFlick(View view){
        if(view==null)
            return;
        Animation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }
}
