package com.we.api.crypto_pricing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String nowDateTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }
}
