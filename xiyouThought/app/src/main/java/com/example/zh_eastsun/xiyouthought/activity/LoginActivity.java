package com.example.zh_eastsun.xiyouthought.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    private EditText inputStuNumEditText;
    private EditText inputPasswordEditText;
    private Button loginButton;
    private CheckBox rememberPasswordCheckBox;
    private CheckBox autoLoginCheckBox;
    private VerifyManager verifyManager;
    private RequestPermissionUtil requestPermissionUtil;
    private HashMap<String,String> userInput;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
        inputStuNumEditText = findViewById(R.id.input_stuNum);
        inputPasswordEditText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.login);
        rememberPasswordCheckBox = findViewById(R.id.remember_password);
        autoLoginCheckBox = findViewById(R.id.auto_login);
        preferences = getSharedPreferences("user_account",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        //根据上次的操作初始化checkbox的选择状态
        if(preferences.getBoolean("isAutoLogin",false)){
            autoLoginCheckBox.setChecked(true);
        }else{
            autoLoginCheckBox.setChecked(false);
        }
        if(preferences.getBoolean("isRemember",false)){
            rememberPasswordCheckBox.setChecked(true);
        }else{
            rememberPasswordCheckBox.setChecked(false);
        }
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
                inputStuNumEditText.setText("");
                inputPasswordEditText.setText("");
            }

            @Override
            public HashMap<String, String> getUserInput() {
                if(userInput == null){
                    userInput = new HashMap<>();
                }
                userInput.put("stuNum",inputStuNumEditText.getText().toString());
                userInput.put("password",inputPasswordEditText.getText().toString());
                return userInput;
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyManager.loginVerify();
            }
        });
        rememberPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putString("stuNum",inputStuNumEditText.getText().toString());
                    editor.putString("password",inputPasswordEditText.getText().toString());
                    editor.putBoolean("isRemember",true);
                    editor.apply();
                }else{
                    editor.putBoolean("isRemember",false);
                    editor.apply();
                }
            }
        });
        autoLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!rememberPasswordCheckBox.isChecked()){
                        rememberPasswordCheckBox.setChecked(true);
                    }
                    editor.putBoolean("isAutoLogin",true);
                    editor.apply();
                }else{
                    editor.putBoolean("isAutoLogin",false);
                    editor.apply();
                }
            }
        });
        //检查用户是否记住密码并还原信息
        readUserMessage();
        //自动登录
        autoLogin();
    }

    /**
     * 如果用户上一次登录时选择记住密码选项时此次登录应该还原用户上次输入的信息
     */
    private void readUserMessage(){
        if(preferences.getBoolean("isRemember",false)){
            inputStuNumEditText.setText(preferences.getString("stuNum",""));
            inputPasswordEditText.setText(preferences.getString("password",""));
        }
    }

    private void autoLogin(){
        if(preferences.getBoolean("isAutoLogin",false)){
            verifyManager.loginVerify();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化
        init();
    }

}
