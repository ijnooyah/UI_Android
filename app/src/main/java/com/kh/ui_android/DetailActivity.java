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
    List<StudentVo> list;
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

        btnSelectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slectedItem = spinnerDetail.getSelectedItem().toString();
                if (slectedItem.equals("학번")) {
                    Log.d("mytag", "학번선택");
                    list = new ArrayList<>();
                    String sno = edtDetail.getText().toString();
//            Log.d("mytag", "selectAll");
                    SQLiteDatabase db = helper.getReadableDatabase();
                    String sql = "select * from tbl_student" +
                            "     where sno = " + sno +
                            "     order by sno";
                    Cursor cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        String sname = cursor.getString(1);
                        int syear = cursor.getInt(2);
                        String gender = cursor.getString(3);
                        String major = cursor.getString(4);
                        int score = cursor.getInt(5);
                        StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
                        list.add(vo);
                    }
                    lAdapter = new MyListAdapter(DetailActivity.this, R.layout.listview, list);
                    lvDetail.setAdapter(lAdapter);
                    cursor.close();
                } else if (slectedItem.equals("전공")) {
                    Log.d("mytag", "전공선택");
                    list = new ArrayList<>();
                    String major = edtDetail.getText().toString().trim();
//            Log.d("mytag", "selectAll");
                    SQLiteDatabase db = helper.getReadableDatabase();
                    String sql = "select * from tbl_student" +
                            "     where major = '" + major + "'" +
                            "     order by sno";
                    Cursor cursor = db.rawQuery(sql, null);
                    Log.d("mytag", cursor.toString());
                    while (cursor.moveToNext()) {
                        String sno = cursor.getString(0);
                        String sname = cursor.getString(1);
                        int syear = cursor.getInt(2);
                        String gender = cursor.getString(3);
                        int score = cursor.getInt(5);
                        StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
                        list.add(vo);
                    }
                    lAdapter = new MyListAdapter(DetailActivity.this, R.layout.listview, list);
                    lvDetail.setAdapter(lAdapter);
                    cursor.close();
                }

            }
        });
    }
}