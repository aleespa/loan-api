package com.mycompany.portfoliomanager.controller;

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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

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

    @Mock
    private ExcelService excelService;

    @InjectMocks
    private LoanController loanController;

    private MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Mock the Excel service response
        given(excelService.processExcelFile(any(MultipartFile.class)))
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

}