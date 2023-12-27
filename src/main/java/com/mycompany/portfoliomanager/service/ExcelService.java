package com.mycompany.portfoliomanager.service;
import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public List<Loan> processExcelFile(MultipartFile file) {
        List<Loan> loans = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean firstRow = true;

            for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                Interest interest = Interest.builder()
                        .frequency(Frequency.fromString(row.getCell(5).getStringCellValue()))
                        .rate(row.getCell(4).getNumericCellValue())
                        .build();
                Loan loan = Loan.builder()
                        .id((long) row.getCell(0).getNumericCellValue())
                        .principal(row.getCell(1).getNumericCellValue())
                        .interest(interest)
                        .term((int) row.getCell(3).getNumericCellValue())
                        .startDate(LocalDate.from(row.getCell(2).getLocalDateTimeCellValue()))
                        .payment_frequency(Frequency.fromString(row.getCell(6).getStringCellValue()))
                        .fixed_payments(booleanReader((int) row.getCell(7).getNumericCellValue()))
                        .build();

                loans.add(loan);
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }
        return loans;
    }
    private boolean booleanReader(int i){
        return (i != 0);
    }
}
