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
            headerRow.createCell(1).setCellValue("Payment Date");
            headerRow.createCell(2).setCellValue("Principal");
            headerRow.createCell(3).setCellValue("Interest");
            headerRow.createCell(4).setCellValue("Remaining Balance");

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));


            CellStyle style = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("#,##0.00"));

            // Populate data rows
            int rowNum = 1;
            for (AmortizationRow row : amortizationRows) {
                Row excelRow = sheet.createRow(rowNum++);
                excelRow.createCell(0).setCellValue(row.getPaymentNumber());

                Cell dateCell = excelRow.createCell(1);
                dateCell.setCellValue(row.getPaymentDate());
                dateCell.setCellStyle(dateStyle);

                Cell principalCell = excelRow.createCell(2);
                principalCell.setCellValue(row.getPrincipal());
                principalCell.setCellStyle(style);

                Cell interestCell = excelRow.createCell(3);
                interestCell.setCellValue(row.getInterest());
                interestCell.setCellStyle(style);

                Cell balanceCell = excelRow.createCell(4);
                balanceCell.setCellValue(row.getRemainingBalance());
                balanceCell.setCellStyle(style);


            }
            // Resize columns to fit content
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}
