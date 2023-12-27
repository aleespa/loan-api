package com.mycompany.portfoliomanager.model.interest;

import lombok.Getter;

@Getter
public enum Frequency {
    Annual(365L),
    Semi_annual(180L),
    Quarterly(90L),
    Monthly(30L),
    Bi_weekly(15L),
    Weekly(7L),
    Daily(1L),
    Continuous(0L);

    private final Long numberDays;

    Frequency(Long numberDays) {
        this.numberDays = numberDays;
    }

    public static Frequency fromString(String text) {
        for (Frequency b : Frequency.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}
