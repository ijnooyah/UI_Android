package com.kh.ui_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSelectAll, btnDetail, btnInsert;
    ListView lvAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectAll = findViewById(R.id.btnSelectAll);
        btnDetail = findViewById(R.id.btnDetail);
        btnInsert = findViewById(R.id.btnInsert);
        lvAll = findViewById(R.id.lvAll);

        btnSelectAll.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        btnInsert.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectAll) {
//            Log.d("mytag", "selectAll");
        } else if (v == btnDetail) {
//            Log.d("mytag", "Detail");
        } else if (v == btnInsert) {
//            Log.d("mytag", "Insert");
        }
    }
}