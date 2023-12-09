package com.example.accountmanager.service;

import com.example.accountmanager.model.Loan;
import com.example.accountmanager.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
class LoanServiceTest {


    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveLoan() {
        Loan loan = new Loan(1L, 100_000);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.saveLoan(loan);

        assertThat(savedLoan).isNotNull();
        verify(loanRepository).save(loan);
    }

}
