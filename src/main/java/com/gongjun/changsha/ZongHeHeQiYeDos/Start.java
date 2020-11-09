package com.gongjun.changsha.ZongHeHeQiYeDos;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 批处理类 （任主任任务）
 * @Author: GongJun
 * @Date: Created in 9:31 2020/10/19
 */
@Slf4j
public class Start {
    //待处理表格路径
    public static String sourceExcelsPath = "D:\\长沙项目\\综合&企业待处理\\地区";

    //生成表格路径
    public static String targetExcelsPath = "D:\\长沙项目\\综合&企业待处理\\已处理";


    public void todo(){
        List<File> sourceExcels = com.gongjun.changsha.tools.FileUtils.getFiles(sourceExcelsPath,new ArrayList<>());
        List<String> zones = new ArrayList<>();
        for (File file:sourceExcels){
            String excelPath = file.getAbsolutePath();
            String zone = excelPath.substring(20,23);
            if(!zones.contains(zone)) zones.add(zone);

        }

        for (String zone:zones){
            String standarExcelPathForOne = targetExcelsPath+"\\"+zone+"\\922-1.xlsx";
            String standarExcelPathForTwo = targetExcelsPath+"\\"+zone+"\\922-2.xlsx";
            String sourceExcelPath = sourceExcelsPath+"\\"+zone;
            setStandardExcelsPath(standarExcelPathForOne,standarExcelPathForTwo);
            setSourceExcelsPath(sourceExcelPath);
            doExcels();
            log.info("{}处理完毕",zone);
        }
    }

    public static void setStandardExcelsPath(String standarExcelPathForOne,String standarExcelPathForTwo){
        //处理表格922-1.xls
        Excel_1_01.standardExcelPath = standarExcelPathForOne;
        Excel_1_02.standardExcelPath = standarExcelPathForOne;
        Excel_1_03.standardExcelPath = standarExcelPathForOne;
        Excel_1_04.standardExcelPath = standarExcelPathForOne;
        Excel_1_05.standardExcelPath = standarExcelPathForOne;
        Excel_1_06.standardExcelPath = standarExcelPathForOne;
        Excel_1_07.standardExcelPath = standarExcelPathForOne;
        Excel_1_08.standardExcelPath = standarExcelPathForOne;
        Excel_1_09.standardExcelPath = standarExcelPathForOne;
        Excel_1_10.standardExcelPath = standarExcelPathForOne;
        Excel_1_11.standardExcelPath = standarExcelPathForOne;
        Excel_1_12.standardExcelPath = standarExcelPathForOne;
        Excel_1_13.standardExcelPath = standarExcelPathForOne;
//        Excel_1_14.standardExcelPath = standarExcelPathForOne;
//        Excel_1_15.standardExcelPath = standarExcelPathForOne;
//        Excel_1_16.standardExcelPath = standarExcelPathForOne;
//        Excel_1_17.standardExcelPath = standarExcelPathForOne;
//        Excel_1_18.standardExcelPath = standarExcelPathForOne;
        //处理表格922-2.xls
        Excel_2_01.standardExcelPath = standarExcelPathForTwo;
        Excel_2_02.standardExcelPath = standarExcelPathForTwo;
        Excel_2_03.standardExcelPath = standarExcelPathForTwo;
        Excel_2_04.standardExcelPath = standarExcelPathForTwo;
        Excel_2_05.standardExcelPath = standarExcelPathForTwo;
        Excel_2_06.standardExcelPath = standarExcelPathForTwo;
        Excel_2_07.standardExcelPath = standarExcelPathForTwo;
        Excel_2_08.standardExcelPath = standarExcelPathForTwo;
        Excel_2_09.standardExcelPath = standarExcelPathForTwo;
        Excel_2_10.standardExcelPath = standarExcelPathForTwo;
        Excel_2_11.standardExcelPath = standarExcelPathForTwo;
        Excel_2_12.standardExcelPath = standarExcelPathForTwo;
        Excel_2_13.standardExcelPath = standarExcelPathForTwo;
        Excel_2_14.standardExcelPath = standarExcelPathForTwo;
        Excel_2_15.standardExcelPath = standarExcelPathForTwo;
        Excel_2_16.standardExcelPath = standarExcelPathForTwo;
        Excel_2_17.standardExcelPath = standarExcelPathForTwo;
        Excel_2_18.standardExcelPath = standarExcelPathForTwo;
        Excel_2_19.standardExcelPath = standarExcelPathForTwo;
        Excel_2_20.standardExcelPath = standarExcelPathForTwo;
        Excel_2_21.standardExcelPath = standarExcelPathForTwo;
        Excel_2_22.standardExcelPath = standarExcelPathForTwo;
        Excel_2_23.standardExcelPath = standarExcelPathForTwo;
        Excel_2_24.standardExcelPath = standarExcelPathForTwo;
        Excel_2_25.standardExcelPath = standarExcelPathForTwo;
        Excel_2_26.standardExcelPath = standarExcelPathForTwo;
    }



