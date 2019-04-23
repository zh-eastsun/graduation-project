package com.example.zh_eastsun.xiyouthought.net;

/**
 * 网络请求结果的回调接口
 */
public interface NetRequestCallback {
    //网络请求成功时回调的方法
    void success();
    //网络请求失败时回调的方法
    void failed();
}
