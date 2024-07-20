package com.example.practiceShop.util;

import java.util.Date;

public class DateUtil {
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    public static Date millisToDate(long milliseconds) {
        return new Date(milliseconds);
    }

    public static Long diffInSeconds(Date d1, Date d2) {
        return (d1.getTime() - d2.getTime()) / 1000;
    }
}
