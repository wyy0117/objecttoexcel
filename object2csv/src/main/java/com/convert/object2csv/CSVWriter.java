package com.convert.object2csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by wyy on 18-3-29
 */
public class CSVWriter<T> {

    List<T> domainList = new ArrayList<>();
    Map<String, Function<T, String>> mapping = new HashMap<>();
    Map<String, String> init = new HashMap<>();

    public CSVWriter(List<T> domainList) {
        this.domainList = domainList;
    }

    public CSVWriter<T> addInit(String title, String value) {
        this.init.put(title, value);
        return this;
    }

    public CSVWriter<T> addMap(String title, Function<T, String> propertyValue) {
        this.mapping.put(title, propertyValue);
        return this;
    }

    public String write() {
        StringBuffer buffer = new StringBuffer();
        String initValues = getInitValues();
        String titleSetStr = getTitle();
        buffer.append(titleSetStr);
        buffer.append("\n");

        domainList.forEach(domain -> {
                    mapping.forEach((k, v) -> {
                                buffer.append(v.apply(domain));
                                buffer.append(",");
                            }
                    );
                    buffer.append(initValues);
                    buffer.append("\n");
                }
        );


        return buffer.toString();
    }

    private final String getTitle() {
        List<String> titleList = new ArrayList<>();
        titleList.addAll(mapping.keySet());
        titleList.addAll(init.keySet());
        String titleSetStr = titleList.toString();
        return titleSetStr.substring(1, titleSetStr.length() - 1);
    }

    private final String getInitValues() {
        List<String> defaultValues = new ArrayList<>();
        defaultValues.addAll(init.values());
        String defaultValuesStr = defaultValues.toString();
        return defaultValuesStr.substring(1, defaultValuesStr.length() - 1);

    }

}
