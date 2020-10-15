package com.gongjun.changsha.excelDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-07",对应关系:1-07VS1-09A,1-10A（开放平台）
 * @Author: GongJun
 * @Date: Created in 10:35 2020/10/13
 */
public class Excel_1_07 {
    /**
     * @修改点******************************************************************
     */
    private static String standardExcelPath = "D:\\长沙项目\\表格批量处理\\标准表格\\922-1.xlsx";

    //待处理表格的保存文件夹路径
    /**
     * @修改点******************************************************************
     */
    private static String todoExcelFilePath = "D:\\长沙项目\\表格批量处理\\平台导出表格";

    public static void todo() {
        //按地区分组


        String excelPathOne = receiveExcelPath("(1-09A)");
        String excelPathTwo = receiveExcelPath("(1-10A)");
        //获取两张表地区
        List<String> zonesOne = readZones(excelPathOne,9);
        List<String> zonesTwo = readZones(excelPathTwo,9);
        if(!zonesOne.containsAll(zonesTwo)&&zonesTwo.containsAll(zonesOne)) return;
        List<String> zones = zonesOne;

        List<String> deptsOne = readDepts(excelPathOne,3,Arrays.asList(0,1,2));
        List<String> deptsTwo = readDepts(excelPathOne,3,Arrays.asList(0,1,2));
        System.out.println(deptsOne);
        System.out.println(deptsTwo);
        //按登记类型分组
//        dataReadDos("(1-09A)", Arrays.asList(0,1,2,3,4,5,6,7,8),Arrays.asList(1),0);
//        dataReadDos("(1-10A)", Arrays.asList(0,1,2,3,4,5,6),Arrays.asList(1),1);

//        dataWriteDos(dataSheetDatas);

    }


    public static List<String> readZones(String dataExcelPath,int dataBeginRow){
        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);
        List<String> zones = new ArrayList<>();
        for (int i = dataBeginRow; i < dataSheet.getPhysicalNumberOfRows(); i++) {

            Row row = dataSheet.getRow(i);
            Cell cell = row.getCell(0);
            Object value = ExcelUtils.getCellValue(cell);
            if(value instanceof java.lang.String&&StringUtils.isNotBlank(String.valueOf(value))){
                zones.add(((String) value).trim().replaceAll(" ",""));
            }

        }
        return zones;
    }

    public static List<String> readDepts(String dataExcelPath,int dataRow,List exceptCols){
        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);
        List<String> depts = new ArrayList<>();
        Row row = dataSheet.getRow(dataRow);
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            if(exceptCols.contains(i)) continue;
            Cell cell = row.getCell(i);
            String value = cell.getStringCellValue();
            depts.add(value);
        }
        return depts;
    }


    public static String receiveExcelPath(String keywords){
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
                if (fileName != null && StringUtils.startsWithIgnoreCase(fileName, keywords) && StringUtils.endsWithIgnoreCase(fileName, ".xlsx"))
                    accordExcels.add(f.getAbsolutePath());
            }
        }
        switch (accordExcels.size()) {
            case 0:
                System.out.println("没有符合条件的表格请重新检查！");
                break;
            case 1:
            {
                return accordExcels.get(0);
            }
            default:
                System.out.println("有重复表格请重新检查！");
                break;
        }

        return "";
    }




    /**
     * @param: [dataExcelPath, exceptRows, exceptCols]
     * @description: 读取表格数据的方法
     * @author: GongJun
     * @time: Created in 9:13 2020/10/15
     * @modified:
     * @return: java.util.List<java.util.List<java.lang.Object>>
     **/

    public static List<List<Object>> dataReadDos(String dataExcelPath,List exceptRows,List exceptCols,int mark) {


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
                if(j == 0){
                    if(mark == 0){
                        if(count == 0) dataValue = "总  计";
                        else dataValue = String.valueOf(dataValue) == null?"":"  "+String.valueOf(dataValue).trim();
                    }
                    if(mark == 1){
                        dataValue = String.valueOf(dataValue);
                        if(StringUtils.isNotBlank((String)dataValue)){
                            if(!((String) dataValue).startsWith(" ")) dataValue = "  "+dataValue;
                            else dataValue = "    "+((String) dataValue).trim();
                        }

                    }
                }


                dataSheetRowDatas.add(dataValue); //保存Cell数据到行
            }
            if (dataSheetRowDatas != null && StringUtils.isNotBlank(String.valueOf(dataSheetRowDatas.get(0))) && Double.valueOf(String.valueOf(dataSheetRowDatas.get(1))) != 0)
                dataSheetDatas.add(dataSheetRowDatas); //保存行数据到表数据中

            count++;
        }
        if(mark == 0)dataSheetDatas.add(1,Arrays.asList("按地区分组",null,null,null,null,null));
        if(mark == 1)dataSheetDatas.add(0,Arrays.asList("按登记注册类型分组",null,null,null,null,null));
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
        Sheet standardSheet = standardWorkbook.getSheet("1-07");

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
        CellStyle titleNoBold = standardSheet.getRow(dataBeginRow+2).getCell(0).getCellStyle();
        CellStyle dataNoBold = standardSheet.getRow(dataBeginRow+2).getCell(1).getCellStyle();
        //写入数据
        //清除数据
        for (int i = dataBeginRow; i < standardSheetRows; i++) {
            Row row = standardSheet.getRow(i);

            for (int j = dataBeginCol; j < row.getPhysicalNumberOfCells(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellValue(0);
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
                if("总计".equals(valueStr.trim())) valueStr="总  计";
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
                title.setCellValue("  "+valueStr.trim());
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
    }

    @Test
    public void test() {
        todo();
    }
}
