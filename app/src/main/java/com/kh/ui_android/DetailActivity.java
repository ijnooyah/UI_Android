package com.kh.ui_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    Spinner spinnerDetail;
    EditText edtDetail;
    Button btnSelectDetail;
    ListView lvDetail;
    ArrayAdapter<String> sAdapter;
    MyListAdapter lAdapter;
    MyDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        spinnerDetail = findViewById(R.id.spinnerDetail);
        edtDetail = findViewById(R.id.edtDetail);
        btnSelectDetail = findViewById(R.id.btnSelectDetail);
        lvDetail = findViewById(R.id.lvDetail);
        helper = new MyDBHelper(getApplicationContext(), "StudentDB", null, 1);

        String[] data = {"학번", "전공"};
        sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        spinnerDetail.setAdapter(sAdapter);

        String option = "";
        spinnerDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("mytag", data[position]);
                option = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnSelectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data[])
                List<StudentVo> list = new ArrayList<>();
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
            }
        });
    }
}