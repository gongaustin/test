package com.gongjun.changsha.synthesizeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.List;

/**
 * @Description:综合
 * @Author: GongJun
 * @Date: Created in 14:20 2020/11/5
 */
@Slf4j
public class dataSumJudgement {
    //全市数据表格路径
//    private static String upLevel = "D:\\长沙项目\\科技\\全市";
    private static String upLevel = "D:\\长沙项目\\综合&企业\\全市";

    //区县数据表格路径
//    private static String downLevel = "D:\\长沙项目\\科技\\地区";
    private static String downLevel = "D:\\长沙项目\\综合&企业\\地区";

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
        List<File> upExcelList = com.gongjun.changsha.serviceDos.dataSumJudgement.readFileList(upLevel);
        if (CollectionUtils.isEmpty(upExcelList)) return;
        for (int i = 0; i < upExcelList.size(); i++) {
            if (upExcelList.get(i).getAbsolutePath().contains("高新") || upExcelList.get(i).getAbsolutePath().contains("922-2")) upExcelList.remove(i);
        }
        if (CollectionUtils.isEmpty(upExcelList)) return;
        //获取区县表格
        List<File> downExelList = com.gongjun.changsha.serviceDos.dataSumJudgement.readFileList(downLevel);

        if (CollectionUtils.isEmpty(downExelList)) return;
        for (int i = 0; i < downExelList.size(); i++) {
            if (downExelList.get(i).getAbsolutePath().contains("高新") || downExelList.get(i).getAbsolutePath().contains("922-2"))
                downExelList.remove(i);
        }

        //不必对的Sheet
        List<String> exceptSheets = Arrays.asList(
                //街道数据
                "1-01",
                "1-02",
                "1-07",
                "1-08",
                "1-11",
                //未统计数据
                "1-14",
                "1-15",
                "1-16",
                "1-17",
                "1-18",
                "2-04",
                "2-05",
                "2-06",
                "2-12",
                "2-25",
                "2-26"
                );

        for (File f : upExcelList) {
            Workbook workbook = ExcelUtils.getWorkbookFromExcel(f);
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if(exceptSheets.contains(sheetName)) continue;
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
                            Workbook workbook1 = ExcelUtils.getWorkbookFromExcel(file);
                            Sheet sheet1 = workbook1.getSheet(sheetName);
                            int markline = 0;
                            for (int l = 0; l < sheet1.getPhysicalNumberOfRows(); l++) {
                                Row row1 = sheet1.getRow(l);
                                if (row1 == null) continue;
                                Cell cell1 = row1.getCell(0);
                                if (cell1 != null) {
                                    String value1 = cell1.getStringCellValue();
                                    if (value1 == null) continue;
                                    value1 = RegUtils.delAllSpaceForString(value1);
//                                        if(StringUtils.containsAny(value1,
//                                                "芙蓉区",
//                                                "开福区",
//                                                "浏阳市",
//                                                "宁乡市",
//                                                "天心区",
//                                                "望城区",
//                                                "雨花区",
//                                                "岳麓区",
//                                                "长沙县")) value1 = "总计";
                                    if (title.equals(value1)) {
                                        markline = l;
                                    }
                                }
                            }
                            if (markline == 0) continue;
                            Object downCellValue = ExcelUtils.getCellValue(sheet1.getRow(markline).getCell(k));
                            if (downCellValue instanceof Double) valueSum += (Double) downCellValue;
//                            System.out.println(file + "--" + downCellValue);
                        }
                        int valueOne = new BigDecimal((double) upValue).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
                        int valueTwoInt = new BigDecimal(valueSum).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
                        if (Math.abs(valueOne - valueTwoInt) > 5) {
                            log.info("[{}:{}]--指标:{},第{}列不一致,地方:{}--全市:{}",
                                    f.getName(),
                                    sheet.getSheetName(),
                                    RegUtils.delAllSpaceForString(row.getCell(0).getStringCellValue()),
                                    k+1,
                                    valueTwoInt,
                                    valueOne
                            );
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