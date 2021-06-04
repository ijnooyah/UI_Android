package com.kh.ui_android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter {
    Context context;
    int view;
    List<StudentVo> list;

    public MyListAdapter(Context context, int view, List<StudentVo> list) {
        this.context = context;
        this.view = view;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(this.context, this.view, null);
        }
        TextView tvSNo, tvSName, tvSYear, tvGender, tvMajor, tvScore;
        tvSNo = convertView.findViewById(R.id.tvSNo);
        tvSName = convertView.findViewById(R.id.tvSName);
        tvSYear = convertView.findViewById(R.id.tvSYear);
        tvGender = convertView.findViewById(R.id.tvGender);
        tvMajor = convertView.findViewById(R.id.tvMajor);
        tvScore = convertView.findViewById(R.id.tvScore);

        StudentVo vo = list.get(position);
        tvSNo.setText(vo.getSno());
        tvSName.setText(vo.getSname());
        tvSYear.setText(String.valueOf(vo.getSyear()));
        tvGender.setText(vo.getGender());
        tvMajor.setText(vo.getMajor());
        tvScore.setText(String.valueOf(vo.getScore()));
        return convertView;
    }
}
