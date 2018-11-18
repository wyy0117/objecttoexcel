package com.convert.object2excel.reader;

import org.apache.poi.ss.usermodel.Cell;

import java.util.function.BiConsumer;

/**
 * @Author: wyy
 * @Date: 18-11-16 上午10:14
 */
public class CellReader<T> {

    public BiConsumer<T, Cell> setValue;

    public CellReader(BiConsumer<T, Cell> setValue) {
        this.setValue = setValue;
    }

    public void readCell(Cell cell, T domain) {

        setValue.accept(domain,cell);
    }

}
