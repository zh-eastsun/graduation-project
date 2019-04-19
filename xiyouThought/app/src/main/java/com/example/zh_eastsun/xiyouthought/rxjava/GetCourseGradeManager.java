package com.example.zh_eastsun.xiyouthought.rxjava;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.javabean.CourseGrade;
import com.example.zh_eastsun.xiyouthought.net.GradeRequest;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取成绩的管理类
 *
 * @author zh_eastsun
 * @version 1.0.0
 */
public class GetCourseGradeManager {
    private Context context;
    private HashMap<String, String> userSelectedItems;
    private SpinnerItemSelectedCallback spinnerItemSelectedCallback;
    private SetRecyclerViewItemsCallback setRecyclerViewItemsCallback;
    private HaveAcceptResponseCallback haveAcceptResponseCallback;
    private GradeRequest gradeRequest;

    /**
     * 获取用户选择条目的接口
     *
     * @author zh_eastsun
     * @version 1.0.0
     */
    public interface SpinnerItemSelectedCallback {
        HashMap<String, String> getUserSelectedItems();
    }

    /**
     * 设置RecyclerView时传输数据的接口
     *
     * @author zh_eastsun
     * @version 1.0.0
     */
    public interface SetRecyclerViewItemsCallback {
        void setRecyclerViewItems(ArrayList<CourseGrade.Grade> courseGrades);
    }

    /**
     * 网络请求已经获取响应的接口
     *
     * @author zh_eastsun
     * @version 1.0.0
     */
    public interface HaveAcceptResponseCallback {
        void doWork();
    }

    public void setSpinnerItemSelectedCallback(SpinnerItemSelectedCallback callback) {
        this.spinnerItemSelectedCallback = callback;
    }

    public void setSetRecyclerViewItemsCallback(SetRecyclerViewItemsCallback callback) {
        this.setRecyclerViewItemsCallback = callback;
    }

    public void setHaveAcceptResponseCallback(HaveAcceptResponseCallback callback) {
        this.haveAcceptResponseCallback = callback;
    }

    public GetCourseGradeManager(Context context) {
        this.context = context;
        gradeRequest = new GradeRequest();
    }

    /**
     * 使用rxjava2进行异步请求的方法
     */
    public void getCourseGrade() {
        //使用dialog以防用户在此期间多次点击查询操作
        final AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("正在登录")
                .setMessage("请等待...")
                .setView(R.layout.wait_progress_bar)
                .setCancelable(false)
                .show();
        //获取用户选择的item
        userSelectedItems = spinnerItemSelectedCallback.getUserSelectedItems();
        //使用rxjava进行异步请求并切换线程改变ui
        Observable.just(userSelectedItems)
                .map(new Function<HashMap<String, String>, ArrayList<CourseGrade.Grade>>() {
                    @Override
                    public ArrayList<CourseGrade.Grade> apply(HashMap<String, String> stringStringHashMap) {
                        String schoolYear = userSelectedItems.get("schoolYear");
                        String schoolTerm = userSelectedItems.get("schoolTerm");
                        return gradeRequest.getCourseGrade(schoolYear, schoolTerm);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<CourseGrade.Grade>>() {
                    @Override
                    public void accept(ArrayList<CourseGrade.Grade> courseGrades) {
                        setRecyclerViewItemsCallback.setRecyclerViewItems(courseGrades);
                        haveAcceptResponseCallback.doWork();
                        dialog.dismiss();
                    }
                });

    }

}
