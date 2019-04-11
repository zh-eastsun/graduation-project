package com.example.zh_eastsun.xiyouthought.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.zh_eastsun.xiyouthought.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ChooseTermView extends LinearLayout {

    private Spinner chooseSchoolYear;
    private Spinner chooseSchoolTerm;
    private Button query;

    private void init(Context context) {
        //获取当前系统时间
        String sysYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        //将当前时间之前4年加入学年数组
        ArrayList<String> schoolYearArray = new ArrayList<>();
        for (int index = 0; index < 5; index++){
            schoolYearArray.add(String.valueOf(Integer.valueOf(sysYear)-index));
        }
        //将两学期加入学期数组
        ArrayList<String> schoolTermArray = new ArrayList<>();
        schoolTermArray.add("第一学期");
        schoolTermArray.add("第二学期");

        //初始化控件
        chooseSchoolYear = findViewById(R.id.choose_school_year);
        chooseSchoolTerm = findViewById(R.id.choose_school_term);
        query = findViewById(R.id.query_school_timetable);

        //为spinner初始化适配器
        ArrayAdapter<String> schoolYearArrayAdapter = new ArrayAdapter<>(context,R.layout.spinner_item,schoolYearArray);
        ArrayAdapter<String> schoolTermArrayAdapter = new ArrayAdapter<>(context,R.layout.spinner_item,schoolTermArray);
        //为适配器设置样式
        schoolYearArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        schoolTermArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //为spinner设置适配器
        chooseSchoolYear.setAdapter(schoolYearArrayAdapter);
        chooseSchoolTerm.setAdapter(schoolTermArrayAdapter);

    }

    public ChooseTermView(Context context) {
        super(context);
    }

    public ChooseTermView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_select,this);
        init(context);

    }
}
