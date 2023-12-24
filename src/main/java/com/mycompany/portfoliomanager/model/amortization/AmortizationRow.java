package com.mycompany.portfoliomanager.model.amortization;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AmortizationRow {
    private final int paymentNumber;
    private final LocalDate paymentDate;
    private final double principal;
    private final double interest;
    private final double remainingBalance;

    public AmortizationRow(int paymentNumber, LocalDate paymentDate, double principal, double interest, double remainingBalance) {
        this.paymentNumber = paymentNumber;
        this.paymentDate = paymentDate;
        this.principal = principal;
        this.interest = interest;
        this.remainingBalance = remainingBalance;
    }
}
