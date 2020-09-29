package com.gongjun.changsha;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 从多个sheet中获取数据写入另一个Sheet中
 * @Author: GongJun
 * @Date: Created in 10:35 2020/9/29
 */
public class excelDataBinding {
    /**
     * @param: []
     * @description: 核定数据的方法
     * @author: GongJun
     * @time: Created in 10:37 2020/9/29
     * @modified:
     * @return: void
     **/
    public void todo() throws Exception{

        String excelPath = "D:\\长沙项目\\测试数据\\测试表格.xlsx";

        //读取表格
        Workbook wb = ExcelUtils.getWorkbookFromExcel(new File(excelPath));
        //获取Sheet的个数
        int numOfSheet = wb.getNumberOfSheets();
        System.out.println(numOfSheet);

        //获取最后一张表的Sheet
        Sheet sl = wb.getSheetAt(numOfSheet-1);

        List<CellRangeAddress> cras = sl.getMergedRegions();

        Iterator<Row> it = sl.iterator();
        while (it.hasNext()){
            Row rw = it.next();
            Iterator<Cell> cells = rw.iterator();
            while (cells.hasNext()){
                Cell cell = cells.next();
                Object value = "";
                switch (cell.getCellTypeEnum()){
                    case STRING:
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        value = cell.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        value = cell.getBooleanCellValue();
                        break;
                    case FORMULA: {
                        //判断cell是否为日期格式
                        if (DateUtil.isCellDateFormatted(cell)) {
                            //转换为日期格式YYYY-mm-dd
                            value = cell.getDateCellValue();
                        } else {
                            //数字
                            value = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    }
                    default:
                        break;
                }

                System.out.println(value);

            }
        }

//        cras.stream().forEach(e->{
//            System.out.println(e.formatAsString());
//
//        });



        
        




    }
    
    
    /**
     * @param: []
     * @description: 通用单元测试方法
     * @author: GongJun
     * @time: Created in 10:43 2020/9/29
     * @modified: 
     * @return: void
     **/
    @Test
    public void test() throws Exception{
        todo();
    }



}
