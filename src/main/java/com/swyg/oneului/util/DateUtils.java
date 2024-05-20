package com.swyg.oneului.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String getPreviousDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate previousDate = date.minusDays(1);

        return previousDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static String getPreviousTwoDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousTwoDate = currentDate.minusDays(2);

        return previousTwoDate.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static String getFormattedNowDate() {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH00");

        return currentTime.format(formatter);
    }

    public static String formatIssueDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }
}
