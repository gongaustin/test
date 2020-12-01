package com.gongjun.changsha.ZhuSearch;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:10 2020/12/1
 */
public class Start {
    public static String EXCEL_PARENT_PATH = "D:\\长沙项目\\电子年鉴制作\\年鉴和表";
    public void todo(){

        List<File> files = FileUtils.getFiles(EXCEL_PARENT_PATH,new ArrayList<>());
        for(File excel:files){
            Search.getZhuTxt(excel);
        }

    }

    @Test
    public void test(){
        todo();
    }
}
