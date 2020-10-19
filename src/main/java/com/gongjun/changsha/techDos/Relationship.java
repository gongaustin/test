package com.gongjun.changsha.techDos;

import org.apache.commons.io.monitor.FileEntry;
import org.junit.Test;

import java.io.*;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:35 2020/10/19
 */
public class Relationship {
    //对应表文件
    public static String relaFilePath = "D:\\长沙项目\\科技\\对应关系\\对应关系.txt";
    public static void readTxtFile(){
        try {
            File file = new File(relaFilePath);
            if(file.isFile()&&file.exists()){
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine())!= null){
                    System.out.println(line);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void test(){
        readTxtFile();
    }
}
