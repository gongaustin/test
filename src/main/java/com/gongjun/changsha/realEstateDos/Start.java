package com.gongjun.changsha.realEstateDos;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
    public static void copyExcel2Zone(){
        File file = new File(zonePath);
        if(file.isDirectory()){
            File[] fs = file.listFiles();
            for (File excelFile : fs){
                if(excelFile.isFile() && StringUtils.endsWithIgnoreCase(excelFile.getName(),".xls")){
                    String fileName = excelFile.getName();
                    String zoneName = fileName.substring(11,fileName.lastIndexOf("."));
                    try {
                        FileUtils.copyFile(new File(standardExcelPath),new File(targetPath+"\\"+zoneName+"\\922-10.XLS"));
                    } catch (IOException e) {
                        log.info("异常信息:{}",e.getMessage());
                    }
                }
            }
        }
    }


    @Test
    public void test(){
        copyExcel2Zone();
    }
}
