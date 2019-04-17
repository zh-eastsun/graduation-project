package com.example.zh_eastsun.xiyouthought.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

    private String schoolYear;
    private String schoolTerm;

    private static String termOne = "3";
    private static String termTwo = "12";

    private QueryCourseGradeCallback queryCourseGradeCallback;

    public interface QueryCourseGradeCallback{
        public void query();
    }

    public void setQueryCourseGradeCallback(QueryCourseGradeCallback queryCourseGradeCallback){
        this.queryCourseGradeCallback = queryCourseGradeCallback;
    }

    private void init(Context context) {
        //获取当前系统时间
        String sysYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        //默认学年为当前系统时间学年
        schoolYear = sysYear;
        //将当前时间之前4年加入学年数组
        final ArrayList<String> schoolYearArray = new ArrayList<>();
        for (int index = 0; index < 5; index++){
            schoolYearArray.add(String.valueOf(Integer.valueOf(sysYear)-index));
        }
        //将两学期加入学期数组
        ArrayList<String> schoolTermArray = new ArrayList<>();
        schoolTermArray.add("第一学期");
        schoolTermArray.add("第二学期");
        //默认学期为第一学期
        schoolTerm = termOne;

        //初始化控件
        chooseSchoolYear = findViewById(R.id.choose_school_year);
        chooseSchoolTerm = findViewById(R.id.choose_school_term);
        query = findViewById(R.id.query_school_timetable);

        //为spinner初始化适配器
        ArrayAdapter<String> schoolYearArrayAdapter = new ArrayAdapter<>(context,R.layout.spinner_item,schoolYearArray);
        ArrayAdapter<String> schoolTermArrayAdapter = new ArrayAdapter<>(context,R.layout.spinner_item,schoolTermArray);
        //为适配器设置样式
        schoolYearArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        schoolTermArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //为spinner设置适配器
        chooseSchoolYear.setAdapter(schoolYearArrayAdapter);
        chooseSchoolTerm.setAdapter(schoolTermArrayAdapter);

        chooseSchoolYear.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolYear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                schoolYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            }
        });
        chooseSchoolTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    schoolTerm = termOne;
                }else{
                    schoolTerm = termTwo;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                schoolTerm = termOne;
            }
        });

        query.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                queryCourseGradeCallback.query();
            }
        });


    }

    public String getSchoolYear(){
        return this.schoolYear;
    }

    public String getSchoolTerm(){
        return this.schoolTerm;
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
