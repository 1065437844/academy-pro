package com.springboot.cloud.common.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CronUtil {

    public static String formatDateByPattern(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }



    //每天执行一次的cron表达式
    public static String cron(){
        String cron="0 06 10 15 1 ? 2014";
        String[] split = cron.split(" ");
        for (String s:split) {
            System.out.println(s);
        }
        split[3]="*";
        split[4]="*";
        String join = String.join(" ", split);
        System.out.println(join);
        return join;
    }

    //value 是星期几，value有值的时候，i===2
    public static String cron(Integer i,String cron,Integer value){
        String[] split = cron.split(" ");
        String join;
        switch (i){
            case 1://每天执行执行一次
                split[3]="*";
                split[4]="*";
                join = String.join(" ", split);
                return join;
            case 2://每周执行一次
                split[3]="?";
                split[4]="*";
                split[5]=value.toString();
                split[6]="";
                join = String.join(" ", split);
                return join;
            default:
                return null;
        }
    }
//    public static void main(String[] args) {
//        System.out.println(cron(2,"0 06 10 15 1 ? 2014",3));
//    }
}
