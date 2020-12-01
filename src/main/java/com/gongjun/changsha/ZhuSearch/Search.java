package com.gongjun.changsha.ZhuSearch;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description:注解搜索
 * @Author: GongJun
 * @Date: Created in 8:54 2020/12/1
 */
public class Search {
    public static void getZhuTxt(File sourceFile){
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(sourceFile);

        int sheetNumber = workbook.getNumberOfSheets();

        for (int i = 0; i < sheetNumber; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            int sheetRowNumber = sheet.getPhysicalNumberOfRows();
            for (int j = (sheetRowNumber > 5?sheetNumber-5:0); j < sheetRowNumber; j++) {
                Row row = sheet.getRow(j);
                if(row == null) continue;
                int cols = row.getPhysicalNumberOfCells();
                for (int k = 0; k < cols; k++) {
                    Cell cell = row.getCell(k);
                    if(cell == null) continue;
                    Object value = ExcelUtils.getCellValue(cell);
                    if(value != null && value instanceof String){
                        String valueStr = ((String) value).trim();
                        if(valueStr.startsWith("注：")||valueStr.startsWith("注:")){
                            if(j<sheetRowNumber && sheet.getRow(j+1) != null && sheet.getRow(j+1).getCell(0) != null){
                                String strOther = sheet.getRow(j+1).getCell(0).getStringCellValue();
                                valueStr = valueStr + RegUtils.delAllSpaceForString(strOther);
                            }
                            System.out.println("["+sheetName+"]--"+valueStr);
                        }

                    }
                }
            }
        }




        return;

    }
}
