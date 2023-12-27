package com.mycompany.portfoliomanager.service;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.amortization.AmortizationRow;
import com.mycompany.portfoliomanager.model.amortization.AmortizationTable;
import com.mycompany.portfoliomanager.model.excel.ExcelExporter;
import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import com.mycompany.portfoliomanager.repository.LoanRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

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
                .principal(100_000.0)
                .build();
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.saveLoan(loan);

        assertThat(savedLoan).isNotNull();
        verify(loanRepository).save(loan);
    }

    @Test
    void testAddLoanAndExportAmortization() throws Exception {
        LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
        Interest interest = Interest.builder()
                .frequency(Frequency.Annual)
                .rate(0.0575)
                .build();
        Loan loan =Loan.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .principal(100_000.0)
                .interest(interest)
                .term(70)
                .payment_frequency(Frequency.Monthly)
                .build();
        Mockito.when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(loan);

        // Assuming AmortizationTable and AmortizationRow are defined in your code
        AmortizationTable table = new AmortizationTable(loan);
        List<AmortizationRow> amortizationRows = table.getRows();

        ExcelExporter exporter = new ExcelExporter();
        String filePath = "target/test_amortization_table.xlsx";
        exporter.exportAmortizationTableToExcel(amortizationRows, filePath);

        // Here you can add assertions or checks to validate the test
        // For example, checking if the file exists, or the repository was called
    }

}
