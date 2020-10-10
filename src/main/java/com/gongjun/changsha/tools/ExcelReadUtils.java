package com.gongjun.changsha.tools;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * @Description: excel读取工具类
 * @Author: GongJun
 * @Date: Created in 11:45 2020/9/29
 */
public class ExcelReadUtils {

    /**
     * @param filePath 需要读取的文件路径
     * @param column   指定需要获取的列数，例如第一列 1
     * @param startRow 指定从第几行开始读取数据
     * @param endRow   指定结束行
     * @return 返回读取列数据的set
     */
    public static HashSet<String> getColumnSet(String filePath, int column, int startRow, int endRow) {
        Workbook wb = readExcel(filePath); //文件
        Sheet sheet = wb.getSheetAt(0); //sheet
        int rownum = sheet.getPhysicalNumberOfRows(); //行数
        Row row = null;
        HashSet<String> result = new HashSet<>();
        String cellData = null;
        if (wb != null) {
            for (int i = startRow - 1; i < endRow; i++) {
                System.out.println(i);
                row = sheet.getRow(i);
                if (row != null) {
                    cellData = (String) getCellFormatValue(row.getCell(column - 1));
                    result.add(cellData.replaceAll(" ", ""));
                } else {
                    break;
                }
                System.out.println(cellData);
            }
        }
        return result;
    }

    /**
     * @param filePath 需要读取的文件路径
     * @param column   指定需要获取的列数，例如第一列 1
     * @param startRow 指定从第几行开始读取数据
     * @return 返回读取列数据的set
     */
    public static HashSet<String> getColumnSet(String filePath, int column, int startRow) {
        Workbook wb = readExcel(filePath); //文件
        Sheet sheet = wb.getSheetAt(0); //sheet
        int rownum = sheet.getPhysicalNumberOfRows(); //行数
        System.out.println("sumrows " + rownum);

        return getColumnSet(filePath, column, startRow, rownum - 1);
    }



    /**
     * @param sheet Sheet
     * @param column   指定需要获取的列数，例如第一列 1
     * @param startRow 指定从第几行开始读取数据
     * @param endRow   指定结束行
     * @return 返回读取列数据的set
     */
    public static HashSet<String> getSheetColumnSet(Sheet sheet, int column, int startRow, int endRow) {
        HashSet<String> result = new HashSet<>();
        if( null == sheet) return result;
        int rownum = sheet.getPhysicalNumberOfRows(); //行数
        Row row = null;
        String cellData = null;
        for (int i = startRow - 1; i < endRow; i++) {
            System.out.println(i);
            row = sheet.getRow(i);
            if (row != null) {
                cellData = (String) getCellFormatValue(row.getCell(column - 1));
                result.add(cellData.replaceAll(" ", ""));
            } else {
                break;
            }
            System.out.println(cellData);
        }

        return result;
    }

    /**
     * @param sheet Sheet
     * @param column   指定需要获取的列数，例如第一列 1
     * @param startRow 指定从第几行开始读取数据
     * @return 返回读取列数据的set
     */
    public static HashSet<String> getSheetColumnSet(Sheet sheet, int column, int startRow) {
        int rownum = sheet.getPhysicalNumberOfRows(); //行数
        System.out.println("sumrows " + rownum);
        return getSheetColumnSet(sheet, column, startRow, rownum - 1);
    }



    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: {
                    cell.setCellType(CellType.STRING);  //将数值型cell设置为string型
                    cellValue = cell.getStringCellValue();
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }


    @Test
    public void testReadExcel() {
        HashSet<String> columnSet = ExcelReadUtils.getColumnSet("src/main/resources/pboc/Excelfile_V2.xlsx", 1, 90);  //读取第一列的从第90行开始往后的数据 到set
        System.out.println(columnSet.size());
        System.out.println(columnSet.toString());

        HashSet<String> excelParamsSet = ExcelReadUtils.getColumnSet("CommonConstant.excelJingDongXinYongPath", 8, 2, 26); //读取第8列的第二行到第26行 数据 到set

    }
}