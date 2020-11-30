package com.gongjun.changsha.YueLuZone;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

/**
 * @Description:验证岳麓区(含高新区)数据>=岳麓区(不含高新区)数据
 * @Author: GongJun
 * @Date: Created in 8:52 2020/11/30
 */
@Slf4j
public class YueLu {
    public static void todo(String ExcelPathInclude, String ExcelPathNotInclude) {
        //含高新
        Workbook includeWorkbook = ExcelUtils.getWorkbookFromExcel(new File(ExcelPathInclude));


        //不含高新
        Workbook notIncludeWorkbook = ExcelUtils.getWorkbookFromExcel(new File(ExcelPathNotInclude));

        //Sheet数量
        int includeSheetNumber = includeWorkbook.getNumberOfSheets();

        for (int i = 0; i < includeSheetNumber; i++) {
            Sheet includeSheet = includeWorkbook.getSheetAt(i);
            String sheetName = includeSheet.getSheetName();
            //行数
            int includeSheetRowNumber = includeSheet.getPhysicalNumberOfRows();
            for (int j = 0; j < includeSheetRowNumber; j++) {
                Row includeRow = includeSheet.getRow(j);
                if (includeRow == null) continue;
                int includeRowCols = includeRow.getPhysicalNumberOfCells();
                for (int k = 0; k < includeRowCols; k++) {
                    Cell includeCell = includeRow.getCell(k);
                    if (includeCell == null) return;
                    Object includeValue = ExcelUtils.getCellValue(includeCell);
                    Double douleIncludeValue = 0d;
                    if (includeValue instanceof Double) douleIncludeValue = (double) includeValue;
                    if (includeValue instanceof String) {
                        try {
                            douleIncludeValue = new Double((String) includeValue).doubleValue();
                        } catch (Exception e) {
                            continue;
                        }
                    }

                    Cell notIncludeCell = notIncludeWorkbook.getSheetAt(i).getRow(j).getCell(k);
                    Object notIncludeValue = ExcelUtils.getCellValue(notIncludeCell);

                    Double douleNotIncludeValue = 0d;
                    if (notIncludeValue instanceof Double) douleNotIncludeValue = (double) notIncludeValue;
                    if (includeValue instanceof String) douleNotIncludeValue = new Double((String) notIncludeValue).doubleValue();

                    //比较
                    if(douleIncludeValue-douleNotIncludeValue<=0){
                        String index = RegUtils.delAllSpaceForString(includeRow.getCell(0).getStringCellValue());
                        log.info("文件{},表格:{},指标{},第{}列数据小于不含高新区的数据,请检查",ExcelPathInclude,sheetName,index,(k+1));
                    }
                }

            }
        }


    }
}
