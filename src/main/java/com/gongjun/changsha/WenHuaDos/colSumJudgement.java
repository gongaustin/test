package com.gongjun.changsha.WenHuaDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:11 2020/11/13
 */
@Slf4j
public class colSumJudgement {
    //测试表格路径
    private static String zonePath = "D:\\长沙项目\\核验数据\\文化\\地区\\芙蓉区";
    //需要测试的表格
    List<String> judgeSheetNames = Arrays.asList(
            "3-03",
            "3-04",
            "3-05",
            "3-06",
            "3-07",
            "3-08",
            "3-09"
    );

    public void judge(){
        List<File> files = FileUtils.getFiles(zonePath,new ArrayList<>());
        for (File excelFile:files){
            //获取Workbook
            Workbook workbook = ExcelUtils.getWorkbookFromExcel(excelFile);
            //获取SheetNumber
            int sheetNumber = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNumber; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if(!judgeSheetNames.contains(sheetName)) continue;
                //获取Sheet的行数
                int sheetRowNumber = sheet.getPhysicalNumberOfRows();
                //获取总计的Row
                Row sumRow = sheet.getRow(3);
                //获取列数
                int sheetColNumber = sumRow.getPhysicalNumberOfCells();
                //获取指标名称
                for (int j = 1; j < sheetColNumber; j++) {
                    //获取总计
                    Cell sumCell = sumRow.getCell(j);
                    if(sumCell == null) continue;
                    double sum = sumCell.getNumericCellValue();
                    double sonSum = 0d;
                    for (int k = 4; k < sheetRowNumber; k++) {
                        Row sonRow = sheet.getRow(k);
                        Cell sonCell = sonRow.getCell(j);
                        if(sonCell == null ) continue;
                        double son = sonCell.getNumericCellValue();
                        sonSum = sonSum + son;
                    }
                    if(Math.abs(sum-sonSum)>0.5) System.out.println("文件["+excelFile+"]表格["+sheetName+",总计第"+(j+1)+"列数据加总不等,和为:"+sonSum+",总计为:"+sum);
                }
            }
        }
    }

    @Test
    public void test(){
        judge();
    }
}
