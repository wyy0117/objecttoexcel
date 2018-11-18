package com.convert.object2excel.reader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午6:25
 */
public class ExcelReader {

    private File excelFile;

    /**
     * you can just read some sheet but all.
     * key:sheet index
     * value:sheetReader
     */
    private Map<Integer, SheetReader> sheetIndex_sheetReader_map;

    public ExcelReader(File excelFile, Map<Integer, SheetReader> sheetIndex_sheetReader_map) {
        this.excelFile = excelFile;
        this.sheetIndex_sheetReader_map = sheetIndex_sheetReader_map;
    }

    /**
     * key is the sheet value
     *
     * @return
     */
    public Map<String, List> read() throws IOException, InvalidFormatException {

        Map<String, List> result = new HashMap<>();
        List<Sheet> sheetList = getSheets();

        sheetIndex_sheetReader_map.forEach((index, sheetReader) -> {
            Sheet sheet = sheetList.get(index);
            List domainList = sheetReader.read(sheet);
            result.put(sheet.getSheetName(), domainList);
        });
        return result;

    }

    private List<Sheet> getSheets() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(excelFile);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        List<Sheet> sheetList = new ArrayList<>();

        while (sheetIterator.hasNext()) {
            sheetList.add(sheetIterator.next());
        }
        return sheetList;
    }
}
