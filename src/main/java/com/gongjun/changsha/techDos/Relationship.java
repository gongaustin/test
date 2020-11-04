package com.gongjun.changsha.techDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理科技对应关系的类
 * @Author: GongJun
 * @Date: Created in 16:35 2020/10/19
 */
public class Relationship {
    //对应表文件
    public static String relaTxtFilePath = "D:\\长沙项目\\科技\\对应关系\\对应关系.txt";

    public static String relaExcelFilePath = "D:\\长沙项目\\科技\\对应关系\\对应关系.xlsx";

    public static Map<String, String> readTxtRelationshipFile() {
        Map<String, String> relaMaps = new HashMap<>();
        try {
            File file = new File(relaTxtFilePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains(",")) {
                        String[] relas = line.split(",");
                        if (relas.length == 2) relaMaps.put(relas[0], relas[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relaMaps;
    }


    public static Map<String, String> readExcelRelationshipFile() {
        Map<String, String> relaMaps = new HashMap<>();
        Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(relaExcelFilePath));
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            String key = row.getCell(0) == null ? null : row.getCell(0).getStringCellValue();
            String value = row.getCell(1) == null ? null : row.getCell(1).getStringCellValue();
            if (key == null || value == null) continue;
            relaMaps.put(RegUtils.delAllSpaceForString(key), RegUtils.delAllSpaceForString(value));
        }
        return relaMaps;
    }

    @Test
    public void test() {
        Map<String, String> map = readExcelRelationshipFile();
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
        }
        map.containsKey("");
        System.out.println(map.get("大型企业"));
    }
}
