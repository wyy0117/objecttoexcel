package com.convert.object2excel;

import com.convert.object2excel.domain.ComponentProcessDTO;
import com.convert.object2excel.domain.ProcessDTO;
import com.convert.object2excel.reader.CellReader;
import com.convert.object2excel.reader.ExcelReader;
import com.convert.object2excel.reader.SheetReader;
import com.convert.object2excel.writer.ColumnCellStyle;
import com.convert.object2excel.writer.ColumnWriter;
import com.convert.object2excel.writer.ExcelWriter;
import com.convert.object2excel.writer.SheetWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: wyy
 * @Date: 18-11-15 上午10:41
 * <p>
 * note:this test is just for myself.
 */
public class MyTestClient {

    @Test
    public void testWrite() throws IOException {
        Date date = new Date();
        ProcessDTO yuzhiProcessDTO = new ProcessDTO("预制", date, null, null, null);
        ProcessDTO jinchangProcessDTO = new ProcessDTO("进场", date, date, null, null);
        ProcessDTO anzhuangProcessDTO = new ProcessDTO("安装", date, date, date, null);
        ColumnCellStyle lockStyle = new ColumnCellStyle(true);
        ColumnCellStyle unLockStyle = new ColumnCellStyle(false);
        ColumnCellStyle dateStyle = new ColumnCellStyle(false, "yyyy/mm/dd hh:mm");


        List<ComponentProcessDTO> sheet1DomainList = new ArrayList<>();
        ComponentProcessDTO componentProcessDTO1 = new ComponentProcessDTO("1-GXBQ-01-YU-OLD", "一阶段管线搬迁前左侧区域-搬迁的现状雨水管", "", Arrays.asList(yuzhiProcessDTO, jinchangProcessDTO, anzhuangProcessDTO), "eed7a000-beb5-46aa-af2c-7a7bae6cbc4c");
        sheet1DomainList.add(componentProcessDTO1);
        sheet1DomainList.add(componentProcessDTO1);
        sheet1DomainList.add(componentProcessDTO1);
        sheet1DomainList.add(componentProcessDTO1);
        sheet1DomainList.add(componentProcessDTO1);

        List<ColumnWriter> sheet1ColumnWriterList = new ArrayList<>();
        sheet1ColumnWriterList.add(new ColumnWriter<>("构件编码", lockStyle, ComponentProcessDTO::getCode));
        sheet1ColumnWriterList.add(new ColumnWriter<>("构件名称", unLockStyle, ComponentProcessDTO::getName));
        sheet1ColumnWriterList.add(new ColumnWriter<>("构件描述信息", unLockStyle, ComponentProcessDTO::getDescription));

        List<ProcessDTO> processDTOList1 = componentProcessDTO1.getProcessDTOList();
        for (int i = 0; i < processDTOList1.size(); i++) {
            ProcessDTO processDTO = processDTOList1.get(i);
            final int index = i;
            sheet1ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanStartTime()));
            sheet1ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanEndTime()));
        }

        for (int i = 0; i < processDTOList1.size(); i++) {
            ProcessDTO processDTO = processDTOList1.get(i);
            final int index = i;
            sheet1ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualStartTime()));
            sheet1ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualEndTime()));
        }
        sheet1ColumnWriterList.add(new ColumnWriter<>("构建二维码", unLockStyle, ComponentProcessDTO::getComponentCode));


        List<ComponentProcessDTO> sheet2DomainList = new ArrayList<>();
        ComponentProcessDTO componentProcessDTO2 = new ComponentProcessDTO("1-GXBQ-01-YU-OLD", "一阶段管线搬迁前左侧区域-搬迁的现状雨水管", "", Arrays.asList(yuzhiProcessDTO, jinchangProcessDTO), "eed7a000-beb5-46aa-af2c-7a7bae6cbc4c");

        sheet2DomainList.add(componentProcessDTO2);
        sheet2DomainList.add(componentProcessDTO2);
        sheet2DomainList.add(componentProcessDTO2);
        sheet2DomainList.add(componentProcessDTO2);

        List<ColumnWriter> sheet2ColumnWriterList = new ArrayList<>();
        sheet2ColumnWriterList.add(new ColumnWriter<>("构件编码", lockStyle, ComponentProcessDTO::getCode));
        sheet2ColumnWriterList.add(new ColumnWriter<>("构件名称", unLockStyle, ComponentProcessDTO::getName));
        sheet2ColumnWriterList.add(new ColumnWriter<>("构件描述信息", unLockStyle, ComponentProcessDTO::getDescription));

        List<ProcessDTO> processDTOList2 = componentProcessDTO2.getProcessDTOList();
        for (int i = 0; i < processDTOList2.size(); i++) {
            ProcessDTO processDTO = processDTOList2.get(i);
            final int index = i;
            sheet2ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanStartTime()));
            sheet2ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanEndTime()));
        }

        for (int i = 0; i < processDTOList2.size(); i++) {
            ProcessDTO processDTO = processDTOList2.get(i);
            final int index = i;
            sheet2ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualStartTime()));
            sheet2ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualEndTime()));
        }
        sheet2ColumnWriterList.add(new ColumnWriter<>("构建二维码", unLockStyle, ComponentProcessDTO::getComponentCode));

        List<ComponentProcessDTO> sheet3DomainList = new ArrayList<>();
        ComponentProcessDTO componentProcessDTO3 = new ComponentProcessDTO("1-GXBQ-01-YU-OLD", "一阶段管线搬迁前左侧区域-搬迁的现状雨水管", "", Arrays.asList(yuzhiProcessDTO), "eed7a000-beb5-46aa-af2c-7a7bae6cbc4c");

        sheet3DomainList.add(componentProcessDTO3);
        sheet3DomainList.add(componentProcessDTO3);
        sheet3DomainList.add(componentProcessDTO3);

        List<ColumnWriter> sheet3ColumnWriterList = new ArrayList<>();
        sheet3ColumnWriterList.add(new ColumnWriter<>("构件编码", lockStyle, ComponentProcessDTO::getCode));
        sheet3ColumnWriterList.add(new ColumnWriter<>("构件名称", unLockStyle, ComponentProcessDTO::getName));
        sheet3ColumnWriterList.add(new ColumnWriter<>("构件描述信息", unLockStyle, ComponentProcessDTO::getDescription));

        List<ProcessDTO> processDTOList3 = componentProcessDTO3.getProcessDTOList();
        for (int i = 0; i < processDTOList3.size(); i++) {
            ProcessDTO processDTO = processDTOList3.get(i);
            final int index = i;
            sheet3ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanStartTime()));
            sheet3ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "计划结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getPlanEndTime()));
        }

        for (int i = 0; i < processDTOList3.size(); i++) {
            ProcessDTO processDTO = processDTOList3.get(i);
            final int index = i;
            sheet3ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际开始", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualStartTime()));
            sheet3ColumnWriterList.add(new ColumnWriter<ComponentProcessDTO, Date>(processDTO.getName() + "实际结束", dateStyle,
                    componentProcessDTO -> componentProcessDTO.getProcessDTOList().get(index).getActualEndTime()));
        }

        sheet3ColumnWriterList.add(new ColumnWriter<>("构建二维码", unLockStyle, ComponentProcessDTO::getComponentCode));


        List<SheetWriter> sheetWriterList = new ArrayList<>();
        sheetWriterList.add(new SheetWriter<>("firstsheet", sheet1DomainList, sheet1ColumnWriterList));
        sheetWriterList.add(new SheetWriter<>("secondsheet", sheet2DomainList, sheet2ColumnWriterList));
        sheetWriterList.add(new SheetWriter<>("thirdsheet", sheet3DomainList, sheet3ColumnWriterList));

        File file = new File("./src/test/resource/测试类型.xlsx");
        if (!file.exists()) {
            System.out.println("create file");
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        new ExcelWriter(sheetWriterList).writeTo(fileOutputStream);
    }

    @Test
    public void testRead() throws IOException, InvalidFormatException {

        File file = new File("./src/test/resource/测试类型.xlsx");

        Workbook workbook = WorkbookFactory.create(file);
        Map<Integer, SheetReader> sheet_sheetReader_map = new HashMap<>();

        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        int sheetIndex = 0;
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Map<Integer, CellReader<ComponentProcessDTO>> column_cellReader_map = new HashMap<>();
            //前面通用的read方式
            CellReader<ComponentProcessDTO> codeReader = new CellReader<>((domain, cell) -> {
                String value = cell.getStringCellValue();
                domain.setCode(value);
            });

            CellReader<ComponentProcessDTO> nameReader = new CellReader<>((domain, cell) -> {
                String value = cell.getStringCellValue();
                domain.setName(value);
            });

            CellReader<ComponentProcessDTO> descReader = new CellReader<>((domain, cell) -> {
                String value = cell.getStringCellValue();
                domain.setDescription(value);
            });
            column_cellReader_map.put(0, codeReader);
            column_cellReader_map.put(1, nameReader);
            column_cellReader_map.put(2, descReader);

            Row row = sheet.getRow(0);
            short columnNumber = row.getLastCellNum();//列数
            int processCount = (columnNumber - 4) / 4;//工序的数量
            Map<Integer, String> processIndex_processName_map = new HashMap<>();
            for (int i = 0; i < processCount; i++) {
                String title = row.getCell(3 + i * 2).getStringCellValue();
                processIndex_processName_map.put(i, title);
            }

            //需要根据列数添加不同数量的reader
            for (int i = 0; i < processCount; i++) {
                final int index = i;
                CellReader<ComponentProcessDTO> planStartTimeReader = new CellReader<>((domain, cell) -> {
                    Date value = cell.getDateCellValue();
                    ProcessDTO processDTO = new ProcessDTO();
                    processDTO.setName(processIndex_processName_map.get(index));
                    List<ProcessDTO> processDTOList = domain.getProcessDTOList();
                    processDTOList.add(processDTO);
                    processDTO.setPlanStartTime(value);
                });

                CellReader<ComponentProcessDTO> planEndTimeReader = new CellReader<>((domain, cell) -> {
                    Date value = cell.getDateCellValue();
                    ProcessDTO processDTO = domain.getProcessDTOList().get(index);
                    processDTO.setPlanEndTime(value);
                });

                CellReader<ComponentProcessDTO> actualStartTimeReader = new CellReader<>((domain, cell) -> {
                    Date value = cell.getDateCellValue();
                    ProcessDTO processDTO = domain.getProcessDTOList().get(index);
                    processDTO.setActualStartTime(value);
                });

                CellReader<ComponentProcessDTO> actualEndTimeReader = new CellReader<>((domain, cell) -> {
                    Date value = cell.getDateCellValue();
                    ProcessDTO processDTO = domain.getProcessDTOList().get(index);
                    processDTO.setActualEndTime(value);
                });
                column_cellReader_map.put(3 + index * 2, planStartTimeReader);
                column_cellReader_map.put(4 + index * 2, planEndTimeReader);
                column_cellReader_map.put(3 + processCount * 2 + index * 2, actualStartTimeReader);
                column_cellReader_map.put(4 + processCount * 2 + index * 2, actualEndTimeReader);
            }

            CellReader<ComponentProcessDTO> componentCodeReader = new CellReader<>((domain, cell) -> {
                String value = cell.getStringCellValue();
                domain.setComponentCode(value);
            });
            column_cellReader_map.put(columnNumber - 1, componentCodeReader);


            SheetReader<ComponentProcessDTO> sheetReader = new SheetReader<>(ComponentProcessDTO::new, column_cellReader_map);

            sheet_sheetReader_map.put(sheetIndex++, sheetReader);
        }
        ExcelReader excelReader = new ExcelReader(file, sheet_sheetReader_map);
        Map<String, List> result = excelReader.read();
        System.out.println("result = " + result.size());
    }
}
