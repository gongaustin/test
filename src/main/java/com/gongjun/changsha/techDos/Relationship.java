package com.gongjun.changsha.techDos;

import org.apache.commons.io.monitor.FileEntry;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理科技对应关系的类
 * @Author: GongJun
 * @Date: Created in 16:35 2020/10/19
 */
public class Relationship {
    //对应表文件
    public static String relaFilePath = "D:\\长沙项目\\科技\\对应关系\\对应关系.txt";
    public static Map<String,String> readTxtFile(){
        Map<String,String> relaMaps = new HashMap<>();
        try {
            File file = new File(relaFilePath);
            if(file.isFile()&&file.exists()){
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine())!= null){
                    if(line.contains(",")){
                        String[] relas = line.split(",");
                        if(relas.length==2) relaMaps.put(relas[0],relas[1]);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return relaMaps;
    }

    @Test
    public void test(){
        Map<String,String> map = readTxtFile();
        for(String key:map.keySet()){
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }
    }
}
