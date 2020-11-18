package com.gongjun.changsha.EbookHandler;

import com.gongjun.changsha.tools.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:批处理类
 * @Author: GongJun
 * @Date: Created in 16:34 2020/11/18
 */
public class Start {

    /**
     * @param: [sourExcelParent, targetExcelParent]
     * @description:批量处理[多个Sheet的表格拆分为单个Sheet的表格]
     * @author: GongJun
     * @time: Created in 15:39 2020/11/18
     * @modified:
     * @return: void
     **/
    public static void batSplitSheetsToSingleExcel(String sourExcelParent, String targetExcelParent) {
        List<File> files = FileUtils.getFiles(sourExcelParent, new ArrayList<>());
        for (File file : files) {
            EbookExcelUtils.splitSheetsToSingleExcel(file.getAbsolutePath(), targetExcelParent);
        }
    }
    //测试方法
    @Test
    public void test() {
        batSplitSheetsToSingleExcel("D:\\测试文件夹\\未拆分", "D:\\测试文件夹\\拆分后");
    }
}