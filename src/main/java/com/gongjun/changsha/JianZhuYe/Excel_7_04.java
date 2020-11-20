package com.gongjun.changsha.JianZhuYe;

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
 * @Date: Created in 10:41 2020/11/19
 */
@Slf4j
public class Excel_7_04 {
    //排除行
    private static List<Integer> exceptRowsList = Arrays.asList(
            0,1,2,3
    );
    //包含列
    private static List<Integer> containColsList = Arrays.asList(
        1,26,5,7,8,13,18,19,21,27,28,29,30,31,32,33,34,35,36,37,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,63,62
   //   1 2  3 4 5 6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45
    );
    //写入表格名称
    private static String writeSheetName = "7-04";
    private static String YIJI = "一级";
    private static String ERJI = "二级";
    private static String SANJI = "三级及以下";

    public static void todo(String sourceExcelPath,String targetExcelPath){
        List<List<Object>> data = readData(sourceExcelPath,0);
        writeData(targetExcelPath,data);
        log.info("表格:[{}]处理完成",sourceExcelPath);

    }

    private static List<List<Object>> readData(String sourceExcelPath,int sheetIndex){
        List<List<Object>> data = new ArrayList<>();
        if(StringUtils.isBlank(sourceExcelPath)) return data;
        //获取Workbook
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(sourceExcelPath));
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int sheetRowNumber = sheet.getPhysicalNumberOfRows();
        int YIJI_count = 0;
        int ERJI_count = 0;
        int SANJI_count = 0;
        for (int i = 0; i < sheetRowNumber; i++) {
            if(exceptRowsList.contains(i)) continue;
            Row row = sheet.getRow(i);
            if(row == null) continue;
            List<Object> rowData = new ArrayList<>(); //行数据
            for (int j = 0; j < containColsList.size(); j++) {
                String index = null; //指标
                int col = containColsList.get(j);
                if (j == 0) {
                    Cell cell = row.getCell(col);
                    if (cell == null) continue;
                    index = cell.getStringCellValue();
                    if(StringUtils.isBlank(index)) continue;
                    index = RegUtils.delAllSpaceForString(index);
                    if("国有控股".equals(index)) index = "国有控股企业";
                    if(YIJI.equals(index)){
                        YIJI_count++;
                        if(YIJI_count == 1) index = YIJI+"ONE";
                        if(YIJI_count == 2) index = YIJI+"TWO";
                    }
                    if(ERJI.equals(index)){
                        ERJI_count++;
                        if(ERJI_count == 1) index = ERJI+"ONE";
                        if(ERJI_count == 2) index = ERJI+"TWO";
                    }
                    if(SANJI.equals(index)) {
                        SANJI_count++;
                        if(SANJI_count == 1) index = SANJI+"ONE";
                        if(SANJI_count == 2) index = SANJI+"TWO";
                    }
                    rowData.add(index);
                }
                if (j > 0) {
                    Cell cell = row.getCell(col);
                    if (cell == null) continue;
                    Object value = ExcelUtils.getCellValue(cell);
                    double valueDouble = 0d;
                    if(value instanceof String && StringUtils.isBlank((String)value)) valueDouble = 0d;
                    if(value instanceof String && StringUtils.isNotBlank((String)value)) valueDouble = Double.valueOf((String)value);
                    if(value instanceof Double) valueDouble = (double)value;
                    rowData.add(valueDouble);
                }
            }
            if(!CollectionUtils.isEmpty(rowData) && rowData.size()==containColsList.size()) data.add(rowData);
        }
        return data;
    }


    private static void writeData(String targetExcelPath,List<List<Object>> data){
        Workbook targetWorkbook = ExcelUtils.getWorkbookFromExcel(new File(targetExcelPath));
        Sheet targetSheet = targetWorkbook.getSheet(writeSheetName);
        int sheetNumber = targetSheet.getPhysicalNumberOfRows();
        int YIJI_count = 0;
        int ERJI_count = 0;
        int SANJI_count = 0;
        for (int i = 0; i < sheetNumber; i++) {
            Row row = targetSheet.getRow(i);
            if(row == null) continue;
            Cell indexCell = row.getCell(0);
            if(indexCell == null) continue;
            String index = indexCell.getStringCellValue();
            if(StringUtils.isBlank(index)) continue;
            index = RegUtils.delAllSpaceForString(index);
            if(YIJI.equals(index)){
                YIJI_count++;
                if(YIJI_count == 1) index = YIJI+"ONE";
                if(YIJI_count == 2) index = YIJI+"TWO";
            }
            if(ERJI.equals(index)){
                ERJI_count++;
                if(ERJI_count == 1) index = ERJI+"ONE";
                if(ERJI_count == 2) index = ERJI+"TWO";
            }
            if(SANJI.equals(index)) {
                SANJI_count++;
                if(SANJI_count == 1) index = SANJI+"ONE";
                if(SANJI_count == 2) index = SANJI+"TWO";
            }
            for (List<Object> rowData:data){
                String dataIndex = rowData.get(0).toString();
                if(index.equals(dataIndex)){
                    for (int j = 1; j < rowData.size(); j++) {
                        Cell cell = row.getCell(j);
                        cell.setCellValue((double)rowData.get(j));
                    }
                }
            }
        }
        ExcelUtils.write2Excel(targetWorkbook, targetExcelPath);
    }

    @Test
    public void test(){
        todo("D:\\长沙项目\\建筑业\\地区\\芙蓉区\\661+总承包和专业承包建筑业法人单位主要财务状况（864301000000000_县级_县级）_430100000000_20180000_20201118.xls",
                "D:\\长沙项目\\建筑业\\已处理\\芙蓉区\\922-7.XLS");
    }
}
