package com.gongjun.changsha.industryDos;

import com.gongjun.changsha.tools.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:批处理类
 * @Author: GongJun
 * @Date: Created in 10:11 2020/10/28
 */
public class Start {
    //区县表格路径
    public static String zonePath = "D:\\长沙项目\\工业\\地区";

    public static String upExcelPath = "D:\\长沙项目\\工业\\全市\\922-6-标准表格.XLS";

    public static void batTodo() {
        List<File> list = FileUtils.getFiles(zonePath, new ArrayList<>());

        for (File file : list) {
            if (file.getName().endsWith("分类型.xlsx"))
                Excel6_05.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
            if (file.getName().endsWith("大类行业.xlsx"))
                Excel6_06.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
            if (file.getName().endsWith("大类行业国有控股.xlsx"))
                Excel6_07.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
            if (file.getName().endsWith("大类行业私营.xlsx"))
                Excel6_08.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
            if (file.getName().endsWith("大类行业外商及港澳台.xlsx"))
                Excel6_09.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
            if (file.getName().endsWith("大类行业大中型.xlsx"))
                Excel6_10.todo(file.getAbsolutePath(), file.getParent().replace("地区", "处理后" + "\\" + "地区") + "\\922-6.XLS");
        }
    }

    /**
     * @param: []
     * @description:重命名表格后缀名，解决文件类型与表格后缀名不匹配的问题
     * @author: GongJun
     * @time: Created in 11:03 2020/10/28
     * @modified:
     * @return: void
     **/
    public void renameExcelSuffix() {
        List<File> list = FileUtils.getFiles(zonePath, new ArrayList<>());

        for (File file : list) {
            if (file.getName().endsWith("xls")) file.renameTo(new File(file.getAbsolutePath().replace("xls", "xlsx")));
        }
    }

    /**
     * @param: []
     * @description:复制表格到各区县
     * @author: GongJun
     * @time: Created in 11:02 2020/10/28
     * @modified:
     * @return: void
     **/
    public void copyFile2Zone() {
        File file = new File(zonePath);
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                if (f.isDirectory()) {
                    String newPath = f.getAbsolutePath().replace("地区", "处理后" + "\\" + "地区") + "\\" + new File(upExcelPath).getName().replace("-标准表格", "");
                    try {
                        org.apache.commons.io.FileUtils.copyFile(new File(upExcelPath), new File(newPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void test() {
        batTodo();
    }

}
