package com.gongjun.changsha;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description: Excel样式处理
 * @Author: GongJun
 * @Date: Created in 16:03 2020/10/10
 */
public class excelStyleUse {

    public void styleUse() throws Exception{

        //样式表
        String modelPath = "D:\\长沙项目\\测试数据\\复用样式\\922-1.XLS";

        //待处理表
        String taskPath = "(1-07A-cs)按行业门类、地区分组的法人单位数(A)2020-10-10.xlsx";

        Workbook workbookModel = ExcelUtils.getWorkbookFromExcel(new File(modelPath));

        Workbook workbookTask = ExcelUtils.getWorkbookFromExcel(new File(taskPath));

        Sheet sheetTask = workbookTask.getSheetAt(0);
        int rowTaskNum = sheetTask.getPhysicalNumberOfRows();

        for (int i = 0; i < rowTaskNum; i++) {
            Row rowTask = sheetTask.getRow(i);



        }


    }


}
