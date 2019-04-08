package com.example.zh_eastsun.xiyouthought.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.fragment.CourseFragment;
import com.example.zh_eastsun.xiyouthought.fragment.GradeFragment;
import com.example.zh_eastsun.xiyouthought.fragment.UserFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //各个功能的Fragment
    private CourseFragment courseFragment;
    private GradeFragment gradeFragment;
    private UserFragment userFragment;

    //各个碎片的布局
    private View courseLayout;
    private View gradeLayout;
    private View userLayout;

    //底部功能栏的控件
    private ImageView course_image;
    private TextView course_text;
    private ImageView grade_image;
    private TextView grade_text;
    private ImageView user_image;
    private TextView user_text;

    //碎片管理器
    private FragmentManager fragmentManager;

    private void initView(){
        courseLayout = findViewById(R.id.course_layout);
        gradeLayout = findViewById(R.id.grade_layout);
        userLayout = findViewById(R.id.user_layout);
        course_image = findViewById(R.id.course_image);
        course_text = findViewById(R.id.course_text);
        grade_image = findViewById(R.id.grade_image);
        grade_text = findViewById(R.id.grade_text);
        user_image = findViewById(R.id.user_image);
        user_text = findViewById(R.id.user_text);

        courseLayout.setOnClickListener(this);
        gradeLayout.setOnClickListener(this);
        userLayout.setOnClickListener(this);
    }

    private void setTabSelection(int index){
        //每次选中之前先清除之前的选中状态
        clearSelection();
        //开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //先隐藏所有Fragment以防有多个Fragment在屏幕上同时显示的情况发生
        hideFragments(transaction);

        switch (index){
            case 0:
                //当点击了课程tab时，改变图片和文字的颜色
                course_image.setImageResource(R.drawable.school_timetable_selected);
                course_text.setTextColor(getResources().getColor(R.color.selected));
                //检查课程的Fragment是否为空，如果为空则新建一个并显示出来，否则直接显示出来
                if(courseFragment == null){
                    courseFragment = new CourseFragment();
                    transaction.add(R.id.content,courseFragment);
                }else{
                    transaction.show(courseFragment);
                }
                break;
            case 1:
                //当点击了成绩tab时，改变图片和文字的颜色
                grade_image.setImageResource(R.drawable.grade_selected);
                grade_text.setTextColor(getResources().getColor(R.color.selected));
                //检查成绩的Fragment是否为空，如果为空则新建一个并显示出来，否则直接显示出来
                if(gradeFragment == null){
                    gradeFragment = new GradeFragment();
                    transaction.add(R.id.content,gradeFragment);
                }else{
                    transaction.show(gradeFragment);
                }
                break;
            case 2:
                //当点击了用户tab时，改变图片和文字的颜色
                user_image.setImageResource(R.drawable.user_selected);
                user_text.setTextColor(getResources().getColor(R.color.selected));
                //检查用户的Fragment是否为空，如果为空则新建一个并显示出来，否则直接显示出来
                if(userFragment == null){
                    userFragment = new UserFragment();
                    transaction.add(R.id.content,userFragment);
                }else{
                    transaction.show(userFragment);
                }
                break;
                default:
                    break;
        }
        transaction.commit();

    }

    private void clearSelection(){
        course_image.setImageResource(R.drawable.school_timetable);
        course_text.setTextColor(getResources().getColor(R.color.unselected));
        grade_image.setImageResource(R.drawable.grade);
        grade_text.setTextColor(getResources().getColor(R.color.unselected));
        user_image.setImageResource(R.drawable.user);
        user_text.setTextColor(getResources().getColor(R.color.unselected));
    }

    private void hideFragments(FragmentTransaction transaction){
        if(courseFragment != null){
            transaction.hide(courseFragment);
        }
        if(gradeFragment != null){
            transaction.hide(gradeFragment);
        }
        if(userFragment != null){
            transaction.hide(userFragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //初始化控件
        initView();
        fragmentManager = getSupportFragmentManager();
        //第一次启动时默认处在课程界面
        setTabSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course_layout:
                setTabSelection(0);
                break;
            case R.id.grade_layout:
                setTabSelection(1);
                break;
            case R.id.user_layout:
                setTabSelection(2);
                break;
                default:
                    break;
        }
    }
}
