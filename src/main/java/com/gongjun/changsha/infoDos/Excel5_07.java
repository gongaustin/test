package com.gongjun.changsha.infoDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
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
 * @Date: Created in 11:16 2020/10/22
 */
public class Excel5_07 {
    public static void todo(String excelPath,String standardPath) {
        if (excelPath == null) return;
        //获取Workbook
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(excelPath));
        //获取Sheet
        Sheet sheetDataOne = workbook.getSheetAt(0);
        int dataBeginRowOne = 6;
        //取列数
        List<Integer> inColsOne = Arrays.asList(1, 4, 6, 12, 18, 24, 30, 36, 49, 52, 55, 58, 61, 64);
        List<List<Object>> data = new ArrayList<>();
        //获取第一张表数据
        for (int i = dataBeginRowOne; i < sheetDataOne.getPhysicalNumberOfRows(); i++) {
            Row row = sheetDataOne.getRow(i);
            List<Object> rowData = new ArrayList<>();
            for (int j = 0; j < inColsOne.size(); j++) {
                Cell cell = row.getCell(inColsOne.get(j));
                Object value = ExcelUtils.getCellValue(cell);
                if (value != null && value instanceof java.lang.String)
                    value = RegUtils.delAllSpaceForString((String) value);
                rowData.add(value);
            }
            if (!CollectionUtils.isEmpty(rowData)) data.add(rowData);
        }


        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardPath));
        Sheet sheetWrite = standardWorkbook.getSheet("5-07");
        int writeBeginRow = 5;

        //写入前清除数据
        for (int i = writeBeginRow; i < sheetWrite.getPhysicalNumberOfRows(); i++) {
            Row row = sheetWrite.getRow(i);
            if(row == null)continue;
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if(cell == null) continue;
                cell.setCellValue((String) null);
            }
        }


        for (int i = writeBeginRow; i < sheetWrite.getPhysicalNumberOfRows(); i++) {
            Row row = sheetWrite.getRow(i);
            if (row == null) continue;
            String hangye = row.getCell(0).getStringCellValue();
            if (StringUtils.isNotBlank(hangye)) hangye = RegUtils.delAllSpaceForString(hangye);
            for (List<Object> rowData : data) {
                if (rowData == null) return;
                if (rowData.get(0) != null && rowData.get(0) instanceof java.lang.String && hangye.equals(rowData.get(0))) {
                    for (int j = 1; j < 14; j++) {
                        Cell cell = row.getCell(j);
                        if(rowData.size()<=j){
                            System.out.println(rowData.toString());
                            cell.setCellValue(0);
                        }
                        else{
                            if (rowData.get(j) instanceof java.lang.Double) cell.setCellValue((double) rowData.get(j));
                            if (rowData.get(j) instanceof java.lang.String) cell.setCellValue((String) rowData.get(j));
                        }
                    }
                }
            }
        }
        ExcelUtils.write2Excel(standardWorkbook, standardPath);
        System.out.println(excelPath+"--处理完毕");
    }

    @Test
    public void test() {
        todo("D:\\长沙项目\\信息化业\\430103信息化经普公报\\5-07.xls","D:\\长沙项目\\信息化业\\430103信息化经普公报\\922-5.xls");
    }
}
