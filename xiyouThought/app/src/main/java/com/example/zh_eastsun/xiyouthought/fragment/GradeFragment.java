package com.example.zh_eastsun.xiyouthought.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.javabean.CourseGrade;
import com.example.zh_eastsun.xiyouthought.net.GradeRequest;
import com.example.zh_eastsun.xiyouthought.view.ChooseTermView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends Fragment {

    private ChooseTermView chooseTermView;
    private GradeRequest gradeRequest;
    private ArrayList<CourseGrade> courseGradeArrayList;


    public GradeFragment() {

    }

    private void initView (View view){
        gradeRequest = new GradeRequest();
        chooseTermView = view.findViewById(R.id.choose_and_query);
        chooseTermView.setQueryCourseGradeCallback(new ChooseTermView.QueryCourseGradeCallback() {
            @Override
            public void query() {
                final String schoolYear = chooseTermView.getSchoolYear();
                final String schoolTerm = chooseTermView.getSchoolTerm();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        courseGradeArrayList = gradeRequest.getCourseGrade(schoolYear,schoolTerm);
                    }
                }).start();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        initView(view);
        return view;
    }

}
