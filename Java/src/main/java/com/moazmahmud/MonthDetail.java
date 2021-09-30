package com.moazmahmud;

class MonthDetail {
    public String nameEn;
    public String nameBn;
    public int days;
    public int leapYearExtraDays;

    public MonthDetail(String nameEn, String nameBn, int days, int leapYearExtraDays) {
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.days = days;
        this.leapYearExtraDays = leapYearExtraDays;
    }

    public int getDays(boolean isLeapYear) {
        return isLeapYear ? (days + leapYearExtraDays) : days;
    }
}
