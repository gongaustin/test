package com.gongjun.changsha.techDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.FileUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;


/**
 * @Description: 科技批处理类
 * @Author: GongJun
 * @Date: Created in 15:23 2020/10/19
 */
public class Start {
    public static void main(String[] args) {

        //常量设置
        String standardExcelPath = "D:\\长沙项目\\科技\\效果表\\922-4.xlsx";

        //处理表格路径
        String todoExcelFilePath = "D:\\长沙项目\\科技\\原始数据";
        String zoneExcelOutPath = "D:\\长沙项目\\科技\\地区";
        File file = new File(todoExcelFilePath);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        for (File f : fs) {                //遍历File[]数组
            if (!f.isDirectory())        //若非目录(即文件)，则打印
            {
                String dataExcelPath = f.getAbsolutePath();
                if(dataExcelPath.endsWith(".xls")){
                    Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(dataExcelPath));
                    Sheet sheet = workbook.getSheetAt(0);
                    String zone = sheet.getRow(sheet.getLastRowNum()).getCell(1).getStringCellValue();
                    if(zone != null) zone = RegUtils.delAllSpaceForString(zone);
                    //创建地区的文件件
                    String zonePath = zoneExcelOutPath+"\\"+zone;
                    File zoneFilePath = new File(zonePath);
                    if(!zoneFilePath.exists()) {
                        try {
                            zoneFilePath.mkdir();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    File standardFile = new File(standardExcelPath);
                    String standardOutExcelFilePath = zoneFilePath+"\\"+standardFile.getName();
                    FileUtils.copyFile(standardExcelPath,standardOutExcelFilePath);
                    CommonSet.dataExcelPathSet(dataExcelPath);
                    CommonSet.standardExcelPathSet(standardOutExcelFilePath);
                    System.out.println("**********["+zone+"]开始处理"+"**********");
                    Start.todo();
                }
            }
        }
    }

    public static void todo(){
        Excel_4_01.todo();
        Excel_4_02.todo();
        Excel_4_03.todo();
        Excel_4_04.todo();
        Excel_4_05.todo();
        Excel_4_06.todo();
        Excel_4_07.todo();
    }
}
