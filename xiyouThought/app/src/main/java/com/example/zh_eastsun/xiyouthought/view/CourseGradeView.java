package com.example.zh_eastsun.xiyouthought.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zh_eastsun.xiyouthought.R;

/**
 * 管理成绩显示的自定义view
 * @author zh_eastsun
 * @version 1.0.0
 */
public class CourseGradeView extends LinearLayout {

    private TextView subjectText;
    private TextView gradeText;
    private TextView achievementPointText;
    private TextView subJectTypeText;

    private void init(){
        subjectText = findViewById(R.id.subject_text);
        gradeText = findViewById(R.id.grade_text);
        achievementPointText = findViewById(R.id.achievement_point_text);
        subJectTypeText = findViewById(R.id.subject_type_text);
    }

    public CourseGradeView(Context context) {
        super(context);
    }

    public CourseGradeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.course_grade_item,this);
        init();
    }

    /**
     * 提供给外部调用设置TextView内容的方法
     * @param subject 科目名称
     * @param grade 成绩
     * @param achievementPoint 绩点
     * @param subjectType 科目类型(必修/选修/选修(尔雅))
     */
    public void setSubjectGrade(String subject,String grade,String achievementPoint,String subjectType){
        subjectText.setText(subject);
        gradeText.setText(grade);
        achievementPointText.setText(achievementPoint);
        subJectTypeText.setText(subjectType);
    }
}
