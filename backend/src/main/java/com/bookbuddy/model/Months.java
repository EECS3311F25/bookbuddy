package com.bookbuddy.model;

/**
 * Enum representing all 12 months of the year.
 * Used by MonthlyTracker to specify the reading goal month.
 */
public enum Months {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private final int value;

    Months(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Months fromValue(int monthNumber) {
        for (Months month : Months.values()) {
            if (month.getValue() == monthNumber) {
                return month;
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + monthNumber);
    }
}
