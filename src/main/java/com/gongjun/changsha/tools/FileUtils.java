package com.gongjun.changsha.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  文件工具类
 * @Author: GongJun
 * @Date: Created in 14:12 2020/10/20
 */
public class FileUtils {
    public static void copyFile(String strOldpath,String strNewPath)
    {
        try
        {
            File fOldFile = new File(strOldpath);
            if (fOldFile.exists())
            {
                int bytesum = 0;
                int byteread = 0;
                InputStream inputStream = new FileInputStream(fOldFile);
                FileOutputStream fileOutputStream = new FileOutputStream(strNewPath);
                byte[] buffer = new byte[1444];
                while ( (byteread = inputStream.read(buffer)) != -1)
                {
                    bytesum += byteread; //这一行是记录文件大小的，可以删去
                    fileOutputStream.write(buffer, 0, byteread);//三个参数，第一个参数是写的内容，
                    //第二个参数是从什么地方开始写，第三个参数是需要写的大小
                }
                inputStream.close();
                fileOutputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("复制单个文件出错");
            e.printStackTrace();
        }
    }

    /**
     * @param: [path, list]
     * @description: 递归获取该目录的所有文件
     * @author: GongJun
     * @time: Created in 10:55 2020/10/26
     * @modified: 
     * @return: java.util.List<java.io.File>
     **/
    public static List<File> getFiles(String path, List<File> list) {
        if(list == null) list = new ArrayList<>();
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) getFiles(files[i].getPath(),list);
                else list.add(files[i]);
            }
        }
        else list.add(file);
        return list;
    }
}
