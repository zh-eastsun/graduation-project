package com.example.zh_eastsun.xiyouthought.net;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginRequestManager {

    private final String TAG = "LoginRequestManager";

    private HttpURLConnection httpURLConnection;

    private String stuNum;
    private String password;
    private String checkCode;
    private String cookie;
    private StringBuilder requestBody;

    public LoginRequestManager(String stuNum, String password, String checkCode, String cookie) {
        this.stuNum = stuNum;
        this.password = password;
        this.checkCode = checkCode;
        this.cookie = cookie;
        this.requestBody = new StringBuilder("")
                .append("__VIEWSTATE=dDwxNTMxMDk5Mzc0Ozs%2BlYSKnsl%2FmKGQ7CKkWFJpv0btUa8%3D" + "&")
                .append("txtUserName=" + stuNum + "&")
                .append("Textbox1=" + "&")
                .append("TextBox2=" + password + "&")
                .append("txtSecretCode=" + checkCode + "&")
                .append("RadioButtonList1=ѧ��" + "&")
                .append("Button1=" + "&")
                .append("lbLanguage=" + "&")
                .append("hidPdrs=" + "&")
                .append("hidsc=");
    }

    //客户端发起登录请求
    public void loginRequest() {
        try {
            URL url = new URL(HttpURL.LOGIN_REQUEST_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);                //可以从该次请求中写入信息
            httpURLConnection.setDoOutput(true);               //可以从该次请求中读取信息
            httpURLConnection.setRequestMethod("POST");        //设置请求方法为POST
            httpURLConnection.setUseCaches(false);             //设置该次请求不可以使用缓存
            httpURLConnection.setConnectTimeout(3000);         //设置该次请求的超时时间为3000毫秒
            httpURLConnection.setInstanceFollowRedirects(true);//设置该次请求可以自动使用重定向
            //设置请求头
            String contentLength = requestBody.toString().getBytes().length + "";
            httpURLConnection.setRequestProperty("Host", "222.24.62.120");
            httpURLConnection.setRequestProperty("Content-Length", contentLength);
            httpURLConnection.setRequestProperty("Cache-Control", "max-age=0");
            httpURLConnection.setRequestProperty("Origin", "http://222.24.62.120");
            httpURLConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpURLConnection.setRequestProperty("Referer", "http://222.24.62.120/default2.aspx");
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            httpURLConnection.setRequestProperty("Cookie", cookie);
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.connect();                       //发起链接

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取返回报文的内容
    public String getResponseBody() {
        try {
            //向本次网络流中写入数据
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            bufferedOutputStream.write(requestBody.toString().getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            //从网络流中读取数据
            DataInputStream dataInputStream = new DataInputStream(httpURLConnection.getInputStream());
            byte[] data = new byte[2048];
            int num;
            while (dataInputStream.read(data) > 0) {
                Log.e(TAG, "getResponseBody: " + new String(data,"gbk"));
            }
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
