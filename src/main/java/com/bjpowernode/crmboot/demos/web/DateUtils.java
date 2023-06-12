package com.bjpowernode.crmboot.demos.web;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formateDateTime(Date date){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 String dateStr=simpleDateFormat.format(date);
                 return dateStr;
    }
}
