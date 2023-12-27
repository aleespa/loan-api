package com.mycompany.portfoliomanager.controller;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import com.mycompany.portfoliomanager.repository.LoanRepository;
import com.mycompany.portfoliomanager.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class LoanControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ExcelService excelService;
    @Mock
    private ExcelService mockExcelService;
    @Autowired
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanController loanController;

    private MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Mock the Excel service response
        given(mockExcelService.processExcelFile(any(MultipartFile.class)))
                .willReturn(Collections.emptyList());

        byte[] fileContent = Files.readAllBytes(Paths.get("src/test/resources/TestLoans.xlsx"));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "TestLoans.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fileContent
        );

        // Perform the request and assert the response
        mockMvc.perform(multipart("/loans/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoanExportExcel() throws IOException {
        Interest interest = Interest.builder()
                .frequency(Frequency.Annual)
                .rate(0.0575)
                .build();
        Loan loan1 = Loan.builder()
                .id(1L)
                .principal(100_000.0)
                .startDate(LocalDate.now())
                .term(70)
                .interest(interest)
                .payment_frequency(Frequency.Monthly)
                .fixed_payments(Boolean.TRUE)
                .build();
        Loan loan2 = Loan.builder()
                .id(2L)
                .principal(100_000.0)
                .startDate(LocalDate.now())
                .term(70)
                .interest(interest)
                .payment_frequency(Frequency.Monthly)
                .fixed_payments(Boolean.TRUE)
                .build();

        loanRepository.saveAll(Arrays.asList(loan1, loan2));


        List<Loan> loans = loanRepository.findAll();

        byte[] excelData = excelService.exportLoansToExcelWorkbook(loans);

        // Write to a file for testing purposes
        File outputFile = new File("target/test_loans_export.xlsx");
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(excelData);
        }

        // Assertions
        assertTrue(outputFile.exists() && outputFile.length() > 0);
        // Additional assertions can be made to check the content of the file
    }

    @Test
    public void roundTripLoanExcel() throws Exception {
        Interest interest = Interest.builder()
                .frequency(Frequency.Annual)
                .rate(0.0575)
                .build();
        Loan loan1 = Loan.builder()
                .id(1L)
                .principal(100_000.0)
                .startDate(LocalDate.now())
                .term(70)
                .interest(interest)
                .payment_frequency(Frequency.Monthly)
                .fixed_payments(Boolean.TRUE)
                .build();
        Loan loan2 = Loan.builder()
                .id(2L)
                .principal(100_000.0)
                .startDate(LocalDate.now())
                .term(70)
                .interest(interest)
                .payment_frequency(Frequency.Monthly)
                .fixed_payments(Boolean.TRUE)
                .build();

        loanRepository.saveAll(Arrays.asList(loan1, loan2));


        List<Loan> originalLoans = loanRepository.findAll();

        byte[] excelData = excelService.exportLoansToExcelWorkbook(originalLoans);

        // Write to a file for testing purposes
        File outputFile = new File("target/round_trip.xlsx");
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(excelData);
        }
        Path path = Paths.get("target/round_trip.xlsx");
        byte[] content = Files.readAllBytes(path);
        MultipartFile multipartFile = new MockMultipartFile("file", content);
        List<Loan> importedLoans = excelService.processExcelFile(multipartFile);

        for (int i = 0; i < originalLoans.size(); i++) {
            compareLoans(originalLoans.get(i), importedLoans.get(i)); // Implement comparison logic
        }

    }

    private void compareLoans(Loan original, Loan imported) {
        assertEquals(original.getId(), imported.getId());
        assertEquals(original.getPrincipal(), imported.getPrincipal(), 0.001);

    }
}