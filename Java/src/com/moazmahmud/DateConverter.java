package com.moazmahmud;

import java.time.LocalDate;

public class DateConverter {
    private static final MonthDetail[] BANGLA_MONTH_DETAILS = {
            new MonthDetail("Boishakh", "বৈশাখ", 31, 0),
            new MonthDetail("Jyoishtho", "জ্যৈষ্ঠ", 31, 0),
            new MonthDetail("Asharh", "আষাঢ়", 31, 0),
            new MonthDetail("Srabon", "শ্রাবণ", 31, 0),
            new MonthDetail("Bhadro", "ভাদ্র", 31, 0),
            new MonthDetail("Ashshin", "আশ্বিন", 30, 0),
            new MonthDetail("Kartik", "কার্তিক", 30, 0),
            new MonthDetail("Ogrohayon", "অগ্রহায়ণ", 30, 0),
            new MonthDetail("Poush", "পৌষ", 30, 0),
            new MonthDetail("Magh", "মাঘ", 30, 0),
            new MonthDetail("Falgoon", "ফাল্গুন", 30, 1),
            new MonthDetail("Choitro", "চৈত্র", 30, 0)
    };

    public static DateKey getBanglaDate(DateKey englishDateKey) {
        LocalDate englishDate = LocalDate.of(englishDateKey.year, englishDateKey.month, englishDateKey.day);
        LocalDate banglaDate = LocalDate.of(englishDateKey.year, 4, 14);

        boolean isBeforeBanglaNewYear = englishDate.isBefore(banglaDate);
        if (isBeforeBanglaNewYear)
            banglaDate = LocalDate.of(englishDateKey.year - 1, 4, 14);

        int monthIndex = 0, dayOfMonth = 1;
        while (!banglaDate.equals(englishDate)) {
            banglaDate = banglaDate.plusDays(1);
            ++dayOfMonth;
            if (dayOfMonth > BANGLA_MONTH_DETAILS[monthIndex].getDays(banglaDate.isLeapYear())) {
                dayOfMonth = 1;
                ++monthIndex;
            }
        }

        final int yearDifference = isBeforeBanglaNewYear ? 594 : 593;
        return new DateKey(
                dayOfMonth,
                monthIndex + 1,
                englishDateKey.year - yearDifference
        );
    }
}
