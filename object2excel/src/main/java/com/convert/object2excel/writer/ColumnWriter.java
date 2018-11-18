package com.convert.object2excel.writer;

import com.convert.object2excel.exception.DomainNullFieldException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.activation.UnsupportedDataTypeException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午1:37
 */
public class ColumnWriter<T, R> {

    private String title;
    private boolean nullable = true;//set nullable,if false,will check null value

    private ColumnCellStyle columnCellStyle;
    private XSSFCellStyle xssfCellStyle;

    private Function<T, R> converter;

    public ColumnWriter(String title, ColumnCellStyle style, Function<T, R> converter) {
        this.title = title;
        this.columnCellStyle = style;
        this.converter = converter;
    }

    public ColumnWriter(String title, XSSFCellStyle cellStyle, Function<T, R> converter) {
        this.title = title;
        this.xssfCellStyle = cellStyle;
        this.converter = converter;
    }

    public void write(Sheet sheet, List<T> domainList, int column) throws UnsupportedDataTypeException {

        Workbook workbook = sheet.getWorkbook();

        CellStyle cellStyle = getStyle(workbook);
        //title
        Row titleRow = sheet.getRow(0);
        if (titleRow == null) {
            titleRow = sheet.createRow(0);
        }
        Cell titleCell = titleRow.createCell(column);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(cellStyle);

        //domain data
        for (int i = 0; i < domainList.size(); i++) {
            T domain = domainList.get(i);
            Row row = sheet.getRow(i + 1);
            if (row == null) {
                row = sheet.createRow(i + 1);
            }
            Cell cell = row.createCell(column);

            R value = converter.apply(domain);
            if (value != null) {
                writeValue(cell, value);
            } else if (!nullable) {
                throw new DomainNullFieldException(domain, title);
            }
            cell.setCellStyle(cellStyle);
        }
    }

    private void writeValue(Cell cell, R value) throws UnsupportedDataTypeException {
        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            throw new UnsupportedDataTypeException(value.getClass().getName());
        }

    }


    public boolean isNullable() {
        return nullable;
    }

    /**
     * you can set nullable to check null value
     *
     * @param nullable
     * @return
     */
    public ColumnWriter setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CellStyle getStyle(Workbook workbook) {

        if (columnCellStyle == null) {
            return xssfCellStyle;
        }

        return columnCellStyle.toXSSFCellStyle(workbook);
    }

    public Function<T, R> getConverter() {
        return converter;
    }

    @Override
    public String toString() {
        return "ColumnWriter{" +
                "title='" + title + '\'' +
                ", nullable=" + nullable +
                ", columnCellStyle=" + columnCellStyle +
                ", xssfCellStyle=" + xssfCellStyle +
                ", converter=" + converter +
                '}';
    }
}
