package com.gongjun.changsha.FuWuYeDos;

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
 * @Description: 判断区县数据之和是否等于全市数据(服务业)
 * @Author: GongJun
 * @Date: Created in 9:24 2020/10/21
 */
public class dataSumJudgement {
    //全市数据表格路径
    private static String upLevel = "D:\\长沙项目\\服务业\\全市";

    //区县数据表格路径
    private static String downLevel = "D:\\长沙项目\\服务业\\地区";

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
            if (downExelList.get(i).getAbsolutePath().contains("（不含高新）")) downExelList.remove(i);
        }


        for (File f : upExcelList) {
            Workbook workbook = ExcelUtils.getWorkbookFromExcel(f);
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                int dataBeginRow = 0;
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) continue;
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        String value = cell.getStringCellValue();
                        if (value != null) value = RegUtils.delAllSpaceForString(value);
                        if (StringUtils.containsAny(value, "总计")) {
                            dataBeginRow = j;
                            break;
                        }
                    }
                }
                if (dataBeginRow == 0) continue;
                for (int j = dataBeginRow; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) continue;
                    if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) return;
                    String title = RegUtils.delAllSpaceForString(row.getCell(0).getStringCellValue());
                    if (StringUtils.isBlank(title) || StringUtils.containsAny(title, "芙蓉区", "开福区", "浏阳市", "宁乡市", "天心区", "望城区", "雨花区", "岳麓区", "长沙县"))
                        continue;
                    for (int k = 1; k < row.getPhysicalNumberOfCells(); k++) {
                        Object upValue = ExcelUtils.getCellValue(row.getCell(k));
                        if (!(upValue instanceof Double)) continue;
                        Double valueSum = 0d;
                        for (File file : downExelList) {
                            if (file.getName().contains(sheetName)) {
                                Workbook workbook1 = ExcelUtils.getWorkbookFromExcel(file);
                                Sheet sheet1 = workbook1.getSheetAt(0);
                                int markline = 0;
                                for (int l = 0; l < sheet1.getPhysicalNumberOfRows(); l++) {
                                    Row row1 = sheet1.getRow(l);
                                    if (row1 == null) continue;
                                    Cell cell1 = row1.getCell(0);
                                    if (cell1 != null) {
                                        String value1 = cell1.getStringCellValue();
                                        if (value1 == null) continue;
                                        value1 = RegUtils.delAllSpaceForString(value1);
                                        if (StringUtils.containsAny(value1, "芙蓉区", "开福区", "浏阳市", "宁乡市", "天心区", "望城区", "雨花区", "岳麓区", "长沙县"))
                                            value1 = "总计";
                                        if (title.equals(value1)) {
                                            markline = l;
                                        }
                                    }
                                }
                                if (markline == 0) continue;
                                Object downCellValue = ExcelUtils.getCellValue(sheet1.getRow(markline).getCell(k));
                                if (downCellValue instanceof Double) valueSum += (Double) downCellValue;
                            }
                        }

                        int valueOne = new BigDecimal((double) upValue).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
                        int valueTwoInt = new BigDecimal(valueSum).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
//                       System.out.println("项目:"+title+"--valueOne:"+valueOne+"--valueTwoInt:"+valueTwoInt);
                        if (Math.abs(valueOne - valueTwoInt) > 5) {
                            System.out.println("[" + f.getAbsolutePath() + ":" + sheet.getSheetName() + "]--" + RegUtils.delAllSpaceForString(row.getCell(0).getStringCellValue()) + "," + "第" + (k + 1) + "列数据不一致,地方总和:" + valueTwoInt + ",全市数据:" + valueOne);
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
