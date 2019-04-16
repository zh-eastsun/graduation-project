package com.example.zh_eastsun.xiyouthought.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.net.NetUtil;
import com.example.zh_eastsun.xiyouthought.rxjava.VerifyManager;


public class LoginActivity extends AppCompatActivity {

    private EditText inputStuNum;
    private EditText inputPassword;
    private Button login;
    private VerifyManager verifyManager;


    //完成控件的初始化
    private void initView() {
        inputStuNum = findViewById(R.id.input_stuNum);
        inputPassword = findViewById(R.id.input_password);
        login = findViewById(R.id.login);
        verifyManager = new VerifyManager(LoginActivity.this, inputStuNum, inputPassword);
        verifyManager.setNetRequestCallback(new VerifyManager.NetRequestCallback() {
            @Override
            public void success() {
                if(verifyManager.getNetRequestResult()){
                    finish();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetPingUsable()) {
                    verifyManager.loginVerify();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.internet_unavailable_hint, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();

    }

}
