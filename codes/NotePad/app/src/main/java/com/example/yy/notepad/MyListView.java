package com.example.yy.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.yy.notepad.db.*;



/**
 * Created by a123 on 17/4/15.
 */

public class MyListView extends Activity {

    // 控件
    ListView lv;
    Button add;

    // 自定义变量
    DBManager dbmg;
    MessageNote messageNote;
    MessageNote messageNote2;
    private List<Map<String,Object>> data;
    MyAdapter adapter = null;
    String[] from = {"date","title","content"};
    int[] to = {R.id.note_date,R.id.note_title,R.id.note_content};

    private List<Map<String, Object>> getData()
    {
        // 初始化的数据从数据库中读取
        List<MessageNote> lp = dbmg.getAll();

        int len = lp.size();
        String[] _date = new String[0];
        String[] _title = new String[0];
        String[] _content = new String[0];
        if(len > 0) {
            _date = new String[len];
            _title = new String[len];
            _content = new String[len];

            for (int i = 0; i < len; i++) {
                MessageNote ptmp = lp.get(i);

                _date[i] = ptmp.date;
                _title[i] = ptmp.title;
                _content[i] = ptmp.content;
            }
        }

        // 根据数据库读取的内容，去初始化list
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<len;i++)
        {
            map = new HashMap<String, Object>();
            map.put(from[0], _date[i]);
            map.put(from[1], _title[i]);
            map.put(from[2], _content[i]);
            list.add(map);
        }
        return list;
    }

    //
    void init()
    {
        lv = (ListView) findViewById(R.id.lv);
        add = (Button) findViewById(R.id.add);

        dbmg = new DBManager(this);
        data = getData();
        adapter = new MyAdapter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        //
        init();

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String curDate = (String) data.get(position).get("date");
                String curTitle = (String) data.get(position).get("title");
                String curContent = (String) data.get(position).get("content");
                Intent intent1 = new Intent(MyListView.this,Note2.class);
                intent1.putExtra("position",position);
                intent1.putExtra("date",curDate);
                intent1.putExtra("title",curTitle);
                intent1.putExtra("content",curContent);
                startActivityForResult(intent1,1);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String title = (String)data.get(position).get("title");

                new AlertDialog.Builder(MyListView.this).setTitle("系统提示")
                        .setMessage("确定删除吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 删除数据后，同时还要从数据库中删除
                                dbmg.deleteByTitle(title);

                                data.remove(position);// 删除数据后，需要刷新数据
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListView.this,Note.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0 && resultCode == 1){
            Bundle message = intent.getExtras();
            String resultdate = message.getString("date");
            String resulttitle = message.getString("title");
            String resultcontent = message.getString("content");

            messageNote = new MessageNote(resultdate,resulttitle,resultcontent);
            dbmg.insertNote(messageNote);

            Map<String, Object> map1;
            map1 = new HashMap<String, Object>();
            map1.put(from[0], messageNote.date);
            map1.put(from[1], messageNote.title);
            map1.put(from[2], messageNote.content);
            data.add(map1);

            adapter.notifyDataSetChanged();
        }
        else if(requestCode == 1 && resultCode == 1){
            Bundle message2 = intent.getExtras();
            int nowposition = message2.getInt("position");
            String newdate = message2.getString("date");
            String nowtitle = message2.getString("title");
            String newcontent = message2.getString("content");
            dbmg.updateNoteContentByTitle(newdate,nowtitle,newcontent);
            messageNote2 = dbmg.getByTitle(nowtitle);

            if(messageNote2!=null) {
                Map<String, Object> map2;
                map2 = new HashMap<String, Object>();
                map2.put(from[0], messageNote2.date);
                map2.put(from[1], messageNote2.title);
                map2.put(from[2], messageNote2.content);
                data.set(nowposition, map2);

                adapter.notifyDataSetChanged();
            }
        }
    }

    static class ViewHolder{
        public TextView date1;
        public TextView title1;
        public TextView content1;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        private Context context;

        public MyAdapter(Context context){
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item,null);
                holder.date1 = (TextView)convertView.findViewById(R.id.note_date);
                holder.title1 = (TextView)convertView.findViewById(R.id.note_title);
                holder.content1 = (TextView)convertView.findViewById(R.id.note_content);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.date1.setText((String)data.get(position).get("date"));
            holder.title1.setText((String)data.get(position).get("title"));
            holder.content1.setText((String)data.get(position).get("content"));

            return convertView;
        }
    }
}