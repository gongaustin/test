package com.gongjun.changsha.KeJiDos;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 11:49 2020/10/20
 */
public class CommonSet {
    public static void dataExcelPathSet(String path) {
        if (path == null) return;
        Excel_4_01.dataExcelPath = path;
        Excel_4_02.dataExcelPath = path;
        Excel_4_03.dataExcelPath = path;
        Excel_4_04.dataExcelPath = path;
        Excel_4_05.dataExcelPath = path;
        Excel_4_06.dataExcelPath = path;
        Excel_4_07.dataExcelPath = path;
    }

    public static void standardExcelPathSet(String path) {
        if (path == null) return;
        Excel_4_01.standardExcelPath = path;
        Excel_4_02.standardExcelPath = path;
        Excel_4_03.standardExcelPath = path;
        Excel_4_04.standardExcelPath = path;
        Excel_4_05.standardExcelPath = path;
        Excel_4_06.standardExcelPath = path;
        Excel_4_07.standardExcelPath = path;
    }
}
