package com.gongjun.changsha.ExcelToZone;

import com.gongjun.changsha.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private final static String RENAME_AFTER = "D:\\长沙项目\\数据专业汇总\\再次处理";
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


    private static List<String> RELATIONS = Arrays.asList(
            "第1篇,综合",
            "第2篇,企业",
            "第3篇,文化及相关产业",
            "第4篇,科技",
            "第5篇,信息化和电子商务",
            "第6篇,工业",
            "第7篇,建筑业",
            "第8篇,批发和零售业",
            "第9篇,住宿和餐饮业",
            "第10篇,房地产开发经营业",
            "第11篇,服务业"
            );

    private void renameExcelS(){
        Map<String,String> relationMap = new HashedMap<>();
        RELATIONS.forEach(e->{
            String[] ss = e.split(",");
            if(ss.length == 2) relationMap.put(ss[0],ss[0]+" "+ss[1]);
        });


        List<File> files = FileUtils.getFiles(RENAME_PARENT,new ArrayList<>());
        for(File file : files){
            String fileName = file.getName();
            String fileNameWithoutSuffix = fileName.substring(0,fileName.lastIndexOf("."));
            if(relationMap.containsKey(fileNameWithoutSuffix)) fileName = relationMap.get(fileNameWithoutSuffix)+".xlsx";
            //获取地区
            String asPathPatent = file.getParent();
            String[] strings = asPathPatent.split("\\\\");
            String zone = strings[strings.length-1];


            try {
                org.apache.commons.io.FileUtils.copyFile(file,new File(RENAME_AFTER+"\\"+zone+"\\"+fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void test(){
        this.renameExcelS();
    }

}
