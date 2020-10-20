package com.gongjun.changsha;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 从多个sheet中获取数据写入另一个Sheet中
 * @Author: GongJun
 * @Date: Created in 10:35 2020/9/29
 */
public class excelDataBinding {
    /**
     * @param: []
     * @description: 核定数据的方法
     * @author: GongJun
     * @time: Created in 10:37 2020/9/29
     * @modified:
     * @return: void
     **/
    public void todo() throws Exception {

        String excelPath = "D:\\长沙项目\\测试数据\\测试表格.xlsx";

        //读取表格
        Workbook wb = ExcelUtils.getWorkbookFromExcel(new File(excelPath));
        //获取Sheet的个数
        int numOfSheet = wb.getNumberOfSheets();
        System.out.println(numOfSheet);

        //获取最后一张表的Sheet
        Sheet sl = wb.getSheetAt(numOfSheet - 1);

        List<CellRangeAddress> cras = sl.getMergedRegions();

        Iterator<Row> it = sl.iterator();
        while (it.hasNext()) {
            Row rw = it.next();
            Iterator<Cell> cells = rw.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                Object value = "";
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        value = cell.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        value = cell.getBooleanCellValue();
                        break;
                    case FORMULA: {
                        //判断cell是否为日期格式
                        if (DateUtil.isCellDateFormatted(cell)) {
                            //转换为日期格式YYYY-mm-dd
                            value = cell.getDateCellValue();
                        } else {
                            //数字
                            value = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    }
                    default:
                        break;
                }

                System.out.println(value);

            }
        }

//        cras.stream().forEach(e->{
//            System.out.println(e.formatAsString());
//
//        });


    }

    /**
     * @param: []
     * @description: 读取前几个表格的数据，并写入新的表格中
     * @author: GongJun
     * @time: Created in 15:16 2020/10/10
     * @modified:
     * @return: void
     **/
    //获取几个表格的数据写入样表
    public void taskOne() throws Exception {

        String excelPath = "D:\\长沙项目\\测试数据\\测试表格.xlsx";
        //获取Workbook
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(excelPath));

        //获取1、F35120_2018年的表格

        Sheet sheetOne = workbook.getSheet("1、F35120_2018年");
        //获取1、F35120_2018年的表格
        Sheet sheetTwo = workbook.getSheet("1、F35020_2018年");
        //样表，写入数据的表格
        Sheet sheetYangBiao = workbook.getSheet("样表");
        //设置字体样式
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        //设置样式垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //获取样表的行
        int rowYangBiao = sheetYangBiao.getPhysicalNumberOfRows();

        //获取样表的行业
        LinkedList hangyes = new LinkedList();
        for (int i = 8; i < rowYangBiao; i++) {
            Row row = sheetYangBiao.getRow(i);
            Cell 样表_行业 = row.getCell(0);
            String hangye = 样表_行业.getStringCellValue().trim();
            hangyes.add(hangye);

        }


        //获取F35120_2018年的表格的行数
        int rowOne = sheetOne.getPhysicalNumberOfRows();

        for (int i = 6; i < rowOne; i++) {

            Row row = sheetOne.getRow(i);

            Cell 指标名称 = row.getCell(1);

            Cell 期末使用计算机_总数量 = row.getCell(3);

            Cell 期末使用计算机_百家企业拥有数量 = row.getCell(4);

            String hangye = (String) ExcelUtils.getCellValue(指标名称);
            hangye = hangye.trim();
            Object 期末使用计算机_总数量_value = ExcelUtils.getCellValue(期末使用计算机_总数量);
            Object 期末使用计算机_百家企业拥有数量_value = ExcelUtils.getCellValue(期末使用计算机_百家企业拥有数量);
            if (hangyes.contains(hangye))

                //测试打印数据
                System.out.println("行业" + hangye + "--" + "总数量" + 期末使用计算机_总数量_value + "--" + 期末使用计算机_百家企业拥有数量_value);


            for (int j = 8; j < rowYangBiao; j++) {
                Row rowWrite = sheetYangBiao.getRow(j);
                Cell 样表_行业 = rowWrite.getCell(0);
                String hangyeName = 样表_行业.getStringCellValue().trim();
                if (hangyeName.equals(hangye)) {
                    Cell cellOne = rowWrite.getCell(1);
                    Cell cellTwo = rowWrite.getCell(2);
                    //判断数据类型，根据数据类型写入
//                    switch (期末使用计算机_总数量_value.getClass().getName()){
//                        case "java.lang.String":
//                            System.out.println("String");
//                        case "java.lang.Double":
//                            System.out.println("Double");
//
//                    }


                    cellOne.setCellStyle(cellStyle);
                    cellTwo.setCellStyle(cellStyle);

                    if (期末使用计算机_总数量_value instanceof java.lang.String)
                        cellOne.setCellValue(String.valueOf(期末使用计算机_总数量_value));
                    if (期末使用计算机_总数量_value instanceof java.lang.Double) cellOne.setCellValue((Double) 期末使用计算机_总数量_value);

                    //判断数据类型，根据数据类型写入
                    if (期末使用计算机_百家企业拥有数量_value instanceof java.lang.String)
                        cellTwo.setCellValue(String.valueOf(期末使用计算机_百家企业拥有数量_value));
                    if (期末使用计算机_百家企业拥有数量_value instanceof java.lang.Double)
                        cellTwo.setCellValue((Double) 期末使用计算机_百家企业拥有数量_value);

                }
            }


        }

        //修改的数据写入表格
        FileOutputStream excelFileOutPutStream = new FileOutputStream(excelPath);
        excelFileOutPutStream.flush();
        workbook.write(excelFileOutPutStream);
        workbook.close();


    }


    /**
     * @param: []
     * @description: 通用单元测试方法
     * @author: GongJun
     * @time: Created in 10:43 2020/9/29
     * @modified:
     * @return: void
     **/
    @Test
    public void test() throws Exception {
        taskOne();
    }


}
