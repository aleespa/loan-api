package com.mycompany.portfoliomanager.model.amortization;

import lombok.Getter;

@Getter
public class AmortizationRow {
    private int paymentNumber;
    private double principal;
    private double interest;
    private double remainingBalance;

    public AmortizationRow(int paymentNumber, double principal, double interest, double remainingBalance) {
        this.paymentNumber = paymentNumber;
        this.principal = principal;
        this.interest = interest;
        this.remainingBalance = remainingBalance;
    }
}
