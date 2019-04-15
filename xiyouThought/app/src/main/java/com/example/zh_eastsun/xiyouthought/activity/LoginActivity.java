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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText inputStuNum;
    private EditText inputPassword;
    private Button login;
    private VerifyManager verifyManager;
    //执行耗时任务的线程池，最大工作线程数为5
    //private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler(Looper.getMainLooper());




    //完成控件的初始化
    private void initView() {
        inputStuNum = findViewById(R.id.input_stuNum);
        inputPassword = findViewById(R.id.input_password);
        login = findViewById(R.id.login);
        verifyManager = new VerifyManager(LoginActivity.this,inputStuNum,inputPassword);
        verifyManager.setNetRequestCallback(new VerifyManager.NetRequestCallback() {
            @Override
            public void success() {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetPingUsable()) {
                    verifyManager.loginVerify();
//                    //等待提示框
//                    final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
//                            .setTitle("正在登录")
//                            .setMessage("请等待...")
//                            .setView(R.layout.wait_progress_bar)
//                            .setCancelable(false)
//                            .show();
//                    //获取用户输入的学号、密码和验证码信息
//                    final String stuNum = inputStuNum.getText().toString();
//                    final String password = inputPassword.getText().toString();
//                    //初始化发起登陆请求的类
//                    final XUPTVerify verify = new XUPTVerify();
//                    executorService.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                //判断登录状态是否成功
//                                //成功时携带学生的基本信息跳转活动
//                                //失败时清空输入栏
//                                if (verify.verify(stuNum, password)) {
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            dialog.dismiss();
//                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
//                                                    .show();
//                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    });
//                                } else {
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            dialog.dismiss();
//                                            Toast.makeText(LoginActivity.this, "账号密码错误，请重新登录", Toast.LENGTH_SHORT)
//                                                    .show();
//                                            inputStuNum.setText("");
//                                            inputPassword.setText("");
//                                        }
//                                    });
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
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
