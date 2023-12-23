package com.mycompany.portfoliomanager.model.amortization;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Interest;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AmortizationTable {
    private List<AmortizationRow> rows;

    public AmortizationTable(Loan loan) {
        this.rows = new ArrayList<>();
        calculateAmortization(loan);
    }

    private void calculateAmortization(Loan loan) {
        // Logic to calculate amortization based on loan details
        // This is a simplified version. You would need to include logic for different interest rates, payment frequencies, etc.
        double balance = loan.getPrincipal();
        int totalPayments = 12;
        Interest interest = loan.getInterest();

        for (int i = 0; i < totalPayments; i++) {
            double principal = balance;
            balance -= principal;

            AmortizationRow row = new AmortizationRow(i + 1, principal, 10, 100);
            rows.add(row);
        }
    }
}
