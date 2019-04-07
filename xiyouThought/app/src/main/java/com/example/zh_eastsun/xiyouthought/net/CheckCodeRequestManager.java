package com.example.zh_eastsun.xiyouthought.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CheckCodeRequestManager {

    private final String TAG = "CheckCodeRequestManager";

    private Bitmap checkCode;                           //请求下来的验证码
    private HttpURLConnection connection;               //用于网络请求的类
    private String cookie;                              //当前请求的cookie

    public void request(){
        try {
            URL checkCodeURL = new URL(HttpURL.CHECK_CODE_URL);
            this.connection = (HttpURLConnection) checkCodeURL.openConnection();
            connection.setRequestMethod("GET");             //该请求方式为GET请求
            connection.setDoInput(true);                    //允许从网络流中读取数据
            connection.setDoOutput(false);                  //不允许向网络流中写数据
            connection.setInstanceFollowRedirects(true);    //设置该请求可以自动执行重定向
            connection.setUseCaches(true);                  //设置可以使用缓存
            connection.setConnectTimeout(3000);             //设置请求的超时时间为3000毫秒
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取cookie
    public String getCookie() {
        if(null == cookie){
            Map<String, List<String>> headerFields = connection.getHeaderFields();   //获取响应的请求头
            cookie = headerFields.get("Set-Cookie").get(0);                          //从请求头中获取cookie
        }
        return cookie;
    }

    //获取验证码的bitmap
    public Bitmap getCheckCode() {
        if(null == checkCode){
            try {
                InputStream is = connection.getInputStream();
                int responseCode = connection.getResponseCode();                    //获取响应的返回码确认请求结果
                Log.e(TAG, "getCheckCode: " + responseCode);
                checkCode = BitmapFactory.decodeStream(is);
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return checkCode;
    }

    //断开此次网络连接
    public void disconnect(){
        this.cookie = null;
        this.checkCode = null;
        connection.disconnect();
        connection = null;
    }
}
