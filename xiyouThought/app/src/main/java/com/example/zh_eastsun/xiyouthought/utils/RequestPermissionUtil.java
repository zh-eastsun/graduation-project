package com.example.zh_eastsun.xiyouthought.utils;

import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 动态权限请求类
 *
 * @author zh_eastsun
 * @version 1.0.0
 */
public class RequestPermissionUtil {

    private RequestPermissionCallback requestPermissionCallback;
    private boolean haveAllPermissions;

    public RequestPermissionUtil(){
        this.haveAllPermissions = false;
    }

    public interface RequestPermissionCallback{
        void success();
        void failed();
    }

    public void setRequestPermissionResult(RequestPermissionCallback requestPermissionCallback){
        this.requestPermissionCallback = requestPermissionCallback;
    }
    /**
     * 动态权限申请的方法
     *
     * @param permissions 要申请的权限
     * @param activity 申请权限的activity
     */
    public void requestPermissions(String[] permissions, FragmentActivity activity){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission){
                        if(permission.granted){
                            haveAllPermissions = true;
                            requestPermissionCallback.success();
                        }else if(permission.shouldShowRequestPermissionRationale){
                            haveAllPermissions = false;
                            requestPermissionCallback.failed();
                        }else{
                            haveAllPermissions = false;
                            requestPermissionCallback.failed();
                        }
                    }
                });
    }

    public boolean getResquestResult(){
        return this.haveAllPermissions;
    }
}
