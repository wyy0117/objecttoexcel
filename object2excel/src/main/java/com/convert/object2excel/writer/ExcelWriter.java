package com.convert.object2excel.writer;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: wyy
 * @Date: 18-11-15 上午11:16
 */
public class ExcelWriter {

    private List<SheetWriter> sheetList;

    public ExcelWriter(List<SheetWriter> sheetList) {
        this.sheetList = sheetList;
    }

    public void writeTo(OutputStream outputStream) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        for (SheetWriter sheetWriter : sheetList) {//write to every sheet
            sheetWriter.writeTo(workbook);
        }
        workbook.write(outputStream);//write to outputStream
        outputStream.flush();
        outputStream.close();

    }
}
