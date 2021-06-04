package com.kh.ui_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

            lvAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //다이얼로그 만들기
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("수정또는 삭제 하기");
                    View dlgView = View.inflate(MainActivity.this, R.layout.dialog_content, null);
                    dlg.setView(dlgView);
                    //다이얼로그에 학생상세정보 끌고오기
                    StudentVo vo = list.get(position);
                    EditText edtSNo2, edtSname2, edtSYear2, edtMajor2, edtScore2;
                    RadioGroup rGroup2;
                    RadioButton rdoMale2, rdoFemale2;
                    Button btnUpdate, btnDelete;
                    edtSNo2 = dlgView.findViewById(R.id.edtSNo2);
                    edtSname2 = dlgView.findViewById(R.id.edtSName2);
                    edtSYear2 = dlgView.findViewById(R.id.edtSYear2);
                    edtMajor2 = dlgView.findViewById(R.id.edtMajor2);
                    edtScore2 = dlgView.findViewById(R.id.edtScore2);
                    rGroup2 = dlgView.findViewById(R.id.rGroup2);
                    rdoMale2 = dlgView.findViewById(R.id.rdoMale2);
                    rdoFemale2 = dlgView.findViewById(R.id.rdoFemale2);
                    btnUpdate = dlgView.findViewById(R.id.btnUpdate);
                    btnDelete = dlgView.findViewById(R.id.btnDelete);
                    edtSNo2.setText(vo.getSno());
                    edtSname2.setText(vo.getSname());
                    edtSYear2.setText(String.valueOf(vo.getSyear()));
                    edtMajor2.setText(vo.getMajor());
                    edtScore2.setText(String.valueOf(vo.getScore()));
                    if (vo.getGender().equals("남")){
                        rdoMale2.setChecked(true);
                    } else {
                        rdoFemale2.setChecked(true);
                    }
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtSNo2.setFocusable(true);
                            edtSname2.setFocusable(true);
                            edtSYear2.setFocusable(true);
                            edtMajor2.setFocusable(true);
                            edtScore2.setFocusable(true);
                            rdoMale2.setClickable(true);
                            edtSNo2.setFocusable(true);
                            rdoFemale2.setClickable(true);
                            boolean result = edtMajor2.isFocusable();
                            Log.d("mytag", String.valueOf(result));
                        }
                    });
                    dlg.setPositiveButton("닫기", null);

                    dlg.show();
                }
            });
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