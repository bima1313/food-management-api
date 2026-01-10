package com.project.foodmarket.utils;

public class ExpirationUtils {
    private static final int DefaultExpiredMinutes = 60;
    private static final int DefaultExpiredHour = 24;

    public static long calculateTokenExpiredDate(int day) {
        return (System.currentTimeMillis() +
                (1000 * 60 * DefaultExpiredMinutes * DefaultExpiredHour * day));
    }

    public static long calculateTokenExpiredDate(int hour, int day) {
        return (System.currentTimeMillis() + (1000 * 60 * DefaultExpiredMinutes * hour * day));
    }

    public static long calculateTokenExpiredDate(int minute, int hour, int day) {
        return System.currentTimeMillis() + (1000 * 60 * minute * hour * day);
    }
}
