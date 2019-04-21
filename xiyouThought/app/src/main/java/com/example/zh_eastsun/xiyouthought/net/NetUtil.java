package com.example.zh_eastsun.xiyouthought.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

    //私有化构造器，该类只向外提供测试网络的相关方法
    private NetUtil() {
    }

    //判断当前网络是否可用
    public static boolean isNetPingUsable(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isConnected();
                }
            }
            return false;
    }
}
