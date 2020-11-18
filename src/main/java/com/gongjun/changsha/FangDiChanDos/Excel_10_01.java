package com.gongjun.changsha.FangDiChanDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
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
 * @Date: Created in 16:45 2020/11/2
 */
@Slf4j
public class Excel_10_01 {
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
        Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
        if (sourceSheet == null) return data;

        List<Integer> sourceExceptRows = Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8);

        List<Integer> sourceCols = Arrays.asList(0, 2, 3);
        int neiziMark = 0;
        for (int i = 0; i < sourceSheet.getPhysicalNumberOfRows(); i++) {
            if (sourceExceptRows.contains(i)) continue;
            Row row = sourceSheet.getRow(i);
            if (row == null) continue;
            List<Object> rowData = new ArrayList<>();
            for (int j = 0; j < sourceCols.size(); j++) {
                String index = null;
                if (j == 0) {
                    Cell cell = row.getCell(sourceCols.get(j));
                    if (cell == null) continue;
                    index = cell.getStringCellValue();
                    if(StringUtils.isBlank(index)) continue;
                    if("总计".equals(RegUtils.delAllSpaceForString(index))) index = "总  计";
                    if("内资".equals(RegUtils.delAllSpaceForString(index))) neiziMark = i - 7;
                    if(index.startsWith(" ")) index = "  "+RegUtils.delAllSpaceForString(index);
                    rowData.add(index);
                }
                if (j > 0) {
                    Cell cell = row.getCell(sourceCols.get(j));
                    if (cell == null) continue;
                    Object value = ExcelUtils.getCellValue(cell);
                    rowData.add(value);
                }
            }
            if(!CollectionUtils.isEmpty(rowData) && rowData.size()==3)
                data.add(rowData);

        }
        data.add(1,Arrays.asList("按地区分组",null,null));
        if(neiziMark != 0) data.add(neiziMark,Arrays.asList("按登记注册类型分组",null,null));
        return data;
    }

    public static void writeData(String targetExcelPath, List<List<Object>> datas) {
        //获取标准表格的Workbook
        Workbook targetWorkbook = ExcelUtils.getWorkbookFromExcel(new File(targetExcelPath));
        if (targetWorkbook == null) return;
        //获取标准表格的Sheet
        /**
         * @修改点******************************************************************
         * */
        Sheet targetSheet = targetWorkbook.getSheet("10-01");

        //获取标准表格的行数
        int targetSheetRows = targetSheet.getPhysicalNumberOfRows();
        //获取标准表格的宾栏
        /**
         * @修改点******************************************************************
         * */
        int dataBeginRow = 2;  //从0开始算


        //原有数据行数
        int originDataRows = targetSheetRows - dataBeginRow;
        int writeDataRows = datas.size();


        /**
         * 样式复制，获取主栏的样式(必须设置总计数据行字体加粗)
         * @必须设置总计数据行字体加粗，否则无法加粗字体
         */
        /**
         * @加粗样式
         * */
        CellStyle titleBold = targetSheet.getRow(dataBeginRow).getCell(0).getCellStyle();
        CellStyle dataBold = targetSheet.getRow(dataBeginRow).getCell(1).getCellStyle();
        /**
         * @不加粗样式
         * */
        CellStyle titleNoBold = targetSheet.getRow(dataBeginRow + 2).getCell(0).getCellStyle();
        CellStyle dataNoBold = targetSheet.getRow(dataBeginRow + 2).getCell(1).getCellStyle();
        //写入数据
        //清除数据
        for (int i = dataBeginRow; i < targetSheetRows; i++) {
            Row row = targetSheet.getRow(i);
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellValue((String) null);
            }
        }
        if (originDataRows > writeDataRows) {
            for (int i = writeDataRows + dataBeginRow; i < targetSheetRows; i++) {
                Row row = targetSheet.getRow(i);
                targetSheet.removeRow(row);
            }
        }
        for (int i = 0; i < writeDataRows; i++) {
            List<Object> rowData = datas.get(i);
            Short hightdex = targetSheet.getRow(0).getHeight();
            //遍历数据
            Row row = targetSheet.getRow(i + dataBeginRow) == null ? targetSheet.createRow(i + dataBeginRow) : targetSheet.getRow(i + dataBeginRow);
            row.setHeight(hightdex);
            Cell title = row.getCell(0) == null ? row.createCell(0) : row.getCell(0);
            String valueStr = String.valueOf(rowData.get(0));
            if (StringUtils.equalsAny(RegUtils.delAllSpaceForString(valueStr),"总计","按地区分组","按登记注册类型分组")) {
                title.setCellStyle(titleBold);
                //单独处理总计
                title.setCellValue(valueStr);
                for (int j = 1; j < rowData.size(); j++) {
                    Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellStyle(dataBold);
                    Object value = rowData.get(j);
                    if (value instanceof java.lang.String) cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            } else {
                title.setCellStyle(titleNoBold);
                title.setCellValue(valueStr);
                for (int j = 1; j < rowData.size(); j++) {
                    Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellStyle(dataNoBold);
                    Object value = rowData.get(j);
                    if (value instanceof java.lang.String) cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            }
        }


        //写入表格
        ExcelUtils.write2Excel(targetWorkbook, targetExcelPath);

    }

    @Test
    public void test(){
        todo("D:\\长沙项目\\房地产\\地区\\1-2\\芙蓉区\\(fdc-4301-1)按地区、登记注册类型全部房地产开发企业数个数、从业人员人数2020-11-16.xlsx",
                "D:\\长沙项目\\房地产\\已处理\\芙蓉区\\922-10.XLS");
    }
}
