package com.gongjun.changsha.ExcelToZone;

import com.gongjun.changsha.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:分专业的数据拷贝到地区文件夹
 * @Author: GongJun
 * @Date: Created in 16:44 2020/11/25
 */
@Slf4j
public class Start {
    private final static String PER_PARENT = "D:\\长沙项目\\数据专业汇总\\汇总前";
    private final static String AFTER_PARENT = "D:\\长沙项目\\数据专业汇总\\汇总后";
    private final static String RENAME_PARENT = "D:\\长沙项目\\数据专业汇总\\重命名";
    private final static List<String> ZONES = Arrays.asList(
            "芙蓉区",
            "开福区",
            "浏阳市",
            "宁乡市",
            "天心区",
            "望城区",
            "雨花区",
            "岳麓区",
            "长沙县",
            "岳麓区（不含高新）"
            );
    public void todo(){
        //创建地区文件夹
        this.createZoneParent();
        List<File> excelLists = FileUtils.getFiles(PER_PARENT,new ArrayList<>());
        excelLists.forEach(e->{
            //获取地区
            String asPathPatent = e.getParent();
            String[] strings = asPathPatent.split("\\\\");
            String zone = strings[strings.length-1];
            try {
                org.apache.commons.io.FileUtils.copyFile(e,new File(AFTER_PARENT+"\\"+zone+"\\"+e.getName()));
            } catch (IOException ioException) {
                log.info("文件拷贝出现错误，错误信息：{}",ioException.getMessage());
            }
        });

    }


    private void createZoneParent(){
        ZONES.forEach(e->{
            String zoneParent = AFTER_PARENT+"\\"+e;
            File file = new File(zoneParent);
            if(!file.exists()) file.mkdir();
        });

    }

    public void rename (){
        List<File> files = FileUtils.getFiles(AFTER_PARENT,new ArrayList<>());
        for (File file:files){
            String asPathPatent = file.getParent();
            String[] strings = asPathPatent.split("\\\\");
            String zone = strings[strings.length-1];


            String fileName = file.getName();
            String fileNameWithoutSuffix = fileName.substring(0,fileName.lastIndexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            String[] ss = fileNameWithoutSuffix.split("-");
            if(ss.length!=2) continue;

            //获取阿拉伯数字
            String number = ss[1];
            if(number.contains("430")) number = number.substring(0,1);
            try {
                org.apache.commons.io.FileUtils.copyFile(file,new File(RENAME_PARENT+"\\"+zone+"\\第"+number+"篇"+suffix));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Test
    public void test(){
        this.rename();
    }

}
