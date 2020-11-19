package com.gongjun.changsha.ZongHeHeQiYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-16",对应关系:1-16VS 清查平台）
 * @Author: GongJun
 * @Date: Created in 10:36 2020/10/13
 */
public class Excel_1_16 {
    public static String standardExcelPath = "";
    public static String todoExcelFilePath = "";
    public static void todo(){
        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        standardWorkbook.removeSheetAt(standardWorkbook.getSheetIndex("1-16"));
        ExcelUtils.write2Excel(standardWorkbook, standardExcelPath);
        System.out.println(standardExcelPath+":[1-16]移除成功");
    }
}
