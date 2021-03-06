package com.gongjun.changsha.ZongHeHeQiYeDos;

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
 * @Description:处理表格922-1.xls中Sheet:"1-18",对应关系:1-18VS1-19A-a（开放平台）
 * @Author: GongJun
 * @Date: Created in 10:37 2020/10/13
 */
public class Excel_1_18 {
    /**
     * @修改点******************************************************************
     */
    public static String standardExcelPath = "D:\\长沙项目\\综合&企业\\地区\\芙蓉区\\922-1.xlsx";

    //待处理表格的保存文件夹路径
    /**
     * @修改点******************************************************************
     */
    public static String todoExcelFilePath = "D:\\长沙项目\\综合&企业待处理\\地区\\芙蓉区";


    public static void todo() {
        //按地区分组
        List<List<Object>> dataSheetDatas = excelDos("(1-19A-a)", Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8), Arrays.asList(1), 0);
        //按登记类型分组
        dataSheetDatas.addAll(excelDos("(1-19A-aa)", Arrays.asList(0, 1, 2, 3, 4, 5), Arrays.asList(1), 1));

        List<List<Object>> dataThree = excelDos("(1-19A-aaa)", Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8), Arrays.asList(1), 3);

        for (int i = 0; i < dataSheetDatas.size(); i++) {
            List<Object> row = dataSheetDatas.get(i);
            if(CollectionUtils.isEmpty(row)) continue;
            Object indexObj = row.get(0);
            if(indexObj == null) continue;
            String index = RegUtils.delAllSpaceForString((String)indexObj);
            for (int j = 0; j < dataThree.size(); j++) {
                List<Object> sonRow = dataThree.get(j);
                if(CollectionUtils.isEmpty(sonRow)||sonRow.size()<2) continue;
                Object indexSonObj = sonRow.get(0);
                if(indexSonObj == null) continue;
                String sonIndex = RegUtils.delAllSpaceForString((String)indexSonObj);
                if(sonIndex.equals(index)) row.add(1,sonRow.get(1));
            }
        }


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
        int 房地产count = 0;
        int 教育count = 0;
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
                    if (mark == 0 || mark == 3) {
                        if (count == 0) dataValue = "总  计";
                        else
                            dataValue = String.valueOf(dataValue) == null ? "" : "  " + String.valueOf(dataValue).trim();
                    }
                    if (mark == 1) {
                        dataValue = String.valueOf(dataValue);
                        if (StringUtils.isNotBlank((String) dataValue)) {
                            if (!((String) dataValue).startsWith(" ")) dataValue = "  " + dataValue;
                            else dataValue = "    " + ((String) dataValue).trim();
                        }
                    }
                }
                dataSheetRowDatas.add(dataValue);
            }
            if(!CollectionUtils.isEmpty(dataSheetRowDatas)){
                if(mark == 0) {
                    String index = (String)dataSheetRowDatas.get(0);
                    if(StringUtils.isNotBlank(index) ) dataSheetDatas.add(dataSheetRowDatas);
                }
                if(mark == 1) {
                    String index = (String)dataSheetRowDatas.get(0);
                    if(StringUtils.isNotBlank(index) && !StringUtils.equalsAny(index,"    教育","    房地产业")){
                        List<String> indexs = getIndexs();
                        index = RegUtils.delAllSpaceForString(index);
                        if(indexs.contains(index)) dataSheetDatas.add(dataSheetRowDatas);
                    }
                }
                if(mark == 3) {
                    String index = (String)dataSheetRowDatas.get(0);
                    if(StringUtils.isNotBlank(index)){
                        if(index.contains("房地产业")) 房地产count++;
                        if(index.contains("教育")) 教育count++;
                        if(!StringUtils.containsAny(index,"房地产业","教育")) dataSheetDatas.add(dataSheetRowDatas);
                        if(index.contains("房地产业")&&房地产count==1) dataSheetDatas.add(dataSheetRowDatas);
                        if(index.contains("教育")&&教育count==1) dataSheetDatas.add(dataSheetRowDatas);
                    }
                }

            }
            //保存行数据到表数据中
            count++;
        }
        if (mark == 0) dataSheetDatas.add(1, Arrays.asList("按地区分组", null, null, null, null, null,null,null,null));
        if (mark == 1) dataSheetDatas.add(0, Arrays.asList("按行业(门类)分组", null, null, null, null, null,null,null,null));
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
        Sheet standardSheet = standardWorkbook.getSheet("1-18");

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
                    if (value instanceof java.lang.String) try {
                        cell.setCellValue(Double.valueOf(String.valueOf(value)));
                    } catch (NumberFormatException e) {
                        cell.setCellValue((String)null);
                    }
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                }
            }
        }


        //写入表格
        ExcelUtils.write2Excel(standardWorkbook, standardExcelPath);
        System.out.println("**********表格Excel_1_18处理完毕**********");
    }

    @Test
    public void test() {
        todo();
//        getIndexs().forEach(e->{
//            System.out.println(e);
//        });
    }


    public static List<String> getIndexs(){
        String indexString = "农林牧渔业\n" + "采矿业\n" + "制造业\n" + "电力热力燃气及水生产和供应业\n" + "建筑业\n" + "批发和零售业\n" + "批发业\n" + "零售业\n" + "交通运输仓储和邮政业\n" + "住宿和餐饮业\n" + "住宿业\n" + "餐饮业\n" + "信息传输软件和信息技术服务业\n" + "金融业\n" + "房地产业\n" + "租赁和商务服务业\n" + "科学研究和技术服务业\n" + "水利环境和公共设施管理业\n" + "居民服务修理和其他服务业\n" + "教育\n" + "卫生和社会工作\n" + "文化体育和娱乐业";
        List<String> indexs = Arrays.asList(indexString.split("\n"));
        return indexs;
    }
}