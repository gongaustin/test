package com.gongjun.changsha.FangDiChanDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:45 2020/11/2
 */
@Slf4j
public class Excel_10_04 {
    /**
     * @param: [sourceExcelPath, targetExcelPath]
     * @description: 数据处理方法
     * @author: GongJun
     * @time: Created in 9:16 2020/11/3
     * @modified:
     * @return: void
     **/
    public static void todo(String sourceExcelPath, String targetExcelPath) {
        if (StringUtils.isAnyBlank(sourceExcelPath, targetExcelPath)) return;
        List<List<Object>> data = readData(sourceExcelPath);
        writeData(targetExcelPath, data);
        log.info("表格[{}]--处理完毕", sourceExcelPath);
    }

    public static List<List<Object>> readData(String sourceExcelPath) {
        List<List<Object>> data = new ArrayList<>();
        //获取Workbook
        Workbook sourceWorkbook = ExcelUtils.getWorkbookFromExcel(new File(sourceExcelPath));
        //获取Seet
        Sheet sourceSheet = null;
        for (int i = 0; i < sourceWorkbook.getNumberOfSheets(); i++) {
            String sheetName = sourceWorkbook.getSheetAt(i).getSheetName();
            if (sheetName.contains("10-04")) sourceSheet = sourceWorkbook.getSheetAt(i);
        }
        if (sourceSheet == null) return data;

        List<Integer> sourceExceptRows = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);

        List<Integer> sourceCols = Arrays.asList(0, 9, 12, 16, 17, 20, 23, 26, 31);
        for (int i = 0; i < sourceSheet.getPhysicalNumberOfRows(); i++) {
            if (sourceExceptRows.contains(i)) continue;
            Row row = sourceSheet.getRow(i);
            if (row == null) continue;
            List<Object> rowData = new ArrayList<>();
            for (int j = 0; j < sourceCols.size(); j++) {
                Cell cell = row.getCell(sourceCols.get(j));
                if (cell == null) continue;
                Object value = ExcelUtils.getCellValue(cell);
                rowData.add(value);
            }
            if(!CollectionUtils.isEmpty(rowData)&& rowData.size()>1)data.add(rowData);
        }
        return data;
    }


    public static void writeData(String targetExcelPath, List<List<Object>> data) {
        //目标表
        Workbook targetWorkbook = ExcelUtils.getWorkbookFromExcel(new File(targetExcelPath));
        Sheet targetSheet = targetWorkbook.getSheet("10-04");
        //清除数据
        int dataBeginRow = 5;
        for (int i = dataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
            Row row = targetSheet.getRow(i);
            if (row == null) continue;
            for (int j = 2; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                cell.setCellValue((String) null);
            }
        }

        //写入数据
        for (int i = dataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
            Row row = targetSheet.getRow(i);
            if (row == null || row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue()))
                continue;
            String title = RegUtils.delAllSpaceForString(row.getCell(0).getStringCellValue());
            for (List<Object> rowData : data) {
                Object dataTitle = rowData.get(0);
                if (dataTitle != null && dataTitle instanceof String) {
                    if (title.equals(RegUtils.delAllSpaceForString((String) dataTitle))) {
                        for (int j = 1; j < row.getPhysicalNumberOfCells() - 1; j++) {
                            Cell cell = row.getCell(j + 1);
                            Object value = rowData.get(j);
                            if (value instanceof String && StringUtils.isNotBlank((String) value)) try {
                                cell.setCellValue(Double.valueOf((String) value));
                            } catch (NumberFormatException e) {
                                cell.setCellValue((String) value);
                            }
                            if (value instanceof Double) cell.setCellValue((double) value);
                        }
                    }
                }
            }
        }
        ExcelUtils.write2Excel(targetWorkbook, targetExcelPath);
    }


    @Test
    public void test() {
        todo("D:\\长沙项目\\房地产\\地区\\10-03、10-04芙蓉.xls", "D:\\长沙项目\\房地产\\已处理\\芙蓉\\922-10.XLS");
    }

}
