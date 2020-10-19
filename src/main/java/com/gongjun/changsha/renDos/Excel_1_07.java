package com.gongjun.changsha.renDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-07",对应关系:1-07VS1-09A,1-10A（开放平台） 主栏与宾栏交换处理
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
        List<String> zonesOne = readZones(excelPathOne, 9);
        List<String> zonesTwo = readZones(excelPathTwo, 9);
        if (!zonesOne.containsAll(zonesTwo) && zonesTwo.containsAll(zonesOne)) return;
        List<String> zones = zonesOne;
        //读取部门
        List<String> deptsOne = readDepts(excelPathOne, 3, Arrays.asList(0, 1, 2));
        List<String> deptsTwo = readDepts(excelPathOne, 3, Arrays.asList(0, 1, 2));

        //读取数据
        List<List<Object>> dataOne = dataReadDos(excelPathOne, Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8), deptsOne.size(), 2, 0);
        List<List<Object>> dataTwo = dataReadDos(excelPathTwo, Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8), deptsTwo.size(), 2, 1);

        dataOne.addAll(dataTwo);
        dataWriteDos(zones, dataOne);

    }

    /**
     * @param: [dataExcelPath, dataBeginRow]
     * @description: 地区读取
     * @author: GongJun
     * @time: Created in 13:40 2020/10/15
     * @modified:
     * @return: java.util.List<java.lang.String>
     **/
    public static List<String> readZones(String dataExcelPath, int dataBeginRow) {
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
            if (value instanceof java.lang.String && StringUtils.isNotBlank(String.valueOf(value))) {
                zones.add(((String) value).trim().replaceAll(" ", ""));
            }

        }
        return zones;
    }

    /**
     * @param: [dataExcelPath, dataRow, exceptCols]
     * @description: 部门读取
     * @author: GongJun
     * @time: Created in 13:40 2020/10/15
     * @modified:
     * @return: java.util.List<java.lang.String>
     **/
    public static List<String> readDepts(String dataExcelPath, int dataRow, List exceptCols) {
        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);
        List<String> depts = new ArrayList<>();
        Row row = dataSheet.getRow(dataRow);
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            if (exceptCols.contains(i)) continue;
            Cell cell = row.getCell(i);
            String value = cell.getStringCellValue();
            depts.add(value);
        }
        return depts;
    }

    /**
     * @param: [keywords]
     * @description: 查找表格，返回表格的绝对路径
     * @author: GongJun
     * @time: Created in 13:41 2020/10/15
     * @modified:
     * @return: java.lang.String
     **/
    public static String receiveExcelPath(String keywords) {
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
            case 1: {
                return accordExcels.get(0);
            }
            default:
                System.out.println("有重复表格请重新检查！");
                break;
        }

        return "";
    }


    /**
     * @param: [dataExcelPath, exceptRows, exceptCols]  此时mark为读取列数
     * @description: 读取表格数据的方法
     * @author: GongJun
     * @time: Created in 9:13 2020/10/15
     * @modified:
     * @return: java.util.List<java.util.List < java.lang.Object>>
     **/

    public static List<List<Object>> dataReadDos(String dataExcelPath, List exceptRows, int cols, int dataBeginCol, int mark) {


        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if (dataWorkbook == null) return null;

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);
        List<List<Object>> dataSheetData = new ArrayList<>();
        int countOuter = 0;
        for (int i = dataBeginCol; i <= dataBeginCol + cols; i++) {
            //行遍历
            List<Object> rowData = new ArrayList<>();
            int countInner = 0;
            for (int j = 0; j < dataSheet.getPhysicalNumberOfRows(); j++) {
                if (exceptRows.contains(j)) continue;
                Row row = dataSheet.getRow(j);
                //列遍历
                Cell cell = row.getCell(i);
                Object value = ExcelUtils.getCellValue(cell);
                if (countOuter > 0 && countInner == 0) value = "  " + value;
                if (value != null && StringUtils.isNotBlank(value.toString())) rowData.add(value);
                countInner++;
            }
            dataSheetData.add(rowData);
            countOuter++;
        }
        return dataSheetData;
    }

    /**
     * @param: bings, [dataSheetDatas]
     * @description: 数据写入
     * @author: GongJun
     * @time: Created in 13:41 2020/10/15
     * @modified:
     * @return: void
     **/
    public static void dataWriteDos(List<String> bings, List<List<Object>> dataSheetDatas) {
        //获取标准表格的Workbook
        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        if (standardWorkbook == null) return;
        //获取标准表格的Sheet
        /**
         * @修改点******************************************************************
         * */
        Sheet standardSheet = standardWorkbook.getSheet("1-07");

        int standardSheetRows = standardSheet.getPhysicalNumberOfRows();

        //重写宾栏

        //移除合并格
        while (standardSheet.getNumMergedRegions() > 0) {
            standardSheet.removeMergedRegion(0);
        }

        int bingRow = 3;

        int bingBeginCol = 1;

        //获取宾栏所在行
        Row rowBing = standardSheet.getRow(bingRow);

        //单独处理宾栏合并格
        Cell firstBingCell = standardSheet.getRow(bingRow - 1).getCell(bingBeginCol);
        firstBingCell.setCellValue(bings.get(0));
        //遍历宾栏
        CellStyle bingStyle = rowBing.getCell(bingBeginCol + 1).getCellStyle();

        for (int i = 1; i < bings.size(); i++) {
            Cell cell = rowBing.getCell(i + bingBeginCol) == null ? rowBing.createCell(i + 1) : rowBing.getCell(i + bingBeginCol);
            cell.setCellStyle(bingStyle);
            cell.setCellValue(bings.get(i));
        }
        int writeDataRows = dataSheetDatas.size();
        int dataBeginRow = bingRow + 1;
        int originDataRows = standardSheetRows - dataBeginRow;

        //遍历数据

        /**
         * @加粗样式
         * */
        CellStyle titleBold = standardSheet.getRow(bingRow + 1).getCell(0).getCellStyle();
        CellStyle dataBold = standardSheet.getRow(bingRow + 1).getCell(1).getCellStyle();
        /**
         * @不加粗样式
         * */
        CellStyle titleNoBold = standardSheet.getRow(bingRow + 2).getCell(0).getCellStyle();
        CellStyle dataNoBold = standardSheet.getRow(bingRow + 2).getCell(1).getCellStyle();
        //写入数据

        //清除数据
        for (int i = dataBeginRow; i < standardSheetRows; i++) {
            Row row = standardSheet.getRow(i);

            for (int j = bingBeginCol; j < row.getPhysicalNumberOfCells(); j++) {
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
            //遍历数据
            Row row = standardSheet.getRow(i + dataBeginRow) == null ? standardSheet.createRow(i + dataBeginRow) : standardSheet.getRow(i + dataBeginRow);

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
                title.setCellValue("  " + valueStr.trim());
                for (int j = 1; j < data.size(); j++) {
                    Cell cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellStyle(dataNoBold);
                    Object value = data.get(j);
                    if (value instanceof java.lang.String) cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            }
        }

        //重新设置合并表格
        standardSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowBing.getPhysicalNumberOfCells() - 1));
        standardSheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        standardSheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        standardSheet.addMergedRegion(new CellRangeAddress(2, 2, 2, rowBing.getPhysicalNumberOfCells() - 1));

        ExcelUtils.write2Excel(standardWorkbook, standardExcelPath);
        System.out.println("**********表格Excel_1_07处理完毕**********");
    }

    @Test
    public void test() {
        todo();
    }
}
