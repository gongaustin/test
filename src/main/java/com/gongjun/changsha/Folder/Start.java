package com.gongjun.changsha.Folder;

import com.gongjun.changsha.tools.FileUtils;
import com.gongjun.changsha.tools.PoiUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 11:42 2020/12/2
 */
public class Start {
    public static final String PARENT_FOLDER = "D:\\长沙项目\\电子年鉴制作\\拆分表格不含图片\\"+"11第十一篇 服务业";
    public void todo(){
        List<File> files = FileUtils.getFiles(PARENT_FOLDER,new ArrayList<>());
        for (File file:files){
            String fileName = file.getName();
            String fileNameWithoutSuffix = fileName.substring(0,fileName.lastIndexOf("."));
            System.out.println("..."+fileNameWithoutSuffix);
        }
    }

    @Test
    public void test(){
        todo();
    }
}
