package com.convert.object2excel.writer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @Author: wyy
 * @Date: 18-11-19 上午10:46
 */
public interface IColumnCellStyle {

    public CellStyle toXSSFCellStyle(Workbook workbook);
}
