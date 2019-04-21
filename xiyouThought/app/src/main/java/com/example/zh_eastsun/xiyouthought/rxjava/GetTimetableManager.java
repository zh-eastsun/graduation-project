package com.example.zh_eastsun.xiyouthought.rxjava;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.javabean.SchoolTimetable;
import com.example.zh_eastsun.xiyouthought.net.TimetableRequest;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GetTimetableManager {

    private Context context;
    private TimetableRequest timetableRequest;
    private UpdateTimetableViewCallback callback;

    public interface UpdateTimetableViewCallback{
        void update(ArrayList<SchoolTimetable.Timetable> timetableList);
    }

    public void setCallback(UpdateTimetableViewCallback callback) {
        this.callback = callback;
    }

    public GetTimetableManager(Context context) {
        this.context = context;
        timetableRequest = new TimetableRequest();
    }

    public void getTimetable() {
        //使用dialog以防用户在此期间多次点击查询操作
        final AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("正在获取课表")
                .setMessage("请耐心等待...")
                .setView(R.layout.wait_progress_bar)
                .setCancelable(false)
                .show();

        Observable.just("")
                .map(new Function<String, ArrayList<SchoolTimetable.Timetable>>() {
                    @Override
                    public ArrayList<SchoolTimetable.Timetable> apply(String s) {
                        return timetableRequest.getTimeTable();
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<SchoolTimetable.Timetable>>() {
                    @Override
                    public void accept(ArrayList<SchoolTimetable.Timetable> timetables) throws Exception {
                        callback.update(timetables);
                        dialog.dismiss();
                    }
                });
    }
}
