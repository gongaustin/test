package com.gongjun.changsha.FuWuYeDos;

import com.gongjun.changsha.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 批处理类
 * @Author: GongJun
 * @Date: Created in 13:52 2020/10/23
 */
@Slf4j
public class Start {
    //全市
    public static String upExcelPath = "D:\\长沙项目\\服务业\\922-11-标准表格.XLS";
    //地区
    public static String downExcelFilePath = "D:\\长沙项目\\服务业\\地区";

    /**
     * @param: []
     * @description: 批量处理的方法
     * @author: GongJun
     * @time: Created in 15:50 2020/10/26
     * @modified:
     * @return: void
     **/
    public static void batTodo() {
        //获取文件
        List<File> excelFileList = FileUtils.getFiles(downExcelFilePath, new ArrayList<>());
        for (File file : excelFileList) {
            String fileName = file.getName();
            if (StringUtils.startsWith(fileName, "11-01"))
                Excel11_01.todo(file.getAbsolutePath(), file.getParent() + "\\922-11.XLS");
            if (StringUtils.startsWith(fileName, "11-02"))
                Excel11_02.todo(file.getAbsolutePath(), file.getParent() + "\\922-11.XLS");
            if (StringUtils.startsWith(fileName, "11-03"))
                Excel11_03.todo(file.getAbsolutePath(), file.getParent() + "\\922-11.XLS");
            if (StringUtils.startsWith(fileName, "11-04"))
                Excel11_04.todo(file.getAbsolutePath(), file.getParent() + "\\922-11.XLS");
        }

    }

    public void copyFile2Zone() {
        File file = new File(downExcelFilePath);
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                if (f.isDirectory()) {
                    String newPath = f.getAbsolutePath() + "\\" + new File(upExcelPath).getName().replace("-标准表格", "");
                    try {
                        org.apache.commons.io.FileUtils.copyFile(new File(upExcelPath), new File(newPath));
                    } catch (IOException e) {
                        log.info("拷贝错误,错误信息{}",e.getMessage());
                    }
                }
            }
        }
    }

    @Test
    public void test() {
        copyFile2Zone();
        batTodo();
    }
}
