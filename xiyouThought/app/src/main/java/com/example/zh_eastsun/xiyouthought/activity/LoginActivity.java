package com.example.zh_eastsun.xiyouthought.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.rxjava.VerifyManager;
import com.example.zh_eastsun.xiyouthought.utils.RequestPermissionUtil;

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
    private RequestPermissionUtil requestPermissionUtil;
    private HashMap<String,String> userInput;

    /**
     * 完成控件的初始化
     */
    private void init() {
        //检查是否有响应的权限
        requestPermissionUtil = new RequestPermissionUtil();
        requestPermissionUtil.setRequestPermissionResult(new RequestPermissionUtil.RequestPermissionCallback() {
            @Override
            public void success() {

            }

            @Override
            public void failed() {
                Toast.makeText(LoginActivity.this,"请您授予必要的权限..否则无法使用...",Toast.LENGTH_SHORT).show();
            }
        });
        requestPermissionUtil.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this);
        if(!requestPermissionUtil.getResquestResult()){
            return ;
        }
        inputStuNum = findViewById(R.id.input_stuNum);
        inputPassword = findViewById(R.id.input_password);
        login = findViewById(R.id.login);
        verifyManager = new VerifyManager(LoginActivity.this);
        //设置请求验证完成时回调的接口
        verifyManager.setNetRequestCallback(new VerifyManager.NetRequestCallback() {
            @Override
            public void success() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failed() {
                Toast.makeText(LoginActivity.this,"登录失败..请检查账号密码和您的网络状态..",Toast.LENGTH_SHORT)
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
        init();

    }

}
