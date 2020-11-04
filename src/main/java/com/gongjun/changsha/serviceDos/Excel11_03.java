package com.gongjun.changsha.serviceDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 13:51 2020/10/23
 */
public class Excel11_03 {
    public static void todo(String sourceExcelPath, String targetExcelPath) {
        //数据源表
        Workbook sourceWorkbook = ExcelUtils.getWorkbookFromExcel(new File(sourceExcelPath));

        Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
        int dataBeginRow = 0;
        for (int i = 0; i < sourceSheet.getPhysicalNumberOfRows(); i++) {
            Row row = sourceSheet.getRow(i);
            if (row == null) continue;
            Cell cell = row.getCell(0);
            if (cell != null) {
                String value = cell.getStringCellValue();
                if (value != null) value = RegUtils.delAllSpaceForString(value);
                if (StringUtils.containsAny(value, "芙蓉区", "开福区", "浏阳市", "宁乡市", "天心区", "望城区", "雨花区", "岳麓区", "长沙县")) {
                    dataBeginRow = i;
                    break;
                }
            }
        }
        if (dataBeginRow == 0) return;
        List<List<Object>> data = new ArrayList<>();
        for (int i = dataBeginRow; i < sourceSheet.getPhysicalNumberOfRows(); i++) {
            Row row = sourceSheet.getRow(i);
            if (row == null) continue;
            List<Object> rowData = new ArrayList<>();
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                Object value = ExcelUtils.getCellValue(cell);
                if (j == 0) {
                    if (value == null) continue;
                    if (value instanceof java.lang.String) {
                        String valueStr = (String) value;
                        if (i == dataBeginRow) value = "总  计";
                        if (StringUtils.isBlank(valueStr)) continue;
                        if (((String) value).startsWith(" ")) {
                            value = "  " + RegUtils.delAllSpaceForString(valueStr);
                        }
                    }
                }
                if (StringUtils.isNotBlank(row.getCell(0).getStringCellValue())) rowData.add(value);
            }

            if (!CollectionUtils.isEmpty(rowData) && rowData.size() != 0) data.add(rowData);
        }

        data.add(1, Arrays.asList("按地区分组", null, null, null, null, null, null));

        int a = 0;
        int b = 0;
        for (int i = 0; i < data.size(); i++) {
            if ("内资企业".equals(data.get(i).get(0))) a = i;
            if ("交通运输、仓储和邮政业".equals(data.get(i).get(0))) b = i + 1;
        }
        if (a != 0) data.add(a, Arrays.asList("按登记注册类型分组", null, null, null, null, null, null));
        if (b != 0) data.add(b, Arrays.asList("按国民经济行业分组", null, null, null, null, null, null));

        //目标表
        Workbook targetWorkbook = ExcelUtils.getWorkbookFromExcel(new File(targetExcelPath));

        Sheet targetSheet = targetWorkbook.getSheet("11-03");
        int targetSheetDataBeginRow = 2;

        /**
         * @加粗样式
         * */
        CellStyle titleBold = targetSheet.getRow(targetSheetDataBeginRow).getCell(0).getCellStyle();
        CellStyle dataBold = targetSheet.getRow(targetSheetDataBeginRow).getCell(1).getCellStyle();
        /**
         * @不加粗样式
         * */
        CellStyle titleNoBold = targetSheet.getRow(targetSheetDataBeginRow + 2).getCell(0).getCellStyle();
        CellStyle dataNoBold = targetSheet.getRow(targetSheetDataBeginRow + 2).getCell(1).getCellStyle();

        //清除数据
        for (int i = targetSheetDataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
            Row row = targetSheet.getRow(i);
            if (row == null) continue;
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                cell.setCellValue((String) null);
            }
        }

        for (int i = targetSheetDataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
            Row row = targetSheet.getRow(i);
            if (row == null) continue;
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                cell.setCellValue((String) null);
            }
        }

        int originDataRows = targetSheet.getPhysicalNumberOfRows() - targetSheetDataBeginRow;
        int writeDataRows = data.size();

        if (originDataRows > writeDataRows) {
            for (int i = writeDataRows + targetSheetDataBeginRow; i < targetSheet.getPhysicalNumberOfRows(); i++) {
                Row row = targetSheet.getRow(i);
                targetSheet.removeRow(row);
            }
        }
        for (int i = 0; i < writeDataRows; i++) {
            List<Object> dataRow = data.get(i);
            //遍历数据
            Row row = targetSheet.getRow(i + targetSheetDataBeginRow) == null ? targetSheet.createRow(i + targetSheetDataBeginRow) : targetSheet.getRow(i + targetSheetDataBeginRow);
            short height = targetSheet.getRow(targetSheetDataBeginRow).getHeight();
            row.setHeight(height);

            for (int j = 0; j < dataRow.size(); j++) {
                Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                if (j == 0) {
                    Object value = dataRow.get(0);
                    if (value != null && value instanceof String && StringUtils.containsAny(RegUtils.delAllSpaceForString((String) value), "总计", "按地区分组", "按登记注册类型分组", "按国民经济行业分组"))
                        row.getCell(0).setCellStyle(titleBold);
                    else row.getCell(0).setCellStyle(titleNoBold);
                }
                if (j > 0) {
                    if (StringUtils.containsAny(RegUtils.delAllSpaceForString((String) dataRow.get(0)), "总计"))
                        row.getCell(j).setCellStyle(dataBold);
                    else row.getCell(j).setCellStyle(dataNoBold);
                }
                if (dataRow.get(j) instanceof String) cell.setCellValue((String) dataRow.get(j));
                if (dataRow.get(j) instanceof Double) cell.setCellValue((double) dataRow.get(j));
            }
        }

        ExcelUtils.write2Excel(targetWorkbook, targetExcelPath);
        System.out.println("文件[" + sourceExcelPath + "]--处理完毕");
    }

    @Test
    public void test() {
        todo("D:\\长沙项目\\服务业\\地区\\430102服务业经普公报\\11-03规模以上服务业企业法人单位主要指标.xlsx", "D:\\长沙项目\\服务业\\地区\\430102服务业经普公报\\922-11.XLS");
    }
}
