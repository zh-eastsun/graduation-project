package com.example.zh_eastsun.xiyouthought.rxjava;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.zh_eastsun.xiyouthought.R;
import com.example.zh_eastsun.xiyouthought.activity.MainActivity;
import com.example.zh_eastsun.xiyouthought.net.NetUtil;
import com.example.zh_eastsun.xiyouthought.net.XUPTVerify;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VerifyManager {

    private HashMap<String, String> loginInformation;
    private Context context;
    private static XUPTVerify xuptVerify;
    private NetRequestCallback netRequestCallback;
    private EditTextInputCallback editTextInputCallback;
    private Boolean netRequestResult;

    public interface NetRequestCallback {
        void success();
        void failed();
    }

    public interface EditTextInputCallback{
        void clearText();
        HashMap<String,String> getUserInput();
    }

    /**
     * 完成网络请求后的回掉
     * @param netRequestCallback
     */
    public void setNetRequestCallback(NetRequestCallback netRequestCallback) {
        this.netRequestCallback = netRequestCallback;
    }

    public void setEditTextCallback(EditTextInputCallback editTextInputCallback){
        this.editTextInputCallback = editTextInputCallback;
    }

    public VerifyManager(Context context) {
        this.context = context;
        xuptVerify = new XUPTVerify();
        loginInformation = new HashMap<>();
        netRequestResult = new Boolean(false);

    }

    public Boolean getNetRequestResult() {
        return netRequestResult;
    }

    /**
     * 登录验证
     */
    public void loginVerify() {
        //提示框对用户进行网络请求提示
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("正在登录")
                .setMessage("请等待...")
                .setView(R.layout.wait_progress_bar)
                .setCancelable(false)
                .show();
        if(!NetUtil.isNetPingUsable(context)){
            dialog.dismiss();
            netRequestCallback.failed();
            return ;
        }
        //获取用户输入的信息
        loginInformation = editTextInputCallback.getUserInput();
        //rxjava进行数据请求及UI更新
        Observable.just(loginInformation)
                .map(new Function<HashMap<String, String>, Boolean>() {
                    @Override
                    public Boolean apply(HashMap<String, String> loginInformation) throws Exception {
                        String stuNum = loginInformation.get("stuNum");
                        String password = loginInformation.get("password");
                        //返回服务器的验证结果
                        return xuptVerify.verify(stuNum, password);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean verifyResult) throws Exception {
                        if (verifyResult) {
                            //验证成功时取消提示框并启动第二个服务
                            dialog.dismiss();
                            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(context, MainActivity.class);
                            netRequestResult = true;
                            netRequestCallback.success();
                            context.startActivity(intent);
                        } else {
                            //失败时取消提示框并清空用户输入的数据
                            dialog.dismiss();
                            Toast.makeText(context, "账号密码错误，请重新登录", Toast.LENGTH_SHORT)
                                    .show();
                            editTextInputCallback.clearText();
                        }
                    }
                });
    }
}
