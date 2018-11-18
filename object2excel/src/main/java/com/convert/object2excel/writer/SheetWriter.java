package com.convert.object2excel.writer;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;

import javax.activation.UnsupportedDataTypeException;
import java.util.List;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午1:36
 */
public class SheetWriter<T> {

    private String name;

    private List<T> domainList;

    private List<ColumnWriter> columnList;

    public SheetWriter(String name, List<T> domainList, List<ColumnWriter> columnList) {
        this.name = name;
        this.domainList = domainList;
        this.columnList = columnList;
    }

    public void writeTo(XSSFWorkbook workbook) throws UnsupportedDataTypeException {
        XSSFSheet sheet = workbook.createSheet(name);
        sheet.enableLocking();
        CTSheetProtection sheetProtection = sheet.getCTWorksheet().getSheetProtection();
        sheetProtection.setFormatCells(false);
        sheetProtection.setFormatColumns(false);
        sheetProtection.setFormatRows(false);
        sheet.setSheetPassword(String.valueOf(System.currentTimeMillis()), null);

        for (int i = 0; i < columnList.size(); i++) {
            ColumnWriter columnWriter = columnList.get(i);
            columnWriter.write(sheet, domainList, i);
        }
    }


    @Override
    public String toString() {
        return "SheetWriter{" +
                "name='" + name + '\'' +
                ", domainList=" + domainList +
                ", columnList=" + columnList +
                '}';
    }
}
