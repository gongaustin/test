package com.gongjun.changsha.GongYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
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
 * @Date: Created in 10:10 2020/10/28
 */
public class Excel6_02 {
    public static void todo(String sourceExcelPath, String targetExcelPath) {
        //获取数据
        Workbook sourceWorkbook = ExcelUtils.getWorkbookFromExcel(new File(sourceExcelPath));
        Sheet sourceSheet = sourceWorkbook.getSheetAt(1);
        //不读的行数
        List<Integer> sourceExceptRows = Arrays.asList(0, 1);
        //读取的列数
        List<Integer> sourceCols = Arrays.asList(0, 1, 2, 3, 4, 5);


        List<List<Object>> data = new ArrayList<>();
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
            data.add(rowData);
        }


        //目标表
        Workbook targetWorkbook = ExcelUtils.getWorkbookFromExcel(new File(targetExcelPath));
        Sheet targetSheet = targetWorkbook.getSheet("6-02");

        //清除数据
        int dataBeginRow = 2;
        for (int i = dataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
            Row row = targetSheet.getRow(i);
            if (row == null) continue;
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
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
                    if (RegUtils.delAllSpaceForString((String) dataTitle).contains(title)) {
                        for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                            Cell cell = row.getCell(j);
                            Object value = rowData.get(j);
                            if (value instanceof String && StringUtils.isNotBlank((String) value))
                                cell.setCellValue(Double.valueOf((String) value));
                            if (value instanceof Double) cell.setCellValue((double) value);
                        }
                    }
                }
            }
        }

        ExcelUtils.write2Excel(targetWorkbook, targetExcelPath);
        System.out.println("文件[" + sourceExcelPath + "]--处理完毕");
    }


    @Test
    public void test() {
        todo("D:\\长沙项目\\工业\\地区\\芙蓉\\02芙蓉区.xls", "D:\\长沙项目\\工业\\处理后\\地区\\芙蓉\\922-6.XLS");
    }
}
