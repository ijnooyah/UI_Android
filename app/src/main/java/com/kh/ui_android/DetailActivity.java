package com.kh.ui_android;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
                            "     where major = ?" +
                            "     order by sno";
                    Cursor cursor = db.rawQuery(sql, new String[]{major});
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

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //다이얼로그 만들기
                AlertDialog.Builder dlg = new AlertDialog.Builder(DetailActivity.this);
                dlg.setTitle("수정또는 삭제 하기");
                View dlgView = View.inflate(DetailActivity.this, R.layout.dialog_content, null);
                dlg.setView(dlgView);
                //다이얼로그에 학생상세정보 끌고오기
                StudentVo vo = list.get(position);
                EditText edtSNo2, edtSName2, edtSYear2, edtMajor2, edtScore2;
                RadioButton rdoMale2, rdoFemale2;
                Button btnUpdate, btnDelete, btnCheckNum2;
                TextView tvCheckResult2;
                edtSNo2 = dlgView.findViewById(R.id.edtSNo2);
                edtSName2 = dlgView.findViewById(R.id.edtSName2);
                edtSYear2 = dlgView.findViewById(R.id.edtSYear2);
                edtMajor2 = dlgView.findViewById(R.id.edtMajor2);
                edtScore2 = dlgView.findViewById(R.id.edtScore2);
                rdoMale2 = dlgView.findViewById(R.id.rdoMale2);
                rdoFemale2 = dlgView.findViewById(R.id.rdoFemale2);
                btnUpdate = dlgView.findViewById(R.id.btnUpdate);
                btnDelete = dlgView.findViewById(R.id.btnDelete);
                btnCheckNum2 = dlgView.findViewById(R.id.btnCheckNum2);
                tvCheckResult2 = dlgView.findViewById(R.id.tvCheckResult2);
                edtSNo2.setText(vo.getSno());
                edtSName2.setText(vo.getSname());
                edtSYear2.setText(String.valueOf(vo.getSyear()));
                edtMajor2.setText(vo.getMajor());
                edtScore2.setText(String.valueOf(vo.getScore()));
                if (vo.getGender().equals("남")) {
                    rdoMale2.setChecked(true);
                } else {
                    rdoFemale2.setChecked(true);
                }

                btnCheckNum2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sno = edtSNo2.getText().toString();
                        if (sno.trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), "학번 기입 필수", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            try {
                                Integer.parseInt(sno);
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "학번은 숫자로 입력해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        SQLiteDatabase db = helper.getReadableDatabase();
                        String sql = "select count(*) from tbl_student" +
                                "     where sno = '" + sno + "'";
                        Cursor cursor = db.rawQuery(sql, null);
                        if(cursor.moveToNext()) {
                            int count = cursor.getInt(0);
                            if (count == 0) {
                                // 학번중복되는거 없음
                                tvCheckResult2.setText("등록 가능한 학번입니다.");
                            } else {
                                //학번 중복됨
                                tvCheckResult2.setText("이미 등록된 학번입니다.");
                            }
                        }
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tvText = tvCheckResult2.getText().toString();


                        edtSNo2.setEnabled(true);
                        edtSName2.setEnabled(true);
                        edtSYear2.setEnabled(true);
                        edtMajor2.setEnabled(true);
                        edtScore2.setEnabled(true);
                        edtSNo2.setEnabled(true);
                        rdoMale2.setClickable(true);
                        rdoFemale2.setClickable(true);

                        String sno = edtSNo2.getText().toString();
                        String sname = edtSName2.getText().toString();
                        String strYear = edtSYear2.getText().toString();
                        int syear;
                        String gender = null;
                        String major = edtMajor2.getText().toString();
                        String strScore = edtScore2.getText().toString();
                        int score;

                        if (rdoMale2.isChecked()) {
                            gender = rdoMale2.getText().toString();
                        } else if (rdoFemale2.isChecked()) {
                            gender = rdoFemale2.getText().toString();
                        }
                        // 입력사항 조건 설정


                        if (sno.trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), "학번 기입 필수", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            try {
                                Integer.parseInt(sno);
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "학번은 숫자로 입력해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
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
                                if (syear > 4 || syear < 1) {
                                    Toast.makeText(getApplicationContext(), "1~4사이의 숫자만 입력 가능", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "1~4사이의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                return;
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
                                if (score > 100 || score < 1) {
                                    Toast.makeText(getApplicationContext(), "1~100사이의 숫자만 입력 가능", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "1~100사이의 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        //Update
                        String btnText = btnUpdate.getText().toString();
                        if (btnText.equals("수정하기")) {

                            btnUpdate.setText("수정완료");
                        } else if (btnText.equals("수정완료")) {
                            if (tvText.trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), "학번중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (tvText.equals("이미 등록된 학번입니다.")) {
                                Toast.makeText(getApplicationContext(), "학번을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SQLiteDatabase db = helper.getWritableDatabase();
                            String sql = "update tbl_student" +
                                    "     set sno = ?," +
                                    "         sname = ?," +
                                    "         syear = ?," +
                                    "         gender = ?," +
                                    "         major = ?," +
                                    "         score = ?" +
                                    "     where sno = ?";
                            Object[] params = {sno, sname, syear, gender, major, score, vo.getSno()};
                            db.execSQL(sql, params);
                            btnSelectDetail.callOnClick();
                            btnUpdate.setText("수정하기");
                        }


                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sql = "delete from tbl_student" +
                                "     where sno = '" + vo.getSno() + "'";
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL(sql);
                        btnSelectDetail.callOnClick();
                    }
                });
                dlg.setPositiveButton("닫기", null);

                dlg.show();

            }
        });
    }
}