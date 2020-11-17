package com.gongjun.changsha.ZongHeHeQiYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-15",对应关系:1-15VS （清查平台）
 * @Author: GongJun
 * @Date: Created in 10:36 2020/10/13
 */
public class Excel_1_15 {
    public void todo(String standardExcelPath){
        Workbook wb = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        wb.removeName("1-15");

    }
}
