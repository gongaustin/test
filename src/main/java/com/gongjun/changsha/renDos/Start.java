package com.gongjun.changsha.renDos;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * @Description: 批处理类 （任主任任务）
 * @Author: GongJun
 * @Date: Created in 9:31 2020/10/19
 */
@Slf4j
public class Start {
    public static void main(String[] args) {
        //首先替换表格
        copyFiles();

        //处理表格922-1.xls
        Excel_1_01.todo();
        Excel_1_02.todo();
        Excel_1_03.todo();
        Excel_1_04.todo();
        Excel_1_05.todo();
        Excel_1_06.todo();
        Excel_1_07.todo();
        Excel_1_08.todo();
        Excel_1_09.todo();
        Excel_1_10.todo();
        Excel_1_11.todo();
        Excel_1_12.todo();
        Excel_1_13.todo();
//        Excel_1_14.todo();
//        Excel_1_15.todo();
//        Excel_1_16.todo();
//        Excel_1_17.todo();
//        Excel_1_18.todo();
        //处理表格922-2.xls
        Excel_2_01.todo();
        Excel_2_02.todo();
        Excel_2_03.todo();
        Excel_2_04.todo();
        Excel_2_05.todo();
        Excel_2_06.todo();
        Excel_2_07.todo();
        Excel_2_08.todo();
        Excel_2_09.todo();
        Excel_2_10.todo();
        Excel_2_11.todo();
        Excel_2_12.todo();
        Excel_2_13.todo();
        Excel_2_14.todo();
        Excel_2_15.todo();
        Excel_2_16.todo();
        Excel_2_17.todo();
        Excel_2_18.todo();
        Excel_2_19.todo();
        Excel_2_20.todo();
        Excel_2_21.todo();
        Excel_2_22.todo();
        Excel_2_23.todo();
        Excel_2_24.todo();
        Excel_2_25.todo();
        Excel_2_26.todo();
    }

    public static void copyFiles() {
        //拷贝标准文件
        try {
            FileUtils.copyFile(new File("D:\\长沙项目\\表格批量处理\\标准表格\\922-1-副本（重新运行前一定要覆盖原来的）.xlsx"), new File("D:\\长沙项目\\表格批量处理\\标准表格\\922-1.xlsx"));
            FileUtils.copyFile(new File("D:\\长沙项目\\表格批量处理\\标准表格\\922-2-副本（重新运行前一定要覆盖原来的）.xlsx"), new File("D:\\长沙项目\\表格批量处理\\标准表格\\922-2.xlsx"));
        } catch (Exception e) {
            log.info("错误信息:{}", e.getMessage());
        }
        log.info("文件拷贝完成");
    }

    @Test
    public void test() {
        copyFiles();
    }
}
