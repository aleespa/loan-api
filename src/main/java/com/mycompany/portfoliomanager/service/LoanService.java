package com.mycompany.portfoliomanager.service;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<Loan> saveLoans(List<Loan> loans) {
        return loanRepository.saveAll(loans);
    }

}
