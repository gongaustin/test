package com.gongjun.changsha.ZongHeHeQiYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-04",对应关系:1-04VS1-04A（开放平台）
 * @Author: GongJun
 * @Date: Created in 10:34 2020/10/13
 */
public class Excel_1_04 {
    /**
     * @修改点******************************************************************
     */
    public static String standardExcelPath = "D:\\长沙项目\\综合&企业待处理\\已处理\\雨花区\\922-1.xlsx";

    //待处理表格的保存文件夹路径
    /**
     * @修改点******************************************************************
     */
    public static String todoExcelFilePath = "D:\\长沙项目\\综合&企业待处理\\地区\\雨花区";

    public static void todo() {
        //标准表格的路径

        File file = new File(todoExcelFilePath);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        List<String> accordExcels = new ArrayList<>();
        for (File f : fs) {                //遍历File[]数组
            if (!f.isDirectory())        //若非目录(即文件)，则打印
            {
                String fileName = f.getName();
                /**
                 * @修改点******************************************************************
                 * */
                if (fileName != null && StringUtils.startsWithIgnoreCase(fileName, "(1-04A)") && StringUtils.endsWithIgnoreCase(fileName, ".xlsx"))
                    accordExcels.add(f.getAbsolutePath());
            }
        }
        switch (accordExcels.size()) {
            case 0:
                System.out.println("没有符合条件的表格请重新检查！");
                break;
            case 1: {
                List<List<Object>> dataSheetDatas = dataReadDos(accordExcels.get(0));
                if (dataSheetDatas != null) dataWriteDos(dataSheetDatas);
            }
            break;
            default:
                System.out.println("有重复表格请重新检查！");
                break;
        }


        //待处理表格的文件夹

    }


    public static List<List<Object>> dataReadDos(String dataExcelPath) {


        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);

        //dataSheet排除的行数(0开始，合并的行算一行)
        /**
         * @修改点******************************************************************
         * */
        List exceptRows = Arrays.asList(0, 1, 2, 3, 4, 5);
        //dataSheet排除的列数(0开始，合并的列算一列)
        /**
         * @修改点******************************************************************
         * */
        List exceptCols = Arrays.asList(1);

        //读取dataSheet的数据
        //获取行数
        int dataSheetRows = dataSheet.getPhysicalNumberOfRows();
        List<List<Object>> dataSheetDatas = new ArrayList<>(); //整张表数据
        for (int i = 0; i < dataSheetRows; i++) {
            if (exceptRows.contains(i)) continue;
            Row dataSheetRow = dataSheet.getRow(i);
            //获取列数
            int dataSheetCols = dataSheetRow.getPhysicalNumberOfCells();
            List<Object> dataSheetRowDatas = new ArrayList<>(); //行数据
            for (int j = 0; j < dataSheetCols; j++) {
                if (exceptCols.contains(j)) continue;
                //获取表格
                Cell cell = dataSheetRow.getCell(j);
                //获取数据
                Object dataValue = ExcelUtils.getCellValue(cell);
                dataSheetRowDatas.add(dataValue); //保存Cell数据到行
            }
            if (dataSheetRowDatas != null && StringUtils.isNotBlank(String.valueOf(dataSheetRowDatas.get(0))) && Double.valueOf(String.valueOf(dataSheetRowDatas.get(1))) != 0)
                dataSheetDatas.add(dataSheetRowDatas); //保存行数据到表数据中
        }
        return dataSheetDatas;
    }

    public static void dataWriteDos(List<List<Object>> dataSheetDatas) {
        //获取标准表格的Workbook
        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        if (standardWorkbook == null) return;
        //获取标准表格的Sheet
        /**
         * @修改点******************************************************************
         * */
        Sheet standardSheet = standardWorkbook.getSheet("1-04");

        //获取标准表格的行数
        int standardSheetRows = standardSheet.getPhysicalNumberOfRows();
        //清除数据
        for (int i = 4; i < standardSheetRows; i++) {
            Row row = standardSheet.getRow(i);

            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellValue((String) null);
            }
        }
        //获取标准表格的宾栏
        /**
         * @修改点******************************************************************
         * */
        for (List<Object> data : dataSheetDatas) {
            if (data == null) continue;
            //遍历变革栏
            for (int i = 0; i < standardSheetRows; i++) {
                Row row = standardSheet.getRow(i);
                Cell titleCell = row.getCell(0);
                if (titleCell == null) continue;
                String title = titleCell.getStringCellValue();
                String titleTrim = RegUtils.delAllSpaceForString(title.trim());
                String dataTitle = String.valueOf(data.get(0));
                if (StringUtils.isNotBlank(dataTitle)) dataTitle = RegUtils.delAllSpaceForString(dataTitle.trim());
                else continue;
                if (StringUtils.isNotBlank(dataTitle) && StringUtils.equals(titleTrim, dataTitle)) {
                    int writeNum = data.size();
                    int rowNum = row.getPhysicalNumberOfCells();
                    //获取样式
                    CellStyle cs = (row.getCell(1) == null ? row.getCell(2) : row.getCell(1)).getCellStyle();
                    if (writeNum >= rowNum) {
                        for (int j = 1; j < writeNum; j++) {
                            Cell cell = row.getCell(j);
                            if (cell == null) cell = row.createCell(j);
                            cell.setCellStyle(cs);
                            Object value = data.get(j);
                            if (value instanceof java.lang.String)
                                cell.setCellValue(Double.valueOf(String.valueOf(value)));
                            if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                        }
                    } else if (writeNum < rowNum) {
                        for (int j = 1; j < writeNum; j++) {
                            Cell cell = row.getCell(j);
                            cell.setCellStyle(cs);
                            Object value = data.get(j);
                            if (value instanceof java.lang.String)
                                cell.setCellValue(Double.valueOf(String.valueOf(value)));
                            if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                        }
                        for (int j = writeNum; j < rowNum; j++) {
                            Cell cell = row.getCell(j);
                            row.removeCell(cell);
                        }
                    }
                }
            }
        }

        //写入表格
        ExcelUtils.write2Excel(standardWorkbook, standardExcelPath);
        System.out.println("**********表格Excel_1_04处理完毕**********");
    }


    @Test
    public void test() {
        todo();
    }
}
