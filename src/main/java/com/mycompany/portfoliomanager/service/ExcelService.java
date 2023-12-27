package com.mycompany.portfoliomanager.service;
import com.mycompany.portfoliomanager.model.Loan;
import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
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
            e.printStackTrace();
        }
        return loans;
    }

    public byte[] exportLoansToExcelWorkbook(List<Loan> loans) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Loans");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Principal");
            headerRow.createCell(2).setCellValue("Start date");
            headerRow.createCell(3).setCellValue("Term");
            headerRow.createCell(4).setCellValue("Rate");
            headerRow.createCell(5).setCellValue("Rate frequency");
            headerRow.createCell(6).setCellValue("Payment frequency");
            headerRow.createCell(7).setCellValue("Fixed payments");

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

            // Fill data rows
            int rowNum = 1;
            for (Loan loan : loans) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(loan.getId());
                row.createCell(1).setCellValue(loan.getPrincipal());

                Cell dateCell = row.createCell(2);
                dateCell.setCellValue(loan.getStartDate());
                dateCell.setCellStyle(dateStyle);

                row.createCell(3).setCellValue(loan.getTerm());
                row.createCell(4).setCellValue(loan.getInterest().getRate());
                row.createCell(5).setCellValue(loan.getInterest().getFrequency().toString());
                row.createCell(6).setCellValue(loan.getPayment_frequency().toString());
                row.createCell(7).setCellValue(loan.getFixed_payments() ? 1 : 0);
            }

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a byte array
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean booleanReader(int i){
        return (i != 0);
    }
}
