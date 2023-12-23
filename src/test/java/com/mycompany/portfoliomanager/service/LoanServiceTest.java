package com.mycompany.portfoliomanager.service;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

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
        Loan loan =Loan.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .amount(100_000)
                .build();
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.saveLoan(loan);

        assertThat(savedLoan).isNotNull();
        verify(loanRepository).save(loan);
    }

}
