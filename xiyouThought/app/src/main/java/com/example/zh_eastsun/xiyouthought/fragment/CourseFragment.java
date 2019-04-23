package com.example.zh_eastsun.xiyouthought.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.javabean.SchoolTimetable;
import com.example.zh_eastsun.xiyouthought.net.TimetableRequest;
import com.example.zh_eastsun.xiyouthought.rxjava.GetTimetableManager;
import com.zhuangfei.timetable.TimetableView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {

    private TimetableView timetableView;
    private Looper looper = Looper.getMainLooper();
    private Handler handler = new Handler(looper);


    public CourseFragment() {
        // Required empty public constructor
    }

    public void initView(View view) {
        timetableView = view.findViewById(R.id.id_timetableView);
        //使用dialog以防用户在此期间多次点击查询操作
        final AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getContext())
                .setTitle("正在请求课表")
                .setMessage("请等待...")
                .setView(R.layout.wait_progress_bar)
                .setCancelable(false)
                .show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final TimetableRequest timetableRequest = new TimetableRequest();
                final ArrayList<SchoolTimetable.Timetable> timetables;
                timetables = timetableRequest.getTimeTable();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timetableView.source(timetables)
                                .showView();
                        dialog.dismiss();
                    }
                });
            }
        }).start();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        initView(view);
        return view;
    }

}
