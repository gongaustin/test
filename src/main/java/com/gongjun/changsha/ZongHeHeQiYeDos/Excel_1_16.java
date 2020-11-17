package com.gongjun.changsha.ZongHeHeQiYeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-16",对应关系:1-16VS 清查平台）
 * @Author: GongJun
 * @Date: Created in 10:36 2020/10/13
 */
public class Excel_1_16 {
    public void todo(String standardExcelPath){
        Workbook wb = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        wb.removeName("1-16");

    }
}
