package com.example.zh_eastsun.xiyouthought.javabean;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.Arrays;
import java.util.List;

public class SchoolTimetable {

    private List<Timetable> kbList;

    public List<Timetable> getKbList() {
        return kbList;
    }

    public void setKbList(List<Timetable> kbList) {
        this.kbList = kbList;
    }

    public class Timetable implements ScheduleEnable{
        private int day;
        private String jcor;
        private String kcmc;
        private String xm;
        private int xqj;
        private String cdmc;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getJcor() {
            return jcor;
        }

        public void setJcor(String jcor) {
            this.jcor = jcor;
        }

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public int getXqj() {
            return xqj;
        }

        public void setXqj(int xqj) {
            this.xqj = xqj;
        }

        public String getCdmc() {
            return cdmc;
        }

        public void setCdmc(String cdmc) {
            this.cdmc = cdmc;
        }

        public Schedule getSchedule() {
            Integer[] weeks = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
            Schedule schedule = new Schedule();
            schedule.setDay(getXqj());
            schedule.setName(getKcmc());
            schedule.setRoom(getCdmc());
            schedule.setTeacher(getXm());
            schedule.setStart(Integer.valueOf(String.valueOf(getJcor().charAt(0))));
            schedule.setWeekList(Arrays.asList(weeks));
            schedule.setStep(2);
            schedule.setColorRandom(2);
            return schedule;
        }
    }
}
