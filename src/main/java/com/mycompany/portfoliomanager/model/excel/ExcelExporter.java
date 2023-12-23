package com.mycompany.portfoliomanager.model.excel;

import com.mycompany.portfoliomanager.model.amortization.AmortizationRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public void exportAmortizationTableToExcel(List<AmortizationRow> amortizationRows, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Amortization Table");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Payment Number");
            headerRow.createCell(1).setCellValue("Principal");
            headerRow.createCell(2).setCellValue("Interest");
            headerRow.createCell(3).setCellValue("Remaining Balance");

            // Populate data rows
            int rowNum = 1;
            for (AmortizationRow row : amortizationRows) {
                Row excelRow = sheet.createRow(rowNum++);
                excelRow.createCell(0).setCellValue(row.getPaymentNumber());
                excelRow.createCell(1).setCellValue(row.getPrincipal());
                excelRow.createCell(2).setCellValue(row.getInterest());
                excelRow.createCell(3).setCellValue(row.getRemainingBalance());
            }

            // Resize columns to fit content
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}
