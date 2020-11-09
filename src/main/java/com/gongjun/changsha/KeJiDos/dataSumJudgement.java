package com.gongjun.changsha.KeJiDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 判断区县数据之和是否等于全市数据(科技)
 * @Author: GongJun
 * @Date: Created in 9:24 2020/10/21
 */
public class dataSumJudgement {
    //全市数据表格路径
    private static String upLevel = "D:\\长沙项目\\科技\\全市";

    //区县数据表格路径
    private static String downLevel = "D:\\长沙项目\\科技\\地区";

    public static List<File> readFileList(String excelPath) {
        List<File> files = new ArrayList<>();
        if (excelPath == null) return files;
        File file = new File(excelPath);
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                files.addAll(readFileList(f.getAbsolutePath()));
            } else files.add(f);
        }
        return files;
    }

    //数据对比
    public static void dataJudge() {
        //获取全市的表格
        List<File> upExcelList = dataSumJudgement.readFileList(upLevel);
        if (CollectionUtils.isEmpty(upExcelList)) return;
        //获取区县表格
        List<File> downExelList = dataSumJudgement.readFileList(downLevel);

        if (CollectionUtils.isEmpty(downExelList)) return;
        for (int i = 0; i < downExelList.size(); i++) {
            if (downExelList.get(i).getAbsolutePath().contains("岳麓区（不含高新区）")) downExelList.remove(i);
        }
        //对比开始
        for (File fileOne : upExcelList) {
            System.out.println(fileOne.getAbsolutePath());
            Workbook upWorkbook = ExcelUtils.getWorkbookFromExcel(fileOne);
            String fileName = fileOne.getName();
            //获取Sheet数量
            int sheetNumber = upWorkbook.getNumberOfSheets();

            for (int i = 1; i < sheetNumber; i++) {
                Sheet upSheet = upWorkbook.getSheetAt(i);

                //删除地区
                for (int n = 5; n <= 14; n++) {
                    Row row = upSheet.getRow(n);
                    upSheet.removeRow(row);
                }
                upSheet.shiftRows(15, upSheet.getLastRowNum(), -(14 - 5 + 1));

                for (int j = 0; j < upSheet.getPhysicalNumberOfRows(); j++) {
                    Row upRow = upSheet.getRow(j);
                    if (upRow == null) continue;
                    for (int k = 0; k < upRow.getPhysicalNumberOfCells(); k++) {
                        Cell upCell = upRow.getCell(k);
                        if (upCell == null) continue;
                        Object valueOne = ExcelUtils.getCellValue(upCell);
                        if (!(valueOne instanceof Double)) continue;
                        Double valueTwo = 0d;
                        for (File fileTwo : downExelList) {
                            if (StringUtils.containsIgnoreCase(fileTwo.getName(), fileName)) {
                                Workbook downWorkbook = ExcelUtils.getWorkbookFromExcel(fileTwo);
                                Cell downCell = downWorkbook.getSheetAt(i).getRow(j).getCell(k);
                                Object downCellValue = ExcelUtils.getCellValue(downCell);
                                if (downCellValue instanceof Double) valueTwo += (Double) downCellValue;
                            }
                        }
                        valueOne = new BigDecimal((double) valueOne).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
                        int valueTwoInt = new BigDecimal(valueTwo).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
                        //if(((int) valueOne - valueTwoInt) < 1) System.out.println("["+fileName+":"+upSheet.getSheetName()+"]--"+ RegUtils.delAllSpaceForString(upRow.getCell(0).getStringCellValue())+","+"第"+(k+1)+"列数据一致,地方总和:"+valueTwoInt+",全市数据:"+valueOne);
                        if (Math.abs((int) valueOne - valueTwoInt) > 5) {
                            System.out.println("[" + fileName + ":" + upSheet.getSheetName() + "]--" + RegUtils.delAllSpaceForString(upRow.getCell(0).getStringCellValue()) + "," + "第" + (k + 1) + "列数据不一致,地方总和:" + valueTwoInt + ",全市数据:" + valueOne);
                        }
                    }
                }
            }
        }
    }


    @Test
    public void aa() {
        dataJudge();
    }


}