    public static void setSourceExcelsPath (String sourceExcelPath){
        Excel_1_01.todoExcelFilePath = sourceExcelPath;
        Excel_1_02.todoExcelFilePath = sourceExcelPath;
        Excel_1_03.todoExcelFilePath = sourceExcelPath;
        Excel_1_04.todoExcelFilePath = sourceExcelPath;
        Excel_1_05.todoExcelFilePath = sourceExcelPath;
        Excel_1_06.todoExcelFilePath = sourceExcelPath;
        Excel_1_07.todoExcelFilePath = sourceExcelPath;
        Excel_1_08.todoExcelFilePath = sourceExcelPath;
        Excel_1_09.todoExcelFilePath = sourceExcelPath;
        Excel_1_10.todoExcelFilePath = sourceExcelPath;
        Excel_1_11.todoExcelFilePath = sourceExcelPath;
        Excel_1_12.todoExcelFilePath = sourceExcelPath;
        Excel_1_13.todoExcelFilePath = sourceExcelPath;
//        Excel_1_14.todoExcelFilePath = sourceExcelPath;
//        Excel_1_15.todoExcelFilePath = sourceExcelPath;
//        Excel_1_16.todoExcelFilePath = sourceExcelPath;
//        Excel_1_17.todoExcelFilePath = sourceExcelPath;
//        Excel_1_18.todoExcelFilePath = sourceExcelPath;
        //处理表格922-2.xls
        Excel_2_01.todoExcelFilePath = sourceExcelPath;
        Excel_2_02.todoExcelFilePath = sourceExcelPath;
        Excel_2_03.todoExcelFilePath = sourceExcelPath;
        Excel_2_04.todoExcelFilePath = sourceExcelPath;
        Excel_2_05.todoExcelFilePath = sourceExcelPath;
        Excel_2_06.todoExcelFilePath = sourceExcelPath;
        Excel_2_07.todoExcelFilePath = sourceExcelPath;
        Excel_2_08.todoExcelFilePath = sourceExcelPath;
        Excel_2_09.todoExcelFilePath = sourceExcelPath;
        Excel_2_10.todoExcelFilePath = sourceExcelPath;
        Excel_2_11.todoExcelFilePath = sourceExcelPath;
        Excel_2_12.todoExcelFilePath = sourceExcelPath;
        Excel_2_13.todoExcelFilePath = sourceExcelPath;
        Excel_2_14.todoExcelFilePath = sourceExcelPath;
        Excel_2_15.todoExcelFilePath = sourceExcelPath;
        Excel_2_16.todoExcelFilePath = sourceExcelPath;
        Excel_2_17.todoExcelFilePath = sourceExcelPath;
        Excel_2_18.todoExcelFilePath = sourceExcelPath;
        Excel_2_19.todoExcelFilePath = sourceExcelPath;
        Excel_2_20.todoExcelFilePath = sourceExcelPath;
        Excel_2_21.todoExcelFilePath = sourceExcelPath;
        Excel_2_22.todoExcelFilePath = sourceExcelPath;
        Excel_2_23.todoExcelFilePath = sourceExcelPath;
        Excel_2_24.todoExcelFilePath = sourceExcelPath;
        Excel_2_25.todoExcelFilePath = sourceExcelPath;
        Excel_2_26.todoExcelFilePath = sourceExcelPath;
    }
    //处理表格
    public static void doExcels(){
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
        todo();
    }
}
