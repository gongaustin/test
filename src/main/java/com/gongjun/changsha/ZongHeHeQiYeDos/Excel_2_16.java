package com.gongjun.changsha.ZongHeHeQiYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:处理表格922-2.xls中Sheet:"2-16",对应关系:2-16VS2-20AA,2-21A（开放平台）
 * @Author: GongJun
 * @Date: Created in 10:40 2020/10/13
 */
public class Excel_2_16 {
    /**
     * @修改点******************************************************************
     */
    public static String standardExcelPath = "D:\\长沙项目\\表格批量处理\\标准表格\\922-2.xlsx";

    //待处理表格的保存文件夹路径
    /**
     * @修改点******************************************************************
     */
    public static String todoExcelFilePath = "D:\\长沙项目\\表格批量处理\\平台导出表格";

    public static void todo() {
        //按地区分组
        List<List<Object>> dataSheetDatas = excelDos("(2-20AA)", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(1), 0);
        //按登记类型分组
        dataSheetDatas.addAll(excelDos("(2-21A)", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8), Arrays.asList(1), 1));

        dataWriteDos(dataSheetDatas);

    }


    public static List<List<Object>> excelDos(String keywords, List exceptRows, List exceptCols, int mark) {
        //标准表格的路径
        List<List<Object>> dataSheetDatas = new ArrayList<>();
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
                if (fileName != null && StringUtils.startsWithIgnoreCase(fileName, keywords) && StringUtils.endsWithIgnoreCase(fileName, ".xlsx"))
                    accordExcels.add(f.getAbsolutePath());
            }
        }
        switch (accordExcels.size()) {
            case 0:
                System.out.println("没有符合条件的表格请重新检查！");
                break;
            case 1: {
                dataSheetDatas = dataReadDos(accordExcels.get(0), exceptRows, exceptCols, mark);
            }
            break;
            default:
                System.out.println("有重复表格请重新检查！");
                break;
        }

        return dataSheetDatas;
    }


    /**
     * @param: [dataExcelPath, exceptRows, exceptCols]
     * @description: 读取表格数据的方法
     * @author: GongJun
     * @time: Created in 9:13 2020/10/15
     * @modified:
     * @return: java.util.List<java.util.List < java.lang.Object>>
     **/

    public static List<List<Object>> dataReadDos(String dataExcelPath, List exceptRows, List exceptCols, int mark) {


        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);

        //dataSheet排除的行数(0开始，合并的行算一行)
        /**
         * @修改点******************************************************************
         * */
//        List exceptRows = Arrays.asList(0, 1, 2, 3, 4, 5);
        //dataSheet排除的列数(0开始，合并的列算一列)
        /**
         * @修改点******************************************************************
         * */
//        List exceptCols = Arrays.asList(1);

        //读取dataSheet的数据
        //获取行数
        int dataSheetRows = dataSheet.getPhysicalNumberOfRows();
        List<List<Object>> dataSheetDatas = new ArrayList<>(); //整张表数据
        int count = 0;
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
                if (j == 0) {
                    if (mark == 0) {
                        if (count == 0) dataValue = "法人单位数(个)";
                        else
                            dataValue = String.valueOf(dataValue) == null ? "" : "  " + String.valueOf(dataValue).trim();
                    }
                    if (mark == 1) {
                        if (count == 0) dataValue = "从业人员期末人数(人)";
                        else
                            dataValue = String.valueOf(dataValue) == null ? "" : "  " + String.valueOf(dataValue).trim();
                    }
                }


                dataSheetRowDatas.add(dataValue); //保存Cell数据到行
            }
            if (dataSheetRowDatas != null && StringUtils.isNotBlank(String.valueOf(dataSheetRowDatas.get(0))) && Double.valueOf(String.valueOf(dataSheetRowDatas.get(1))) != 0)
                dataSheetDatas.add(dataSheetRowDatas); //保存行数据到表数据中

            count++;
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
        Sheet standardSheet = standardWorkbook.getSheet("2-16");

        //获取标准表格的行数
        int standardSheetRows = standardSheet.getPhysicalNumberOfRows();
        //获取标准表格的宾栏
        /**
         * @修改点******************************************************************
         * */
        int dataBeginRow = 4;  //从0开始算
        int dataBeginCol = 1;  //从0开始算


        //原有数据行数
        int originDataRows = standardSheetRows - dataBeginRow;
        int writeDataRows = dataSheetDatas.size();


        /**
         * 样式复制，获取主栏的样式(必须设置总计数据行字体加粗)
         * @必须设置总计数据行字体加粗，否则无法加粗字体
         */
        /**
         * @加粗样式
         * */
        CellStyle titleBold = standardSheet.getRow(dataBeginRow).getCell(0).getCellStyle();
        CellStyle dataBold = standardSheet.getRow(dataBeginRow).getCell(1).getCellStyle();
        /**
         * @不加粗样式
         * */
        CellStyle titleNoBold = standardSheet.getRow(dataBeginRow + 2).getCell(0).getCellStyle();
        CellStyle dataNoBold = standardSheet.getRow(dataBeginRow + 2).getCell(1).getCellStyle();
        //写入数据
        //清除数据
        for (int i = dataBeginRow; i < standardSheetRows; i++) {
            Row row = standardSheet.getRow(i);

            for (int j = dataBeginCol; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellValue((String) null);
            }
        }
        if (originDataRows > writeDataRows) {
            for (int i = writeDataRows + dataBeginRow; i < standardSheetRows; i++) {
                Row row = standardSheet.getRow(i);
                standardSheet.removeRow(row);
            }
        }
        for (int i = 0; i < writeDataRows; i++) {
            List<Object> data = dataSheetDatas.get(i);
            Short hightdex = standardSheet.getRow(0).getHeight();
            //遍历数据
            Row row = standardSheet.getRow(i + dataBeginRow) == null ? standardSheet.createRow(i + dataBeginRow) : standardSheet.getRow(i + dataBeginRow);
            row.setHeight(hightdex);
            Cell title = row.getCell(0) == null ? row.createCell(0) : row.getCell(0);
            String valueStr = String.valueOf(data.get(0));
            if (!valueStr.startsWith(" ")) {
                title.setCellStyle(titleBold);
                //单独处理总计
                if ("总计".equals(valueStr.trim())) valueStr = "总  计";
                title.setCellValue(valueStr);
                for (int j = 1; j < data.size(); j++) {
                    Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellStyle(dataBold);
                    Object value = data.get(j);
                    if (value instanceof java.lang.String) cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            } else {
                title.setCellStyle(titleNoBold);
                title.setCellValue(valueStr);
                for (int j = 1; j < data.size(); j++) {
                    Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellStyle(dataNoBold);
                    Object value = data.get(j);
                    if (value instanceof java.lang.String) cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            }
        }


        //写入表格
        ExcelUtils.write2Excel(standardWorkbook, standardExcelPath);
        System.out.println("**********表格Excel_2_16处理完毕**********");
    }

    @Test
    public void test() {
        todo();
    }
}
