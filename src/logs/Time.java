package logs;

import java.time.LocalDateTime;

public class Time {

    // global variables
    public final static int JAN = 1;
    public final static int FEB = 2;
    public final static int MAR = 3;
    public final static int APR = 4;
    public final static int MAY = 5;
    public final static int JUN = 6;
    public final static int JUL = 7;
    public final static int AUG = 8;
    public final static int SEP = 9;
    public final static int OCT = 10;
    public final static int NOV = 11;
    public final static int DEC = 12;

    // local variables
    int month, day, year, hour, minute, second;

    public Time(int mm, int dd, int yr, int hr, int min, int sec){
        month = mm;
        day = dd;
        year = yr;
        hour = hr;
        minute = min;
        second = sec;
    }

    public Time(Time time){
        month = time.getMonth();
        day = time.getDay();
        year = time.getYear();
        hour = time.getHour();
        minute = time.getMinute();
        second = time.getSecond();
    }

    public void setTime(int mm, int dd, int yr, int hr, int min, int sec){
        month = mm;
        day = dd;
        year = yr;
        hour = hr;
        minute = min;
        second = sec;
    }

    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getYear(){
        return year;
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public int getSecond(){
        return second;
    }

    public String getSaveString(){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(month + "/");
        sb.append(day + "/");
        sb.append(year + "|");
        sb.append(hour + ":");
        sb.append(minute + ":");
        sb.append(second + "]");
        return sb.toString();
    }

    public Time addHours(long hours){
        LocalDateTime ldt = LocalDateTime.now().plusHours(hours);
        return new Time(ldt.getMonthValue(),ldt.getDayOfMonth(),
                ldt.getYear(),ldt.getHour(),
                ldt.getMinute(), ldt.getSecond());
    }

    public static Time getCurrentTime(){
        LocalDateTime ldt = LocalDateTime.now();
        return new Time(ldt.getMonthValue(),ldt.getDayOfMonth(),
                ldt.getYear(),ldt.getHour(),
                ldt.getMinute(), ldt.getSecond());

    }
}
