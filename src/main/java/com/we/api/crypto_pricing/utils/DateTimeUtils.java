package com.we.api.crypto_pricing.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtils {

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String nowDateTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatter.format(localDateTime);
    }

    public static boolean isValid(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        try {
            LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
