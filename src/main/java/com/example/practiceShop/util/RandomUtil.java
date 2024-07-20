package com.example.practiceShop.util;

public class RandomUtil {
    public static Long generateRandomNumber(Long mi, Long mx) {
        return (long) ((Math.random() * (mx - mi)) + mi);
    }
}
