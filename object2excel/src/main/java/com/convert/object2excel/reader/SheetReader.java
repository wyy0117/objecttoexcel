package com.convert.object2excel.reader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午6:33
 */
public class SheetReader<T> {

    private Supplier<T> createInstance;//create domain instance
    /**
     * key:column number
     * value:cell reader
     */
    private Map<Integer, CellReader<T>> columnIndex_cellReader;
    private int fromRow = 1;
    private int endRow = 0;

    public SheetReader(Supplier<T> createInstance, Map<Integer, CellReader<T>> columnIndex_cellReader) {
        this.createInstance = createInstance;
        this.columnIndex_cellReader = columnIndex_cellReader;
    }

    public SheetReader(Supplier<T> createInstance, Map<Integer, CellReader<T>> columnIndex_cellReader, int fromRow, int endRow) {
        this.createInstance = createInstance;
        this.columnIndex_cellReader = columnIndex_cellReader;
        this.fromRow = fromRow;
        this.endRow = endRow;
    }

    public List<T> read(Sheet sheet) {

        List<T> result = new ArrayList<>();
        if (endRow == 0) {
            endRow = sheet.getLastRowNum();
        }

        int i = fromRow;//avoid 2 sheet use the same sheetReader,can't override fromRow value
        while (i < endRow + 1) {
            Row row = sheet.getRow(i);
            T domain = createInstance.get();
            result.add(domain);
            columnIndex_cellReader.forEach((index, cellReader) -> {
                Cell cell = row.getCell(index);
                cellReader.readCell(cell, domain);
            });
            i++;
        }
        return result;
    }
}
