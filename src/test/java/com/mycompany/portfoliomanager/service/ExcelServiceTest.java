package com.mycompany.portfoliomanager.service;

import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Frequency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ExcelServiceTest {

    private final ExcelService excelService = new ExcelService();

    @Test
    void testProcessExcelFile(@TempDir Path tempDir) throws Exception {
        // Prepare a test file
        URL resource = getClass().getClassLoader().getResource("TestLoans.xlsx");
        Path path = Paths.get(resource.toURI());
        File testFile = path.toFile();

        try (FileInputStream fis = new FileInputStream(testFile)) {
            MultipartFile multipartFile = new MockMultipartFile("file", fis);

            // Call the method to test
            List<Loan> loans = excelService.processExcelFile(multipartFile);

            // Assertions
            assertNotNull(loans);

            assertEquals(loans.size(), 14);

            assertEquals(loans.get(0).getId(), 1);
            assertEquals(loans.get(0).getPrincipal(), 10000);
            assertEquals(loans.get(0).getTerm(), 27);
            assertEquals(loans.get(0).getInterest().getRate(), 0.005);
            assertEquals(loans.get(0).getInterest().getFrequency(), Frequency.Monthly);
            assertEquals(loans.get(0).getPayment_frequency(), Frequency.Monthly);
            assertEquals(loans.get(0).getFixed_payments(), Boolean.TRUE);


        }
    }



}