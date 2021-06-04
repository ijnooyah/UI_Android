package com.kh.ui_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSelectAll, btnDetail, btnInsert;
    ListView lvAll;
    MyListAdapter adapter;
    MyDBHelper helper;
    List<StudentVo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectAll = findViewById(R.id.btnSelectAll);
        btnDetail = findViewById(R.id.btnDetail);
        btnInsert = findViewById(R.id.btnInsert);
        lvAll = findViewById(R.id.lvAll);
        helper = new MyDBHelper(getApplicationContext(), "StudentDB", null, 1);


        btnSelectAll.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        btnInsert.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectAll) {
            list = new ArrayList<>();
//            Log.d("mytag", "selectAll");
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from tbl_student" +
                    "     order by sno";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String sno = cursor.getString(0);
                String sname = cursor.getString(1);
                int syear = cursor.getInt(2);
                String gender = cursor.getString(3);
                String major = cursor.getString(4);
                int score = cursor.getInt(5);
                StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
                list.add(vo);
            }
            adapter = new MyListAdapter(this, R.layout.listview, list);
            lvAll.setAdapter(adapter);
        } else if (v == btnDetail) {
//            Log.d("mytag", "Detail");
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
        } else if (v == btnInsert) {
//            Log.d("mytag", "Insert");
            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        }
    }
}