package com.gongjun.changsha.excelDos;

import com.gongjun.changsha.tools.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:处理表格922-1.xls中Sheet:"1-01",对应关系:1-01VS1-07A-cs（开放平台）
 * @Author: GongJun
 * @Date: Created in 10:34 2020/10/13
 */
public class Excel_1_01 {

    public static void todo(){
        //标准表格的路径
        String standardExcelPath = "D:\\长沙项目\\表格批量处理\\标准表格\\922-1.xlsx";

        //待处理表格的保存文件夹路径
        String todoExcelFilePath = "D:\\长沙项目\\表格批量处理\\平台导出表格";
        File file = new File(todoExcelFilePath);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        List<String> accordExcels = new ArrayList<>();
        for(File f:fs){				//遍历File[]数组
            if(!f.isDirectory())		//若非目录(即文件)，则打印
            {
                String excelPath = f.getAbsolutePath();
                if(excelPath != null && StringUtils.contains(excelPath,"1-07A-cs")) accordExcels.add(f.getAbsolutePath());
            }
        }
        switch (accordExcels.size()){
            case 0:
                System.out.println("没有符合条件的表格请重新检查！");
                break;
            case 1:
                dataDos(accordExcels.get(0),standardExcelPath);
                break;
            default:
                System.out.println("有重复表格请重新检查！");
                break;
        }



        //待处理表格的文件夹

    }

    public static void dataDos(String dataExcelPath,String standardExcelPath){

        //获取标准表格的Workbook
        Workbook standardWorkbook = ExcelUtils.getWorkbookFromExcel(new File(standardExcelPath));
        //获取数据表格的Workbook
        Workbook dataWorkbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
        if(standardWorkbook == null | dataWorkbook == null) return;

        //获取标准表格的Sheet
        Sheet standardSheet = standardWorkbook.getSheet("1-01");

        //获取数据表格的Sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(0);



    }


    @Test
    public void test(){
        todo();
    }


}
