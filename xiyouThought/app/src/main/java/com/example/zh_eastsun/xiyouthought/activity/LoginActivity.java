package com.example.zh_eastsun.xiyouthought.activity;

import android.content.Intent;
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
import com.example.zh_eastsun.xiyouthought.net.XUPTVerify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "mainActivity";

    private EditText inputStuNum;
    private EditText inputPassword;
    //    private EditText inputCheckCode;
    private Button login;
//    private ImageView checkCodeImage;

    //执行耗时任务的线程池，最大工作线程数为5
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler(Looper.getMainLooper());
    private String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
//        initCheckCode();

    }

    //完成控件的初始化
    private void initView() {
        inputStuNum = findViewById(R.id.input_stuNum);
        inputPassword = findViewById(R.id.input_password);
//        inputCheckCode = findViewById(R.id.input_check_code);
        login = findViewById(R.id.login);
//        checkCodeImage = findViewById(R.id.check_code);

//        checkCodeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initCheckCode();
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetPingUsable()) {
                    //获取用户输入的学号、密码和验证码信息
                    final String stuNum = inputStuNum.getText().toString();
                    final String password = inputPassword.getText().toString();
                    //初始化发起登陆请求的类
                    final XUPTVerify verify = new XUPTVerify();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //判断登录状态是否成功
                                //成功时携带学生的基本信息跳转活动
                                //失败时清空输入栏
                                if (verify.verify(stuNum, password)) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                                                    .show();
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "账号密码错误，请重新登录", Toast.LENGTH_SHORT)
                                                    .show();
                                            inputStuNum.setText("");
                                            inputPassword.setText("");
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, R.string.internet_unavailable_hint, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

//    //初始化或刷新验证码
//    private void initCheckCode() {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                if (NetUtil.isNetPingUsable()) {
//                    //当前网络可用的情况初始化或刷新验证码图片
//                    final CheckCodeRequestManager checkCodeRequestManager = new CheckCodeRequestManager();
//                    checkCodeRequestManager.request();
//                    Log.e(TAG, "run: " + checkCodeRequestManager.getCookie());
//                    final Bitmap bitmap = checkCodeRequestManager.getCheckCode();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            checkCodeImage.setImageBitmap(bitmap);
//                        }
//                    });
//                    cookie = checkCodeRequestManager.getCookie();
//                    checkCodeRequestManager.disconnect();
//                } else {
//                    //当前网络不可用时弹出窗口提醒用户网络不可用
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(LoginActivity.this, R.string.internet_unavailable_hint, Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    });
//                }
//            }
//        });
//    }
}
