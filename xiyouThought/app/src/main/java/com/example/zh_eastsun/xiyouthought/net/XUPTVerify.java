package com.example.zh_eastsun.xiyouthought.net;


import com.example.zh_eastsun.xiyouthought.javabean.PublicKey;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class XUPTVerify {
    private static final String TOKEN = "csrftoken";
    private static final String JSESSIONID = "JSESSIONID";
    private static final String MODULUS = "modulus";
    private static final String EXPONENT = "exponent";

    private static final String XUPT_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
    private static final String PUBLIC_KEY_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=";
    private static final String POST_URL = "http://www.zfjw.xupt.edu.cn/jwglxt/xtgl/login_slogin.html";

    private static Map<String,Object> tokenAndCookie;

    private Base64 base64 = new Base64();
    private RSA rsa = new RSA();
    
    public static void main(String[] args) throws Exception {
		XUPTVerify verify = new XUPTVerify();
		verify.verify("04152017","helloworld1997");
		System.out.println(verify.getStudentInfo(XUPTVerify.tokenAndCookie,"04152017"));
	}

	/**
	 * 经过系统服务器的验证
	 * @param stuID 学生的学号
	 * @param password 学生的教务系统密码
	 * @return 是否通过验证
	 * @throws Exception
	 */
    public boolean verify(String stuID, String password) throws Exception{
    	//拿到Cookie
        tokenAndCookie = getTokenAndCookie();
        //将密码进行加密、编码
        String encryPassword = RSA(password, (Map<String, String>) tokenAndCookie.get(JSESSIONID));

        //构建模拟请求
        Connection connection = Jsoup.connect(POST_URL);
        connection.header("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        //添加表单提交的数据
        connection.data("csrftoken", tokenAndCookie.get(TOKEN).toString());
        connection.data("yhm", stuID);
        connection.data("mm", encryPassword);
        //connection.data("mm", encryPassword);
        connection.cookies((Map<String, String>) tokenAndCookie.get(JSESSIONID))
                .ignoreContentType(true).method(Method.POST).execute();
        //得到模拟请求的response
        Response response = connection.execute();
       
        //分析返回的HTML
        Document document = Jsoup.parse(response.body());
        //如果有id为tips的标签，说明登录失败，并将失败信息返回
        Element result = document.getElementById("tips");
       
        if(result != null) {
            return false;
        }
        return true;
    }

    /**
     * 获取个人信息
     * @param tokenAndCookie
     * @throws IOException
     */
    public Map<String,String> getStudentInfo(Map<String, Object> tokenAndCookie,String stuID) throws IOException {
		Connection connection = Jsoup.connect("http://www.zfjw.xupt.edu.cn/jwglxt/xsxxxggl/xsgrxxwh_cxXsgrxx.html?gnmkdm=N100801&layout=default&su="+stuID);
		connection.header("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		connection.cookies((Map<String, String>) tokenAndCookie.get(JSESSIONID));
		Response response = connection.execute();

		Element element = Jsoup.parse(response.body()).body();
		Map<String,String> map = new HashMap<String, String>();
		//姓名
		Element nameElement = element.getElementById("col_xm");
		String name = nameElement.text();
		map.put("stuName", name);
		//班级
		Element lessonElement = element.getElementById("col_bh_id");
		String major = lessonElement.text();
		map.put("major", major);
		return map;
	}

    
    /**
	 * 向网页发一次请求，获取Cookie
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> getTokenAndCookie() throws IOException {
		Connection connection = Jsoup.connect(XUPT_URL + new Date().getTime());
		connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		Response response = connection.execute();
		Map<String, Object> result = new HashMap<>();
		//保存cookie
		result.put(JSESSIONID, response.cookies());
		//保存token
		Document document = Jsoup.parse(response.body());
		String token = document.getElementById(TOKEN).val();
		result.put(TOKEN, token);
		return result;
	}

	/**
	 * 将密码通过RSA公钥进行加密
	 * @param password
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public String RSA(String password, Map<String, String> cookies) throws Exception {
		//获取公钥
		Map<String, String> publicKeyMap = getPublicKey(cookies);
		//进行加密
		String encrptPassword = rsa.RSAEncrypt(password, base64.b64tohex(publicKeyMap.get(MODULUS)), base64.b64tohex(publicKeyMap.get(EXPONENT)));
		//将加密后的密码进行Base64编码，并返回
		return base64.hex2b64(encrptPassword);
	}

	/**
	 * 通过添加Cookie值，获取RSA加密的公钥
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPublicKey (Map<String, String> cookies) throws Exception {
		Connection connection = Jsoup.connect(PUBLIC_KEY_URL + new Date().getTime());
		connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		Response response = connection.cookies(cookies).ignoreContentType(true).execute();
		Gson gson = new Gson();
		PublicKey publicKey = gson.fromJson(response.body(),PublicKey.class);
//		JSONObject jsonObject = JSONObject.fromObject(response.body());
//		String modulus = jsonObject.getString(MODULUS);
//		String exponent = jsonObject.getString(EXPONENT);
		Map<String, String> result = new HashMap<>();
		result.put(MODULUS, publicKey.getModulus());
		result.put(EXPONENT, publicKey.getExponent());
		return result;
	}
	
}

