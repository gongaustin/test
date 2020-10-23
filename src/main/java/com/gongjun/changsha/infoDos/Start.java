package com.gongjun.changsha.infoDos;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 批量处理类 （信息化）
 * @Author: GongJun
 * @Date: Created in 11:16 2020/10/22
 */
public class Start {
    //处理表格路径
    public static String filePath="D:\\长沙项目\\信息化业";

    public static void main(String[] args) {
        List<File> files = new ArrayList();
        files = Start.getFiles(filePath,files);
        files.forEach(e->{
            String sourceExcelPath = e.getAbsolutePath();
            String targetExcelPath = e.getParent()+"\\922-5.xls";
            targetExcelPath = targetExcelPath.replace("\\信息化业\\","\\信息化\\地区\\");
            System.out.println(targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-01.xls")) Excel5_01.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-02.xls")) Excel5_02.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-03.xls")) Excel5_03.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-04.xls")) Excel5_04.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-05.xls")) Excel5_05.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-06.xls")) Excel5_06.todo(sourceExcelPath,targetExcelPath);
            if(StringUtils.contains(e.getName(),"5-07.xls")) Excel5_07.todo(sourceExcelPath,targetExcelPath);
        });
    }


    public static List<File> getFiles(String path,List<File> list) {
        if(list == null) list = new ArrayList<>();
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    getFiles(files[i].getPath(),list);
                } else {
                    list.add(files[i]);
                }
            }
        } else {
            list.add(file);
        }
        return list;
    }

}
