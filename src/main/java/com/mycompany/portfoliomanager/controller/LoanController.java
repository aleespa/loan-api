package com.mycompany.portfoliomanager.controller;
import com.mycompany.portfoliomanager.repository.LoanRepository;
import com.mycompany.portfoliomanager.service.ExcelService;
import com.mycompany.portfoliomanager.service.LoanService;

import com.mycompany.portfoliomanager.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Loan addLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<List<Loan>> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Loan> loans = excelService.processExcelFile(file);
        List<Loan> savedLoans = loanService.saveLoans(loans);

        return ResponseEntity.ok(savedLoans);
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportLoansToExcel() throws IOException {
        List<Loan> loans = loanRepository.findAll();

        byte[] excelFileContent = excelService.exportLoansToExcelWorkbook(loans);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=loans.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFileContent);
    }
}