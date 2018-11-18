package com.convert.object2excel.writer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午1:59
 */
public class ColumnCellStyle {

    private boolean lock;

    private String dateFormat;

    public ColumnCellStyle(boolean lock) {
        this(lock, null);
    }

    public ColumnCellStyle(boolean lock, String dateFormat) {
        this.lock = lock;
        this.dateFormat = dateFormat;
    }

    public CellStyle toXSSFCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setLocked(lock);
        if (lock) {
            style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        }
        if (dateFormat != null) {
            short format = workbook.createDataFormat().getFormat(dateFormat);
            style.setDataFormat(format);
        }
        return style;
    }

    @Override
    public String toString() {
        return "ColumnCellStyle{" +
                "lock=" + lock +
                ", dateFormat='" + dateFormat + '\'' +
                '}';
    }
}
