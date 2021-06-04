package com.kh.ui_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    EditText edtSNo, edtSName, edtSYear, edtMajor, edtScore;
    RadioGroup rGroup;
    RadioButton rdoMale, rdoFemale;
    Button btnRegister;
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
        helper = new MyDBHelper(getApplicationContext(), "StudentDB", null, 1);


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

                //다이얼로그 띄우기 (확인버튼 누르면 메인화면으로 돌아가고 취소 버튼누르면 그대로 있게)
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InsertActivity.this, MainActivity.class));
    }
}
