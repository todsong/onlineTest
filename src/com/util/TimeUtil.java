package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil
{
    public static String getTodayDate()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }
    public static String getNowTime()
    {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        return formatter.format(current);
    }
}
