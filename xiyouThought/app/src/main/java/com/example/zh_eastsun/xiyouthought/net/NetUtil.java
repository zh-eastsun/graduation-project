package com.example.zh_eastsun.xiyouthought.net;

public class NetUtil {

    //私有化构造器，该类只向外提供测试网络的相关方法
    private NetUtil(){}

    //判断当前网络是否可用
    public static boolean isNetPingUsable(){
        Runtime runtime = Runtime.getRuntime();
        try{
            //此处因为学校服务器开启了防火墙，导致ping命令无法通过，所以改用百度测试网络连通性
            Process process = runtime.exec("ping -c 1 www.baidu.com");
            int ret = process.waitFor();
            if(ret == 0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
