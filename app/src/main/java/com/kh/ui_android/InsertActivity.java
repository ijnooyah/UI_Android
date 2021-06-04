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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    EditText edtSNo, edtSName, edtSYear, edtMajor, edtScore;
    RadioGroup rGroup;
    RadioButton rdoMale, rdoFemale;
    Button btnRegister, btnCheckNum;
    TextView tvCheckResult;
    MyDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtSNo = findViewById(R.id.edtSNo);
        edtSName = findViewById(R.id.edtSName);
        edtSYear = findViewById(R.id.edtSYear);
        edtMajor = findViewById(R.id.edtMajor);
        edtScore = findViewById(R.id.edtScore);
        rGroup = findViewById(R.id.rGroup);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);
        btnRegister = findViewById(R.id.btnRegister);
        btnCheckNum = findViewById(R.id.btnCheckNum);
        tvCheckResult = findViewById(R.id.tvCheckResult);
        helper = new MyDBHelper(getApplicationContext(), "StudentDB", null, 1);

        btnCheckNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sno = edtSNo.getText().toString();
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
                        tvCheckResult.setText("등록 가능한 학번입니다.");
                        btnRegister.setEnabled(true);
                    } else {
                        //학번 중복됨
                        tvCheckResult.setText("이미 등록된 학번입니다.");
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sno = edtSNo.getText().toString();
                String sname = edtSName.getText().toString();
                String strYear = edtSYear.getText().toString();
                int syear;
                String gender = null;
                String major = edtMajor.getText().toString();
                String strScore = edtScore.getText().toString();
                int score;

                if(rdoMale.isChecked()) {
                    gender = rdoMale.getText().toString();
                } else if (rdoFemale.isChecked()) {
                    gender = rdoFemale.getText().toString();
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

                //insert
//                StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "insert into tbl_student" +
                        "     values(?, ?, ?, ?, ?, ?)";
                Object[] params = {sno, sname, syear, gender, major, score};
                db.execSQL(sql, params);

                //다이얼로그 띄우기 (확인버튼 누르면 메인화면으로 돌아가고 닫기 버튼누르면 그대로 있게)
                AlertDialog.Builder dlg = new AlertDialog.Builder(InsertActivity.this);
                dlg.setTitle("등록완료");
                View dlgView = View.inflate(InsertActivity.this, R.layout.dialog_insert, null);
                dlg.setView(dlgView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                        startActivity(intent);
                        InsertActivity.this.finish();

                    }
                });

                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InsertActivity.this, MainActivity.class));
    }
}
