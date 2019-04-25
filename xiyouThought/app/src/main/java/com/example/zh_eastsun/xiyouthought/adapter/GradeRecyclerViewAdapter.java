package com.example.zh_eastsun.xiyouthought.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.javabean.CourseGrade;
import com.example.zh_eastsun.xiyouthought.view.CourseGradeView;

import java.util.ArrayList;


/**
 * 显示课程成绩的RecyclerView的Adapter
 * @author zh_eastsun
 * @version 1.0.0
 */
public class GradeRecyclerViewAdapter extends RecyclerView.Adapter<GradeRecyclerViewAdapter.GradeItemViewHolder> {

    private ArrayList<CourseGrade.Grade> courseGrades;

    public GradeRecyclerViewAdapter(ArrayList<CourseGrade.Grade> courseGrades){
        this.courseGrades = courseGrades;
    }

    @NonNull
    @Override
    public GradeItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_grade_view,viewGroup,false);
        GradeItemViewHolder viewHolder = new GradeItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GradeItemViewHolder gradeItemViewHolder, int i) {
        CourseGrade.Grade grade = courseGrades.get(i);
        String subject = grade.getKcmc();
        String subjectGrade = grade.getCj();
        String achievementPoint = grade.getJd();
        String subjectType = grade.getKcxzmc();
        String teacherName = grade.getJsxm();
        String examType = grade.getKsxz();
        gradeItemViewHolder.courseGradeView.setSubjectGrade(subject,subjectGrade,achievementPoint,subjectType,teacherName,examType);
    }

    @Override
    public int getItemCount() {
        return courseGrades.size();
    }

    class GradeItemViewHolder extends RecyclerView.ViewHolder{

        CourseGradeView courseGradeView;

        public GradeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            courseGradeView = itemView.findViewById(R.id.course_grade_view);
        }
    }
}
