package com.convert.object2csv;

import com.convert.object2csv.domain.SysCode;
import com.convert.object2csv.exception.CSVReadException;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyy on 18-3-29
 */
public class Client {

    @Test
    public void testRead() throws CSVReadException, InstantiationException, IllegalAccessException, IOException {
        String path = ClassLoader.getSystemResource("syscodelist.csv").getPath();
        File file = new File(path);
        System.out.println("file.exists() = " + file.exists());
        CSVReader<SysCode> csvReader = new CSVReader<>();
        List<SysCode> result = csvReader.setStartRow(1)
                .constructor(SysCode::new)
                .addInit((obj) -> obj.setType("defaultType"))
                .addMap(0, (obj, value) -> obj.setId(Long.parseLong(value)))
                .addMap(1, SysCode::setCode)
                .addMap(2, SysCode::setName)
                .ignoreColumn(3)
                .addMap(4, (obj, value) -> obj.setOrderNo(Integer.parseInt(value)))
                .setEndRow(3)
                .read(file, SysCode.class);
        System.out.println("result = " + result);
    }

    @Test
    public void testWrite() throws IOException {
        SysCode sysCode = new SysCode();
        sysCode.setId(123);
        sysCode.setCode("code");
        sysCode.setName("name");
        sysCode.setDescription("desc");
        sysCode.setOrderNo(1);
        sysCode.setType("defaultType");
        List<SysCode> list = new ArrayList<>();
        list.add(sysCode);
        list.add(sysCode);

        CSVWriter<SysCode> csvWriter = new CSVWriter<>(list);
        csvWriter.addInit("defTitle1", "defValue1");
        csvWriter.addInit("defTitle2", "defValue2");
        csvWriter.addMap("id", obj -> String.valueOf(obj.getId()));
        csvWriter.addMap("code", SysCode::getCode);
        csvWriter.addMap("name", SysCode::getName);

        String result = csvWriter.write();
//        System.out.println(result);
        String basePath = ClassLoader.getSystemResource("write_result.csv").getPath();
        System.out.println("basePath = " + basePath);
        File file = new File(basePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream outSTr = new FileOutputStream(file);
        BufferedOutputStream buff = new BufferedOutputStream(outSTr);

        buff.write(result.getBytes());
        buff.flush();
        buff.close();

    }
}
