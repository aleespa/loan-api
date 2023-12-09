package com.mycompany.loanapi.repository;

import com.mycompany.loanapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findById(Long id);
}