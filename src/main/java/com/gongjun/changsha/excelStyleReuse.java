package com.gongjun.changsha;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: Excel样式处理-数据拷贝&样式复用
 * @Author: GongJun
 * @Date: Created in 16:03 2020/10/10
 */
public class excelStyleReuse {

    public void styleUse() throws Exception {

        //样式表
        String modelPath = "D:\\长沙项目\\测试数据\\复用样式\\922-1.xlsx";

        //待处理表
        String taskPath = "D:\\长沙项目\\测试数据\\复用样式\\(1-07A-cs)按行业门类、地区分组的法人单位数(A)2020-10-10.xlsx";

        Workbook workbookModel = ExcelUtils.getWorkbookFromExcel(new File(modelPath));

        Workbook workbookTask = ExcelUtils.getWorkbookFromExcel(new File(taskPath));

        //设置标椎表格的样式
        CellStyle cellhangStyle = null;
        CellStyle cellDataStyle = null;


        //获取任务表的Sheet
        Sheet sheetTask = workbookTask.getSheetAt(0);
        //获取标椎表的Sheet
        Sheet sheetModel = workbookModel.getSheet("1-01");


        int rowTaskNum = sheetTask.getPhysicalNumberOfRows();


        //获取标准表格的行名
        int rowModelNum = sheetModel.getPhysicalNumberOfRows();

        List hangModelName = new ArrayList();
        List hangModelNameTrim = new ArrayList();

        //标准表格的起始行
        int rowBeginModelNum = 4;
        //获取标准表格的行标题的集合
        for (int i = rowBeginModelNum; i < rowModelNum; i++) {
            Row rowModel = sheetModel.getRow(i);
            Cell cell = rowModel.getCell(0);
            String hang = cell.getStringCellValue()==null?"":cell.getStringCellValue();
            hangModelName.add(hang);
            hangModelNameTrim.add(hang == null ? "" : hang.trim());
        }

//        待处理表排除不读取的行列项
        //列排除
        List outCols = Arrays.asList(1, 2, 3, 4);
        //行派出
        List outRows = Arrays.asList(1, 2, 3, 4, 6, 7);

        List<List> xlsxData = new ArrayList(); //表数据
        for (int i = 0; i < rowTaskNum; i++) {
            if (!outRows.contains(i)) {
                //获取每行的数据
                Row rowTask = sheetTask.getRow(i);
                int cols = rowTask.getPhysicalNumberOfCells();
                //行数据
                List<Object> hangData = new ArrayList();
                for (int j = 0; j < cols; j++) {
                    if (!outCols.contains(j)) {
                        Cell cellTask = rowTask.getCell(j);
                        Object value = ExcelUtils.getCellValue(cellTask);
                        //数据保存
                        hangData.add(value);
                    }
                }
                xlsxData.add(hangData);
            }
        }

        //写入标椎表格

        for (int i = 0; i < xlsxData.size(); i++) {
            Row row = sheetModel.getRow(i);
            if (row == null) row = sheetModel.createRow(i);
            List hang = xlsxData.get(i);
            //标题单独处理
            if (i == 0) {

                //设置下划线样式
                int  physicalNumberOfCells = row.getPhysicalNumberOfCells();
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    if(cell != null && cell.getStringCellValue() != null){
                        cellhangStyle = cell.getCellStyle();
                    }

                }

                //并非多此一举，复用样式
                hang = xlsxData.get(1);

                if(hang.size() > physicalNumberOfCells){
                    for (int j = physicalNumberOfCells; j < hang.size(); j++) {
                        Cell cell = row.getCell(j);
                        if(cell == null) cell = row.createCell(j);
                        cell.setCellStyle(cellhangStyle);
                    }
                }

                if(hang.size() < physicalNumberOfCells){
                    for (int j = hang.size(); j < physicalNumberOfCells; j++) {
                        Cell cell = row.getCell(j);
                        if(cell == null) cell = row.createCell(j);
                    }
                }

                //设置合并的单元格
                sheetModel.removeMergedRegion(0);
                sheetModel.addMergedRegion(new CellRangeAddress(0, 0, 0, hang.size() - 1));

            }
            //列标题处理
            if (i == 1) {

                //并非多此一举，复用样式
                hang = xlsxData.get(1);
                cellhangStyle = row.getCell(2).getCellStyle();
                for (int j = row.getPhysicalNumberOfCells(); j < hang.size(); j++) {
                    Cell cell = row.getCell(j);
                    if(cell == null) cell = row.createCell(j);
                    cell.setCellStyle(cellhangStyle);
                }
                //设置合并的单元格
                sheetModel.removeMergedRegion(2);
                sheetModel.addMergedRegion(new CellRangeAddress(1, 1, 2, hang.size() - 1));
            }
            if (i == 2) {
                hang = xlsxData.get(i - 1);
                System.out.println(hang.toString());
                cellhangStyle = row.getCell(2).getCellStyle();

                for (int j = 1; j < hang.size(); j++) {
                    if (j == 1) {
                        Cell cellMerge = sheetModel.getRow(1).getCell(1);
                        CellStyle cellMergeStyle = cellMerge.getCellStyle();
                        cellMerge.setCellStyle(cellMergeStyle);
                        cellMerge.setCellValue(String.valueOf(hang.get(1)));
                    }
                    Cell cell = row.getCell(j);
                    if (cell == null) cell = row.createCell(j);
                    Object value = hang.get(j);
                    cell.setCellStyle(cellhangStyle);
                    if (value instanceof java.lang.String) cell.setCellValue(String.valueOf(value));
                    if (value instanceof java.lang.Double) cell.setCellValue((Double) value);

                }
            }


//写入数据
            if (i >= 3) {
                //获取行名
                hang = xlsxData.get(i - 1);
                Cell cell0 = row.getCell(0);
                String cell0Value = "";
                if (cell0 != null) {
                    cell0Value = cell0.getStringCellValue().trim().replaceAll(" ", "");
                }

                //获取数据的样式
                Cell cellStyle = row.getCell(1);
                if (cellStyle != null) cellDataStyle = cellStyle.getCellStyle();

                //轮训数据写入
                for (int j = 1; j < xlsxData.size(); j++) {
                    List hangDatas = xlsxData.get(j);
                    Object hangTitle = hangDatas.get(0);

                    for (int k = 1; k < hang.size(); k++) {
                        if (StringUtils.isNotBlank(String.valueOf(hangTitle)) && String.valueOf(hangTitle).trim().replaceAll(" ", "").equals(cell0Value)) {
                            Cell cell = row.getCell(k);
                            if (cell == null) cell = row.createCell(k);
                            cell.setCellStyle(cellDataStyle);
                            Object value = hang.get(k);
                            if (value instanceof java.lang.String) cell.setCellValue(String.valueOf(value));
                            if (value instanceof java.lang.Double) cell.setCellValue((Double) value);
                        }
                    }
                }
            }
        }
        //修改的数据写入表格
        this.write2Excel(workbookModel, modelPath);
    }


    /**
     * @param: [workbook, filePath]
     * @description: 写入表格的方法
     * @author: GongJun
     * @time: Created in 15:49 2020/10/12
     * @modified:
     * @return: void
     **/
    public void write2Excel(Workbook workbook, String filePath) throws Exception {
        FileOutputStream excelFileOutPutStream = new FileOutputStream(filePath);
        excelFileOutPutStream.flush();
        workbook.write(excelFileOutPutStream);
        workbook.close();
    }


    @Test
    public void test() throws Exception {
        styleUse();
    }


}
