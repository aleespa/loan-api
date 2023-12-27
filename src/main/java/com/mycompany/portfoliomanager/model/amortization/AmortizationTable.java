package com.mycompany.portfoliomanager.model.amortization;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Interest;
import lombok.Getter;

import java.time.LocalDate;
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
        int numberPayments = calculateNumberPayments(loan);
        double paymentAmount = calculatePaymentAmount(loan);
        Interest interest = loan.getInterest();
        LocalDate paymentDate = loan.getStartDate();

        for (int i = 0; i < numberPayments; i++) {
            double principal = balance;
            double interestPaid = principal * calculateInterestFactor(loan) - principal;
            balance -= paymentAmount;
            AmortizationRow row = new AmortizationRow(i + 1, paymentDate, principal, interestPaid, balance);
            rows.add(row);
            paymentDate = paymentDate.plusDays(loan.getPayment_frequency().getNumberDays());
        }
    }
    private int calculateNumberPayments(Loan loan){
        if( loan.getTerm() != null){
            return loan.getTerm();
        } else{
            return 0;
        }
    }
    private double calculatePaymentAmount(Loan loan){
        int numberPayments = calculateNumberPayments(loan);
        return loan.getPrincipal() / numberPayments;
    }

    private double calculateInterestFactor(Loan loan){
        Interest interest = loan.getInterest();
        return Math.pow(
                1 + interest.getRate() / interest.getFrequency().getNumberDays(),
                loan.getPayment_frequency().getNumberDays());
    }
}
