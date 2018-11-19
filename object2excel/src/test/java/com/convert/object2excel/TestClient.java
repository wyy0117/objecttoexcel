package com.convert.object2excel;

import com.convert.object2excel.domain.UserDTO;
import com.convert.object2excel.reader.CellReader;
import com.convert.object2excel.reader.ExcelReader;
import com.convert.object2excel.reader.SheetReader;
import com.convert.object2excel.writer.ColumnCellStyle;
import com.convert.object2excel.writer.ColumnWriter;
import com.convert.object2excel.writer.ExcelWriter;
import com.convert.object2excel.writer.SheetWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.binaryrc4.BinaryRC4Decryptor;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: wyy
 * @Date: 2018/11/18 20:26
 */
public class TestClient {

    @Test
    public void testWriter() throws IOException {
        UserDTO user1 = new UserDTO("aa", true, 170.5, new Date());
        UserDTO user2 = new UserDTO("bb", false, 172.0, new Date());
        UserDTO user3 = new UserDTO("cc", false, 168.2, new Date());
        UserDTO user4 = new UserDTO("dd", true, 180.5, new Date());
        UserDTO user5 = new UserDTO("ee", false, 180.3, new Date());
        UserDTO user6 = new UserDTO("ff", true, 177.5, new Date());
        ColumnCellStyle lockStyle = new ColumnCellStyle(true);
        ColumnCellStyle unLockStyle = new ColumnCellStyle(false);
        ColumnCellStyle dateStyle = new ColumnCellStyle(false, "yyyy/mm/dd hh:mm");

        ColumnWriter<UserDTO, String> nameWriter = new ColumnWriter<>("name", lockStyle, UserDTO::getName);
        ColumnWriter<UserDTO,Boolean>genderWriter = new ColumnWriter<>("gender",unLockStyle,UserDTO::getGender);
        ColumnWriter<UserDTO,Double>heightWriter = new ColumnWriter<>("height",unLockStyle,UserDTO::getHeight);
        ColumnWriter<UserDTO,Date>birthdayWriter = new ColumnWriter<>("birthday",dateStyle,UserDTO::getBirthday);

        List<ColumnWriter>columnWriter1 = new ArrayList<>();
        columnWriter1.add(nameWriter);
        columnWriter1.add(genderWriter);
        columnWriter1.add(birthdayWriter);

        List<UserDTO> sheetUserDTO1 = new ArrayList<>();
        sheetUserDTO1.add(user1);
        sheetUserDTO1.add(user2);
        sheetUserDTO1.add(user3);
        SheetWriter<UserDTO> sheetWriter1 = new SheetWriter<>("first", sheetUserDTO1, columnWriter1);

        List<UserDTO>sheetUserDTO2= new ArrayList<>();
        sheetUserDTO2.add(user4);
        sheetUserDTO2.add(user5);
        sheetUserDTO2.add(user6);

        List<ColumnWriter>columnWriter2 = new ArrayList<>();
        columnWriter2.add(nameWriter);
        columnWriter2.add(genderWriter);
        columnWriter2.add(heightWriter);
        columnWriter2.add(birthdayWriter);
        SheetWriter<UserDTO>sheetWriter2 = new SheetWriter<>("second",sheetUserDTO2,columnWriter2);


        File file = new File("./src/test/resource/test.xlsx");
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        new ExcelWriter(Arrays.asList(sheetWriter1,sheetWriter2)).writeTo(outputStream);
    }

    @Test
    public void testReader() throws IOException, InvalidFormatException {
        File file = new File("./src/test/resource/test.xlsx");

        CellReader<UserDTO> nameReader = new CellReader<>(((userDTO, cell) -> {
            String name = cell.getStringCellValue();
            userDTO.setName(name);
        }));

        CellReader<UserDTO>genderReader = new CellReader<>(((userDTO, cell) -> {
            boolean gender = cell.getBooleanCellValue();
            userDTO.setGender(gender);
        }));

        CellReader<UserDTO>heightReader = new CellReader<>((userDTO, cell) -> {
            double height = cell.getNumericCellValue();
            userDTO.setHeight(height);
        });

        CellReader<UserDTO> birthdayReader = new CellReader<>((userDTO, cell) -> {
            Date birthday = cell.getDateCellValue();
            userDTO.setBirthday(birthday);
        });

        Map<Integer,CellReader<UserDTO>>index_cellReader1 = new HashMap<>();
        index_cellReader1.put(0,nameReader);
        index_cellReader1.put(1,genderReader);
        index_cellReader1.put(2,heightReader);

        Map<Integer,CellReader<UserDTO>>index_cellReader2 = new HashMap<>();
        index_cellReader2.put(0,nameReader);
        index_cellReader2.put(1,genderReader);
        index_cellReader2.put(2,heightReader);
        index_cellReader2.put(3,birthdayReader);



        SheetReader<UserDTO> sheetReader1 = new SheetReader<>(UserDTO::new,index_cellReader1);
        SheetReader<UserDTO> sheetReader2 = new SheetReader<>(UserDTO::new,index_cellReader2);
        Map<Integer, SheetReader>index_sheetReader_map = new HashMap<>();
        index_sheetReader_map.put(0,sheetReader1);
        index_sheetReader_map.put(1,sheetReader2);
        Map<String, List> result = new ExcelReader(file, index_sheetReader_map).read();
        System.out.println("result.size() = " + result.size());
    }
}
