package com.gongjun.changsha.FangDiChanDos;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:方式参批处理类
 * @Author: GongJun
 * @Date: Created in 9:12 2020/11/3
 */
@Slf4j
public class Start {
    public static String zonePath = "D:\\长沙项目\\房地产\\地区";

    public static String targetPath = "D:\\长沙项目\\房地产\\已处理";

    public static String standardExcelPath = "D:\\长沙项目\\房地产\\全市\\922-10 - 标准表格.XLS";

    //拷贝处理文件
    public static void copyExcel2Zone() {
        File file = new File(zonePath);
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File excelFile : fs) {
                if (excelFile.isFile() && StringUtils.endsWithIgnoreCase(excelFile.getName(), ".xls")) {
                    String fileName = excelFile.getName();
                    String zoneName = fileName.substring(11, fileName.lastIndexOf("."));
                    try {
                        FileUtils.copyFile(new File(standardExcelPath), new File(targetPath + "\\" + zoneName + "\\922-10.XLS"));
                    } catch (IOException e) {
                        log.info("异常信息:{}", e.getMessage());
                    }
                }
            }
        }
    }

    public void todo(){
        List<File> files = com.gongjun.changsha.tools.FileUtils.getFiles(zonePath,new ArrayList<>());

        for (File file:files){
            String sourceExcelName = file.getName();
            String sourceExcelPath = file.getAbsolutePath();
            String zone = sourceExcelName.substring(11, sourceExcelName.lastIndexOf("."));
            String targetExcelPath = targetPath + "\\" + zone + "\\922-10.XLS";
            Excel_10_03.todo(sourceExcelPath,targetExcelPath);
            Excel_10_04.todo(sourceExcelPath,targetExcelPath);
        }
    }

    public void todoFor1And2(){
        String zoneParent = "D:\\长沙项目\\房地产\\地区\\1-2";
        List<File> files = com.gongjun.changsha.tools.FileUtils.getFiles(zoneParent,new ArrayList<>());
        for(File file : files){
            String[] fileStrs = file.getAbsolutePath().split("\\\\");
            String zoneName = fileStrs[fileStrs.length-2];
            String fileName = file.getName();
            if(fileName.startsWith("(fdc-4301-1)")) Excel_10_01.todo(file.getAbsolutePath(),targetPath+"\\"+zoneName+"\\922-10.XLS");
            if(fileName.startsWith("(fdc-4301-02)")) Excel_10_02.todo(file.getAbsolutePath(),targetPath+"\\"+zoneName+"\\922-10.XLS");
        }


    }


    @Test
    public void test() {
        todoFor1And2();
    }
}
