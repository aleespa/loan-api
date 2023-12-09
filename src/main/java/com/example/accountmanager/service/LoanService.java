package com.example.accountmanager.service;

import com.example.accountmanager.model.Loan;
import com.example.accountmanager.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LoanService {
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan saveLoan(Loan book) {
        return loanRepository.save(book);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }


}
