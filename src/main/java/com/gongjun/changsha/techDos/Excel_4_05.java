package com.gongjun.changsha.techDos;

import com.gongjun.changsha.tools.ExcelUtils;
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
 * @Date: Created in 10:47 2020/10/19
 */
public class Excel_4_05 {
    //原始数据表格路径
    public static String dataExcelPath = "D:\\长沙项目\\科技\\原始数据\\430102.xls";
    //标准表格（写入数据）路径
    public static String standardExcelPath = "D:\\长沙项目\\科技\\效果表\\922-4.xlsx";
    //标准表格Sheet关键字
    public static String keyword = "4-05";

    public static List<List<Object>> readDos() {
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        Sheet dataSheet = dataWorkbook.getSheetAt(0);
        int dataSheetRowNum = dataSheet.getPhysicalNumberOfRows();
        List exceptRows = Arrays.asList(0, 1, 2, 3, 4);  //排除的行
        List inCols = Arrays.asList(1, 126, 132, 135, 138, 141);  //包含的列
        List<List<Object>> dataSheetDatas = new ArrayList<>();
        for (int i = 0; i < dataSheetRowNum; i++) {
            if (exceptRows.contains(i)) continue;
            Row row = dataSheet.getRow(i);
            if (row == null) continue;
            int colNum = row.getPhysicalNumberOfCells();
            List<Object> rowData = new ArrayList<>();
            for (int j = 0; j < colNum; j++) {
                if (!inCols.contains(j)) continue;
                Cell cell = row.getCell(j);
                Object value = ExcelUtils.getCellValue(cell);
                if (j == 1) {
                    if (value != null && value instanceof java.lang.String)
                        value = String.valueOf(value).trim().replaceAll(" ", "");
                }
                rowData.add(value);
            }
            if (rowData != null && StringUtils.isNotBlank(String.valueOf(rowData.get(0)))) dataSheetDatas.add(rowData);
        }
        return dataSheetDatas;
    }

    public static void writeDataDos(List<List<Object>> dataSheetDatas) {
        if (dataSheetDatas == null) return;
        Workbook standarWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        Sheet standardSheet = standarWorkbook.getSheet(keyword);

        int zoneBeginRow = 5;
        int zoneEndRow = 14;
        //删除地区
        for (int i = zoneBeginRow; i <= zoneEndRow; i++) {
            Row row = standardSheet.getRow(i);
            standardSheet.removeRow(row);
        }
        standardSheet.shiftRows(zoneEndRow + 1, standardSheet.getLastRowNum(), -(zoneEndRow - zoneBeginRow + 1));

        //清除数据

        for (int i = zoneBeginRow - 1; i < standardSheet.getLastRowNum(); i++) {
            Row row = standardSheet.getRow(i);

            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellValue(0);
            }
        }

        //写入数据
        for (int i = zoneBeginRow - 1; i < standardSheet.getLastRowNum(); i++) {
            Row row = standardSheet.getRow(i);
            if (row == null) continue;
            String title = row.getCell(0).getStringCellValue();
            if (title == null) continue;
            title = title.trim().replaceAll("[　*| *| *|//s*]*", "").replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
            for (List<Object> data : dataSheetDatas) {
                if (title.equals(data.get(0))) {
                    for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                        Cell cell = row.getCell(j);
                        cell.setCellValue((Double) data.get(j));
                    }
                }
            }
        }
        ExcelUtils.write2Excel(standarWorkbook, standardExcelPath);
        System.out.println("**********表格Excel_4_05处理完毕**********");
    }

    @Test
    public static void todo() {
        readDos().forEach(e -> {
            System.out.println(e.toString());
        });
    }
}
