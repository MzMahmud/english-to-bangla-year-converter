package com.moazmahmud;

import java.util.Comparator;
import java.util.Objects;

public class DateKey implements Comparable<DateKey> {
    public int day;
    public int month;
    public int year;

    public DateKey(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateKey dateKey = (DateKey) o;
        return day == dateKey.day && month == dateKey.month && year == dateKey.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    @Override
    public int compareTo(DateKey other) {
        return Comparator.comparing((DateKey d) -> d.year)
                         .thenComparing(d -> d.month)
                         .thenComparing(d -> d.day)
                         .compare(this, other);
    }
}
