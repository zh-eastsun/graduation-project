package com.example.zh_eastsun.xiyouthought.net;


import com.example.zh_eastsun.xiyouthought.javabean.CourseGrade;
import com.example.zh_eastsun.xiyouthought.javabean.PageSize;
import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;


public class GradeRequest {

    /**
     * 获取页面数
     *
     * @return 请求的页面数
     * @throws IOException
     */
    private int getPageSize(String schoolYear, String schoolTerm) throws IOException {
        Connection connection = Jsoup.connect(HttpURL.COURSE_GRADE_URL);
        connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        connection.cookies(XUPTVerify.getJsessionid());
        connection.data("xnm", schoolYear);
        connection.data("xqm", schoolTerm);
        connection.data("queryModel.currentPage", "1");

        Connection.Response response = connection.ignoreContentType(true).execute();
        Gson gson = new Gson();

        PageSize pageSize = gson.fromJson(response.body(), PageSize.class);
        return Integer.valueOf(pageSize.getTotalPage());
    }

    /**
     * 获取课程成绩
     * @param schoolYear 用户选择的学年
     * @param schoolTerm 用户选择的学期
     * @return  课程成绩Bean类的集合
     * @throws IOException
     */
    public ArrayList<CourseGrade> getCourseGrade(String schoolYear, String schoolTerm) {

        try{
            //此处需要判断页面大小，数据并不一定存储在一页表格中
            int pageSize = getPageSize(schoolYear, schoolTerm);
            ArrayList<CourseGrade> courseGradeArrayList = new ArrayList<>();
            //循环查询每个页面
            for (int index = 0; index < pageSize; index++) {
                Connection connection = Jsoup.connect(HttpURL.COURSE_GRADE_URL);
                connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
                connection.cookies(XUPTVerify.getJsessionid());
                connection.data("xnm",schoolYear);
                connection.data("xqm",schoolTerm);
                connection.data("queryModel.currentPage",String.valueOf(index+1));
                String jsonData = connection.ignoreContentType(true).post().body().text();
                Gson gson = new Gson();
                CourseGrade courseGrade = gson.fromJson(jsonData,CourseGrade.class);
                courseGradeArrayList.add(courseGrade);
            }
            return courseGradeArrayList;
        }catch(IOException e){
            return null;
        }
    }

}
