package com.gongjun.changsha.JianZhuYe;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:批量处理类
 * @Author: GongJun
 * @Date: Created in 13:25 2020/11/19
 */
@Slf4j
public class Start {
    //原始数据表格路径
    private static String sourceExcelParentPath = "D:\\长沙项目\\建筑业\\地区";

    private static String targetExcelPatentPath = "D:\\长沙项目\\建筑业\\已处理";

    private static String standardExcelPath = "D:\\长沙项目\\建筑业\\标准表格\\922-7.XLS";

    //拷贝标准文件
    private static void copyStandardExcel(){
        List<File> fileList = FileUtils.getFiles(sourceExcelParentPath,new ArrayList<>());
        List<String> zones = new ArrayList<>();
        for (File file:fileList){
            String asPathPatent = file.getParent();
            String[] strings = asPathPatent.split("\\\\");
            String zone = strings[strings.length-1];
            if(!zones.contains(zone)) zones.add(zone);
        }

        for (String zone:zones){
            try {
                org.apache.commons.io.FileUtils.copyFile(new File(standardExcelPath),new File(targetExcelPatentPath+"\\"+zone+"\\922-7.XLS"));
            } catch (IOException e) {
                log.info("表格拷贝出现错误，错误信息:{}",e.getMessage());
            }
        }

    }

    public static void todo(){
        copyStandardExcel();
        List<File> fileList = FileUtils.getFiles(sourceExcelParentPath,new ArrayList<>());
        for (File file:fileList){
            String asPathPatent = file.getParent();
            String[] strings = asPathPatent.split("\\\\");
            String zone = strings[strings.length-1];
            String fileName = file.getName();
            if(fileName.startsWith("660+")) Excel_7_03.todo(file.getAbsolutePath(),targetExcelPatentPath+"\\"+zone+"\\922-7.XLS");
            if(fileName.startsWith("661+")) Excel_7_04.todo(file.getAbsolutePath(),targetExcelPatentPath+"\\"+zone+"\\922-7.XLS");
        }
    }

    @Test
    public void test(){
        todo();
    }

}
