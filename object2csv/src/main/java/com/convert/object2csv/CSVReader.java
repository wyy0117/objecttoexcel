package com.convert.object2csv;

import com.convert.object2csv.exception.CSVReadException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by wyy on 18-3-28
 */
public class CSVReader<T> {

    private Integer startRow = 1;
    private Integer endRow = null;

    //定义每列的转换方法
    private Map<Integer, BiConsumer<T, String>> mapping = new HashMap<>();

    //定义对象的初始化方法
    private List<Consumer<T>> initOjbList = new ArrayList<>();

    //忽略某些列的值
    private List<Integer> ignoreColumns = new ArrayList<>();

    private Supplier<T> constructor = null;

    public CSVReader() {
    }

    public CSVReader(Integer startRow, Integer endRow) {
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public CSVReader<T> setStartRow(Integer startRow) {
        this.startRow = startRow;
        return this;
    }

    public CSVReader<T> setEndRow(Integer endRow) {
        this.endRow = endRow;
        return this;
    }

    public CSVReader<T> ignoreColumn(int column) {
        ignoreColumns.add(column);
        return this;
    }

    //创建对象的方法
    public CSVReader<T> constructor(Supplier<T> constructor) {
        this.constructor = constructor;
        return this;
    }

    //对象的初始化方法
    public CSVReader<T> addInit(Consumer<T> initMethod) {
        initOjbList.add(initMethod);
        return this;
    }

    //每列转换赋值的方法
    public CSVReader<T> addMap(int columnIndex, BiConsumer<T, String> propertyHandler) {
        mapping.put(columnIndex, propertyHandler);
        return this;
    }

    public final List<T> read(File file, Class<T> clazz, Charset charset) throws IOException, InstantiationException, IllegalAccessException, CSVReadException {
        CSVParser parse = CSVParser.parse(file, charset, CSVFormat.DEFAULT);
        return doWork(parse, clazz);
    }

    public final List<T> read(File file, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, CSVReadException {
        CSVParser parse = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
        return doWork(parse, clazz);
    }

    public final List<T> read(String content, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, CSVReadException {
        CSVParser parse = CSVParser.parse(content, CSVFormat.DEFAULT);
        return doWork(parse, clazz);
    }

    public final List<T> read(InputStream inputStream, Charset charset, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, CSVReadException {
        CSVParser parse = CSVParser.parse(inputStream, charset, CSVFormat.DEFAULT);
        return doWork(parse, clazz);
    }

    public final List<T> read(InputStream inputStream, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException, CSVReadException {
        CSVParser parse = CSVParser.parse(inputStream, Charset.defaultCharset(), CSVFormat.DEFAULT);
        return doWork(parse, clazz);
    }

    private final List<T> doWork(CSVParser parser, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, CSVReadException {
        List<T> result = new ArrayList<>();
        List<CSVRecord> records = parser.getRecords();
        if (endRow == null) {
            endRow = records.size();
        }
        for (int i = startRow; i < endRow; i++) {
            T obj = null;
            if (constructor == null) {
                obj = clazz.newInstance();
            } else {
                obj = constructor.get();
            }
            //初始化对象
            for (Consumer<T> init : initOjbList) {
                init.accept(obj);
            }
            CSVRecord record = records.get(i);
            int propertySize = record.size();
            for (int j = 0; j < propertySize; j++) {
                if (!ignoreColumns.contains(j)) {
                    BiConsumer<T, String> propertyHandler = mapping.get(j);
                    if (propertyHandler == null) {
                        throw new CSVReadException("未定义对第" + j + "列的转换");
                    }
                    propertyHandler.accept(obj, record.get(j));
                }
            }

            result.add(obj);

        }
        return result;
    }

}
