package MainFiles;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExportUtils {

    private ExportUtils() {
    }

    public static void exportToExcel(
            JTable table,
            String sheetName,
            String defaultFileName,
            Component parent) {

        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    parent,
                    "There is no data to export.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Export to Excel");

        fileChooser.setFileFilter(
                new FileNameExtensionFilter(
                        "Excel files (*.xlsx)", "xlsx"));

        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setSelectedFile(
                new File(defaultFileName + ".xlsx"));

        int result = fileChooser.showSaveDialog(parent);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (!file.getName().toLowerCase(Locale.ROOT)
                .endsWith(".xlsx")) {

            file = new File(file.getAbsolutePath() + ".xlsx");
        }

        if (file.exists()) {

            int confirm = JOptionPane.showConfirmDialog(
                    parent,
                    "This file already exists. Overwrite it?",
                    "Confirm overwrite",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(sheetName);

            // Estilo do cabecalho
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            // Criacao do cabecalho
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < table.getColumnCount(); col++) {

                Cell cell = headerRow.createCell(col);

                cell.setCellValue(table.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }

            // Exportacao dos registros exibidos na tabela
            for (int row = 0; row < table.getRowCount(); row++) {

                Row excelRow = sheet.createRow(row + 1);

                for (int col = 0; col < table.getColumnCount(); col++) {

                    Cell cell = excelRow.createCell(col);

                    Object value = table.getValueAt(row, col);

                    if (value instanceof Number) {

                        cell.setCellValue(
                                ((Number) value).doubleValue());

                    } else if (value != null) {

                        cell.setCellValue(value.toString());
                    }
                }
            }

            // Ajuste automatico da largura das colunas
            for (int col = 0; col < table.getColumnCount(); col++) {

                sheet.autoSizeColumn(col);
            }

            // Mantem o cabecalho visivel
            sheet.createFreezePane(0, 1);

            // Adiciona filtros no proprio Excel
            sheet.setAutoFilter(new CellRangeAddress(
                    0,
                    table.getRowCount(),
                    0,
                    table.getColumnCount() - 1));

            // Salva o arquivo
            try (FileOutputStream output = new FileOutputStream(file)) {

                workbook.write(output);
            }

            JOptionPane.showMessageDialog(
                    parent,
                    "Excel file exported successfully!");

        } catch (IOException | RuntimeException e) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Error exporting Excel file: " + e.getMessage(),
                    "Export error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}