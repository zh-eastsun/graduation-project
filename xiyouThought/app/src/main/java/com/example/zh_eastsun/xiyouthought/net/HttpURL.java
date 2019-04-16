package com.example.zh_eastsun.xiyouthought.net;

public class HttpURL {

    //旧教务系统
    //请求验证码的URL
    public static final String CHECK_CODE_URL = "http://222.24.62.120/CheckCode.aspx";
    //请求登陆的URL
    public static final String LOGIN_REQUEST_URL = "http://222.24.62.120/default2.aspx";

    //新教务系统登录URL
    public static final String XUPT_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
    //新教务系统RSA公钥URL
    public static final String PUBLIC_KEY_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=";
    //新教务系统发起登录请求的URL
    public static final String POST_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_slogin.html";
    //新教务系统请求成绩的URL
    public static final String COURSE_GRADE_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdm=N305005";

    //将构造器私有化，该类只负责存储相关URL信息
    private HttpURL(){}
}
