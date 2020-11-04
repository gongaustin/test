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
 * @Description:分行业企业使用计算机情况
 * @Author: GongJun
 * @Date: Created in 11:15 2020/10/22
 */
public class Excel5_01 {
    public static void todo(String excelPath, String standardPath) {
        if (excelPath == null) return;
        //获取Workbook
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(excelPath));
        //获取Sheet
        Sheet sheetDataOne = workbook.getSheetAt(0);
        int dataBeginRowOne = 6;
        //取列数
        List<Integer> inColsOne = Arrays.asList(1, 3, 4, 5);
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


        Sheet sheetDataTwo = workbook.getSheetAt(1);
        int dataBeginRowTwo = 6;
        List<Integer> inColsTwo = Arrays.asList(1, 3, 4);
        for (int i = dataBeginRowTwo; i < sheetDataTwo.getPhysicalNumberOfRows(); i++) {
            Row row = sheetDataTwo.getRow(i);
            for (int j = 1; j < inColsTwo.size(); j++) {
                Cell cellTitle = row.getCell(inColsTwo.get(0));
                Object titleValue = ExcelUtils.getCellValue(cellTitle);
                if (titleValue != null && titleValue instanceof java.lang.String)
                    titleValue = RegUtils.delAllSpaceForString((String) titleValue);
                else return;
                for (int k = 0; k < data.size(); k++) {
                    List<Object> rowData = data.get(k);
                    if (!CollectionUtils.isEmpty(rowData)) {
                        Object title = rowData.get(0);
                        if (title != null && title.equals(titleValue)) {
                            rowData.add(ExcelUtils.getCellValue(row.getCell(inColsTwo.get(j))));
                        }
                    }
                }
            }
        }


        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardPath));
        Sheet sheetWrite = standardWorkbook.getSheet("5-01");

        int writeBeginRow = 4;


        //写入前清除数据
        for (int i = writeBeginRow; i < sheetWrite.getPhysicalNumberOfRows(); i++) {
            Row row = sheetWrite.getRow(i);
            if (row == null) continue;
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
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
                    for (int j = 1; j < 6; j++) {
                        Cell cell = row.getCell(j);
                        if (rowData.get(j) instanceof java.lang.Double) cell.setCellValue((double) rowData.get(j));
                        if (rowData.get(j) instanceof java.lang.String) cell.setCellValue((String) rowData.get(j));
                    }
                }
            }
        }
        ExcelUtils.write2Excel(standardWorkbook, standardPath);
        System.out.println(excelPath + "--处理完毕");
    }


    @Test
    public void test() {
        todo("D:\\长沙项目\\信息化业\\430103信息化经普公报\\5-01.xls", "D:\\长沙项目\\信息化业\\430103信息化经普公报\\922-5.xls");
    }

}
