package com.example.zh_eastsun.xiyouthought.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.activity.LoginActivity;
import com.example.zh_eastsun.xiyouthought.net.XUPTVerify;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private TextView showNameText;
    private TextView showStudentNumberText;
    private TextView showClassNameText;
    private TextView showInstitutionText;
    private Button logoutButton;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public UserFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        preferences = getContext().getSharedPreferences("user_account",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        showNameText = view.findViewById(R.id.show_name);
        showStudentNumberText = view.findViewById(R.id.show_student_number);
        showClassNameText = view.findViewById(R.id.show_class_name);
        showInstitutionText = view.findViewById(R.id.show_institution);
        logoutButton = view.findViewById(R.id.logout);

        Map<String, String> studentInfo = XUPTVerify.getStudentInfo();
        showNameText.setText(studentInfo.get("stuName"));
        showStudentNumberText.setText(studentInfo.get("stuNum"));
        showClassNameText.setText(studentInfo.get("major"));
        showInstitutionText.setText(studentInfo.get("academy"));
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferences.getBoolean("isAutoLogin",true)){
                    editor.putBoolean("isAutoLogin",false);
                    editor.apply();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

}
