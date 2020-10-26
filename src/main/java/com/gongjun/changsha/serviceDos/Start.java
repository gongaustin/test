package com.gongjun.changsha.serviceDos;

import com.gongjun.changsha.tools.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * @Description: 批处理类
 * @Author: GongJun
 * @Date: Created in 13:52 2020/10/23
 */
public class Start {
    //全市
    public static String upExcelPath = "D:\\长沙项目\\服务业\\全市\\922-11.XLS";
    //地区
    public static String downExcelFilePath = "D:\\长沙项目\\服务业\\地区";

    public void copyFile2Zone(){
        File file = new File(downExcelFilePath);
        if(file.isDirectory()){
            File[] fs = file.listFiles();
            for (File f:fs){
                if(f.isDirectory()){
                    FileUtils.copyFile(new File(upExcelPath).getAbsolutePath(),f.getAbsolutePath()+"\\"+new File(upExcelPath).getName());
                }
            }
        }
    }


    @Test
    public void test(){
        copyFile2Zone();
    }
}
