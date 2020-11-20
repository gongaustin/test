package com.gongjun.changsha.EbookHandler;

import org.junit.Test;

/**
 * @Description:批处理类
 * @Author: GongJun
 * @Date: Created in 16:34 2020/11/18
 */
public class Start {

    private static String SOURCE_EXCEL_PARENT = "D:\\长沙项目\\电子年鉴制作\\年鉴和表";
    private static String TARGET_EXCEL_PARENT = "D:\\长沙项目\\电子年鉴制作\\年鉴拆分表";
    private static String MATERIAL_EXCEL_PARENT = "D:\\长沙项目\\电子年鉴制作\\电子年鉴生成材料";


    //测试方法
    @Test
    public void test() {
        EbookExcelUtils.batSplitSheetsToSingleExcel(SOURCE_EXCEL_PARENT, TARGET_EXCEL_PARENT);
        EbookExcelUtils.batRenameChangshaExcelAndCopyExcel2OtherPath(TARGET_EXCEL_PARENT);
        EbookExcelUtils.batCaptureChangshaExcelToPng(MATERIAL_EXCEL_PARENT);
    }
}
