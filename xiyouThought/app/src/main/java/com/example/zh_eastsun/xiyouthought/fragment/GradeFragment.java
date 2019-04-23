package com.example.zh_eastsun.xiyouthought.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.adapter.GradeRecyclerViewAdapter;
import com.example.zh_eastsun.xiyouthought.javabean.CourseGrade;
import com.example.zh_eastsun.xiyouthought.net.GradeRequest;
import com.example.zh_eastsun.xiyouthought.net.NetRequestCallback;
import com.example.zh_eastsun.xiyouthought.rxjava.GetCourseGradeManager;
import com.example.zh_eastsun.xiyouthought.view.ChooseTermView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 显示成绩的Fragment
 *
 * @author zh_eastsun
 * @version 1.0.0
 */
public class GradeFragment extends Fragment {

    private ChooseTermView chooseTermView;
    private RecyclerView recyclerView;
    private GetCourseGradeManager getCourseGradeManager;
    private ArrayList<CourseGrade.Grade> gradeArrayList;
    private HashMap<String, String> userSelectedItems;


    public GradeFragment() {

    }

    /**
     * 初始化控件
     * @param view 根布局的实例
     */
    private void initView(View view) {

        getCourseGradeManager = new GetCourseGradeManager(getContext());
        chooseTermView = view.findViewById(R.id.choose_and_query);
        recyclerView = view.findViewById(R.id.course_grade_recyclerview);
        //设置用户在Spinner选择条目后回调的接口，主要用于将数据传输出去
        getCourseGradeManager.setSpinnerItemSelectedCallback(new GetCourseGradeManager.SpinnerItemSelectedCallback() {
            @Override
            public HashMap<String, String> getUserSelectedItems() {
                return userSelectedItems;
            }
        });
        //设置RecyclerView需要设置内部条目View的时候的回调接口，主要用于接收外部数据
        getCourseGradeManager.setSetRecyclerViewItemsCallback(new GetCourseGradeManager.SetRecyclerViewItemsCallback() {
            @Override
            public void setRecyclerViewItems(ArrayList<CourseGrade.Grade> courseGrades) {
                gradeArrayList = courseGrades;
            }
        });
        //设置网络请求已经得到回应的回调，主要用于在网络请求完成后更新RecyclerView的数据
        getCourseGradeManager.setNetRequestCallback(new NetRequestCallback() {
            @Override
            public void success() {
                GradeRecyclerViewAdapter adapter = new GradeRecyclerViewAdapter(gradeArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void failed() {
                Toast.makeText(getContext(),"数据请求失败,请确认网络环境",Toast.LENGTH_SHORT).show();
            }
        });
        //设置用户查询成绩的回调
        chooseTermView.setQueryCourseGradeCallback(new ChooseTermView.QueryCourseGradeCallback() {
            @Override
            public void query() {
                final String schoolYear = chooseTermView.getSchoolYear();
                final String schoolTerm = chooseTermView.getSchoolTerm();
                userSelectedItems = new HashMap<>();
                userSelectedItems.put("schoolYear", schoolYear);
                userSelectedItems.put("schoolTerm", schoolTerm);
                getCourseGradeManager.getCourseGrade();
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
