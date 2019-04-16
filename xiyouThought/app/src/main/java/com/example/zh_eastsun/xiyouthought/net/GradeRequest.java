package com.example.zh_eastsun.xiyouthought.net;


import com.example.zh_eastsun.xiyouthought.javabean.PageSize;
import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;


public class GradeRequest {

    private PageSize pageSize;
    private String schoolYear;
    private String schoolTerm;
    private Gson gson;
    public GradeRequest(String schoolYear,String schoolTerm) throws IOException{
        gson = new Gson();
        this.schoolYear = schoolYear;
        this.schoolTerm = schoolTerm;
        Connection connection = Jsoup.connect(HttpURL.COURSE_GRADE_URL);
        connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        connection.cookies(XUPTVerify.getJsessionid());
        connection.data("xnm",schoolYear);
        connection.data("xqm",schoolTerm);
        Connection.Response response = connection.ignoreContentType(true).execute();
        pageSize = gson.fromJson(response.body(),PageSize.class);
    }


}
