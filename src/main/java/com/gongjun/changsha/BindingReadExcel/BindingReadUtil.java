package com.gongjun.changsha.BindingReadExcel;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:读取Excel数据通用类
 * @Author: GongJun
 * @Date: Created in 9:06 2020/11/19
 */
public class BindingReadUtil {
    /**
     * @param: [表格文件路径, 排除的行, 包含的列, 读取的表格序号]
     * @description:长沙政府项目读取表格通用方法
     * @author: GongJun
     * @time: Created in 9:16 2020/11/19
     * @modified:
     * @return: java.util.List<java.util.List<java.lang.Object>>
     **/
    public static List<List<Object>> commonRead(String excelPath,List<Integer> exceptRowsList,List<Integer> containColsList, int sheetIndex){
        List<List<Object>> data = new ArrayList<>();
        if(StringUtils.isBlank(excelPath)) return data;
        //获取Workbook
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(excelPath));
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int sheetRowNumber = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < sheetRowNumber; i++) {
            if(exceptRowsList.contains(i)) continue;
            Row row = sheet.getRow(i);
            if(row == null) continue;
            List<Object> rowData = new ArrayList<>(); //行数据
            for (int j = 0; j < containColsList.size(); j++) {
                String index = null; //指标
                if (j == 0) {
                    Cell cell = row.getCell(containColsList.get(j));
                    if (cell == null) continue;
                    index = cell.getStringCellValue();
                    if(StringUtils.isBlank(index)) continue;
                    if("总计".equals(RegUtils.delAllSpaceForString(index))) index = "总  计";
                    if(index.startsWith(" ")) index = "  "+RegUtils.delAllSpaceForString(index);
                    rowData.add(index);
                }
                if (j > 0) {
                    Cell cell = row.getCell(containColsList.get(j));
                    if (cell == null) continue;
                    Object value = ExcelUtils.getCellValue(cell);
                    rowData.add(value);
                }
            }
            if(!CollectionUtils.isEmpty(rowData) && rowData.size()==containColsList.size()) data.add(rowData);
        }
        return data;
    }
}
