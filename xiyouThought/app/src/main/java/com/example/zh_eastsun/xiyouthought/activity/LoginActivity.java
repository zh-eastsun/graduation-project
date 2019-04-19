package com.example.zh_eastsun.xiyouthought.activity;

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

import java.util.HashMap;


/**
 * 用户登录的活动
 * @author zh_eastsun
 * @version 1.0.0
 */
public class LoginActivity extends AppCompatActivity {

    private EditText inputStuNum;
    private EditText inputPassword;
    private Button login;
    private VerifyManager verifyManager;

    private HashMap<String,String> userInput;


    /**
     * 完成控件的初始化
     */
    private void initView() {
        inputStuNum = findViewById(R.id.input_stuNum);
        inputPassword = findViewById(R.id.input_password);
        login = findViewById(R.id.login);
        verifyManager = new VerifyManager(LoginActivity.this);
        //设置请求验证完成时回调的接口
        verifyManager.setNetRequestCallback(new VerifyManager.NetRequestCallback() {
            @Override
            public void success() {
                if(verifyManager.getNetRequestResult()){
                    finish();
                }
            }

            @Override
            public void failed() {
                Toast.makeText(LoginActivity.this,"网络不可用...请检查网络...",Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //设置EditText清除用户输入和获取用户输入的接口
        verifyManager.setEditTextCallback(new VerifyManager.EditTextInputCallback() {
            @Override
            public void clearText() {
                inputStuNum.setText("");
                inputPassword.setText("");
            }

            @Override
            public HashMap<String, String> getUserInput() {
                if(userInput == null){
                    userInput = new HashMap<>();
                }
                userInput.put("stuNum",inputStuNum.getText().toString());
                userInput.put("password",inputPassword.getText().toString());
                return userInput;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyManager.loginVerify();
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
