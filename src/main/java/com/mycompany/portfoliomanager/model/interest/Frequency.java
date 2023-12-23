package com.mycompany.portfoliomanager.model.interest;

public enum Frequency {
    Annual(365),
    Semi_annual(180),
    Quarterly(90),
    Monthly(30),
    Bi_weekly(15),
    Weekly(7),
    Daily(1),
    Continuous(0);

    private final double number_days;

    Frequency(double number_days) {
        this.number_days = number_days;
    }

}
