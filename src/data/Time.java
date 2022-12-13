package data;

import java.time.LocalDateTime;

public class Time {

    // variables
    public int month, day, year, hour, minute, second;

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

    public Time retrieveSaveData(String time){
        if(time.startsWith("[")){
            int i = 1;
            StringBuffer month, day, year, hour, minute, second;

            // scan month data
            month = new StringBuffer();
            while(time.charAt(i) != '/'){
                month.append(time.charAt(i++));
            }
            i++;

            // scan day data
            day = new StringBuffer();
            while(time.charAt(i) != '/'){
                day.append(time.charAt(i++));
            }
            i++;

            // scan year data
            year = new StringBuffer();
            while(time.charAt(i) != '|'){
                year.append(time.charAt(i++));
            }
            i++;

            // scan hour data
            hour = new StringBuffer();
            while(time.charAt(i) != ':'){
                hour.append(time.charAt(i++));
            }
            i++;

            // scan minute data
            minute = new StringBuffer();
            while(time.charAt(i) != ':'){
                minute.append(time.charAt(i++));
            }
            i++;

            // scan second data
            second = new StringBuffer();
            while(time.charAt(i) != ']'){
                second.append(time.charAt(i++));
            }

            return new Time(Integer.valueOf(month.toString()),Integer.valueOf(day.toString()),
                            Integer.valueOf(year.toString()),Integer.valueOf(hour.toString()),
                            Integer.valueOf(minute.toString()),Integer.valueOf(second.toString()));
        }else {
            return null;
        }
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
