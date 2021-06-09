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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSelect, btnInsert;
    ListView listview;
    MyListAdapter adapter;
    MyDBHelper helper;
    List<StudentVo> list;
    Spinner spinner;
    EditText edtSelect;
    ArrayAdapter<String> sAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSelect = findViewById(R.id.btnSelect);
        btnInsert = findViewById(R.id.btnInsert);
        btnInsert = findViewById(R.id.btnInsert);
        listview = findViewById(R.id.listview);
        spinner = findViewById(R.id.spinner);
        edtSelect = findViewById(R.id.edtSelect);
        String[] data = {"전체", "이름", "전공"};
        sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        spinner.setAdapter(sAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        helper = new MyDBHelper(getApplicationContext(), "StudentDB", null, 1);


        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == btnSelect) {
            String slectedItem = spinner.getSelectedItem().toString();
            if (slectedItem.equals("이름")) {
                Log.d("mytag", "학번선택");
                list = new ArrayList<>();
                String name = edtSelect.getText().toString();
                if(name.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
//            Log.d("mytag", "selectAll");
                SQLiteDatabase db = helper.getReadableDatabase();
                String sql = "select * from tbl_student" +
                        "     where sname like '%" + name + "%'" +
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
                adapter = new MyListAdapter(MainActivity.this, R.layout.listview, list);
                listview.setAdapter(adapter);
                cursor.close();
            } else if (slectedItem.equals("전공")) {
                Log.d("mytag", "전공선택");
                list = new ArrayList<>();
                String smajor = edtSelect.getText().toString().trim();
                if(smajor.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
//            Log.d("mytag", "selectAll");
                SQLiteDatabase db = helper.getReadableDatabase();
                String sql = "select * from tbl_student" +
                        "     where major like '%" + smajor + "%'" +
                        "     order by sno";
                Cursor cursor = db.rawQuery(sql, null);
                Log.d("mytag", cursor.toString());
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
                adapter = new MyListAdapter(MainActivity.this, R.layout.listview, list);
                listview.setAdapter(adapter);
                cursor.close();
            } else if (slectedItem.equals("전체")) {
                list = new ArrayList<>();

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
                listview.setAdapter(adapter);
            }

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //다이얼로그 만들기
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    View dlgView = View.inflate(MainActivity.this, R.layout.dialog_content, null);
                    dlg.setView(dlgView);
                    //다이얼로그에 학생상세정보 끌고오기
                    StudentVo vo = list.get(position);
                    EditText edtSNo2, edtSName2, edtSYear2, edtMajor2, edtScore2;
                    RadioButton rdoMale2, rdoFemale2;
                    Button btnUpdate, btnDelete;
                    edtSNo2 = dlgView.findViewById(R.id.edtSNo2);
                    edtSName2 = dlgView.findViewById(R.id.edtSName2);
                    edtSYear2 = dlgView.findViewById(R.id.edtSYear2);
                    edtMajor2 = dlgView.findViewById(R.id.edtMajor2);
                    edtScore2 = dlgView.findViewById(R.id.edtScore2);
                    rdoMale2 = dlgView.findViewById(R.id.rdoMale2);
                    rdoFemale2 = dlgView.findViewById(R.id.rdoFemale2);
                    btnUpdate = dlgView.findViewById(R.id.btnUpdate);
                    btnDelete = dlgView.findViewById(R.id.btnDelete);
                    edtSNo2.setText(vo.getSno());
                    edtSName2.setText(vo.getSname());
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

                            edtSName2.setEnabled(true);
                            edtSYear2.setEnabled(true);
                            edtMajor2.setEnabled(true);
                            edtScore2.setEnabled(true);
                            rdoMale2.setClickable(true);
                            rdoFemale2.setClickable(true);

                            String sname = edtSName2.getText().toString();
                            String strYear = edtSYear2.getText().toString();
                            int syear;
                            String gender = null;
                            String major = edtMajor2.getText().toString();
                            String strScore = edtScore2.getText().toString();
                            int score;

                            if(rdoMale2.isChecked()) {
                                gender = rdoMale2.getText().toString();
                            } else if (rdoFemale2.isChecked()) {
                                gender = rdoFemale2.getText().toString();
                            }
                            // 입력사항 조건 설정


                            if (sname.trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), "이름 기입 필수", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (strYear.trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), "학년 기입 필수", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                try {
                                    syear = Integer.parseInt(strYear);
                                    if(syear > 4 || syear < 1) {
                                        Toast.makeText(getApplicationContext(), "1~4사이의 숫자만 입력 가능", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "1~4사이의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();                        return;
                                }

                            }
                            if (gender == null) {
                                Toast.makeText(getApplicationContext(), "성별란 체크 필수", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (major.trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), "전공 기입 필수", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (strScore.trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), "점수 기입 필수", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                try {
                                    score = Integer.parseInt(strScore);
                                    if(score > 100 || score < 1) {
                                        Toast.makeText(getApplicationContext(), "1~100사이의 숫자만 입력 가능", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "1~100사이의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();                        return;
                                }

                            }

                            //Update
                            String btnText = btnUpdate.getText().toString();
                            if (btnText.equals("수정하기")) {

                                btnUpdate.setText("수정완료");
                            } else if (btnText.equals("수정완료")) {
                                SQLiteDatabase db = helper.getWritableDatabase();
                                String sql = "update tbl_student" +
                                        "     set sname = ?," +
                                        "         syear = ?," +
                                        "         gender = ?," +
                                        "         major = ?," +
                                        "         score = ?" +
                                        "     where sno = ?";
                                Object[] params = {sname, syear, gender, major, score, vo.getSno()};
                                db.execSQL(sql, params);
                                Toast.makeText( MainActivity.this, "수정완료", Toast.LENGTH_SHORT).show();
                                btnUpdate.setText("수정하기");
                            }


                        }
                    });
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sql = "delete from tbl_student" +
                                    "     where sno = '" + vo.getSno() +"'";
                            SQLiteDatabase db = helper.getWritableDatabase();
                            db.execSQL(sql);
                            Toast.makeText( MainActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setPositiveButton("닫기", null);

                    dlg.show();
                }
            });
        } else if (v == btnInsert) {
//            Log.d("mytag", "Insert");
            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        }
    }
}