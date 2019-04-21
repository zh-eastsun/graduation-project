package com.example.zh_eastsun.xiyouthought.net;

import com.example.zh_eastsun.xiyouthought.javabean.SchoolTimetable;
import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * 请求课程表的工具类
 *
 * @author zh_eastsun
 * @version 1.0.0
 */
public class TimetableRequest {

    private String schoolYear;
    private String schoolTerm;

    /**
     * 计算当前的学期，默认只请求当前学期的课表
     */
    private void calculateDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 9) {
            schoolYear = String.valueOf(calendar.get(Calendar.YEAR));
            schoolTerm = "3";
        } else if (month >= 1 && month < 3) {
            schoolYear = String.valueOf(calendar.get(Calendar.YEAR));
            schoolTerm = "3";
        } else {
            schoolYear = String.valueOf(calendar.get(Calendar.YEAR) - 1);
            schoolTerm = "12";
        }
    }

    /**
     * 获取课表的List
     *
     * @return 课表的List
     */
    public ArrayList<SchoolTimetable.Timetable> getTimeTable() {
        try {
            calculateDate();
            Connection connection = Jsoup.connect(HttpURL.SCHOOL_TIMETABLE_URL);
            connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
            Map<String, String> cookies = XUPTVerify.getJsessionid();
            connection.cookies(cookies);
            connection.data("xnm",schoolYear);
            connection.data("xqm",schoolTerm);
            String jsonData = connection.ignoreContentType(true).post().body().text();
            Gson gson = new Gson();
            SchoolTimetable schoolTimetable = gson.fromJson(jsonData,SchoolTimetable.class);
            return (ArrayList<SchoolTimetable.Timetable>) schoolTimetable.getKbList();
        } catch (IOException e){
            return null;
        }
    }
}
