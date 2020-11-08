package com.gongjun.changsha.synthesizeDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @Description:新的判断方法
 * @Author: GongJun
 * @Date: Created in 9:09 2020/11/6
 */
@Slf4j
public class newJudgement {
    //全市数据
    private static String cityPath = "D:\\长沙项目\\综合&企业\\全市";
    //地区数据
    private static String zonePath = "D:\\长沙项目\\综合&企业\\地区";

    //表格关键词
    private static String keyword = "922-1";

    //获取文件

    /**
     * @param: [path, list, keyword]
     * @description:获取文件的List
     * @author: GongJun
     * @time: Created in 9:13 2020/11/6
     * @modified:
     * @return: java.util.List<java.io.File>
     **/
    public List<File> getFiles(String path, List<File> list, String keyword) {
        if (list == null) list = new ArrayList<>();
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) getFiles(files[i].getPath(), list, keyword);
                else {
                    if (keyword == null) list.add(files[i]);
                    if (keyword != null && files[i].getName().contains(keyword)) list.add(files[i]);
                }
            }
        } else {
            if (keyword == null) list.add(file);
            if (keyword != null && file.getName().contains(keyword)) list.add(file);
        }
        return list;
    }

    public void judgement() {
        //获取市表格文件
        List<File> cityFiles = this.getFiles(cityPath, new ArrayList<>(), keyword);

        //获取地区表格文件
        List<File> zoneFiles = this.getFiles(zonePath, new ArrayList<>(), keyword);


        //排除的Sheet集合
        List<String> exceptSheets = Arrays.asList(
                //标题
                "1-00",
                //街道（镇）数据
                "1-01", "1-02", "1-07", "1-08", "1-11",
                //未统计数据
                "1-14", "1-15", "1-16", "1-17", "1-18",
                //标题
                "2-00",
                //街道（镇）数据
                "2-04", "2-05", "2-06", "2-12", "2-25", "2-26");
        //市数据集合
        Map<String, List<Double>> cityData = new HashMap<>();

        //获取市数据
        for (File cityFile : cityFiles) {

            //获取市Workbook
            Workbook cityWorkbook = ExcelUtils.getWorkbookFromExcel(new File(cityFile.getAbsolutePath()));
            //获取市Sheet数量
            int citySheetNumber = cityWorkbook.getNumberOfSheets();
            if (citySheetNumber == 0) continue;
            for (int i = 0; i < citySheetNumber; i++) {
                //获取市Sheet
                Sheet citySheet = cityWorkbook.getSheetAt(i);
                //获取市Sheet的SheetName
                String sheetName = citySheet.getSheetName();
                if (exceptSheets.contains(sheetName)) continue;
                //获取市Sheet的行数
                int citySheetRowNumber = citySheet.getPhysicalNumberOfRows();
                //主要指标定义
                for (int j = 0; j < citySheetRowNumber; j++) {
                    Row cityRow = citySheet.getRow(j);
                    if (cityRow == null) continue;
                    Object value = ExcelUtils.getCellValue(cityRow.getCell(0));
                    if (value != null && value instanceof String && StringUtils.isNotBlank((String) value)) {
                        String index = sheetName + "--" + value;
                        index = RegUtils.delAllSpaceForString(index); //index作为Key值
                        //获取cityRow的列数（Cell数）
                        int cityRowCellNumber = cityRow.getPhysicalNumberOfCells();
                        List<Double> rowData = new ArrayList<>();
                        for (int k = 1; k < cityRowCellNumber; k++) {
                            Cell cictyCell = cityRow.getCell(k);
                            if (cictyCell == null) continue;
                            Object cellValue = ExcelUtils.getCellValue(cityRow.getCell(k));
                            if (cellValue == null) continue;
                            if (cellValue instanceof Double) rowData.add((double) cellValue);
                            if (cellValue instanceof String && StringUtils.isNotBlank((String) cellValue)) {
                                try {
                                    rowData.add(Double.valueOf((String) cellValue));
                                } catch (NumberFormatException e) {
                                    log.info("指标:{},数据转换发生错误,需要人为判断,错误信息:{}", index, e.getMessage());
                                } finally {
                                    rowData.add(0d);
                                }
                            }
                        }
                        if (!CollectionUtils.isEmpty(rowData)) cityData.put(index, rowData);
                    }
                }
            }
        }


        //获取区县数据
        for (File zonefile : zoneFiles) {
            Map<String, Object> zoneData = new HashMap<>();
            //获取市Workbook
            Workbook zoneWorkbook = ExcelUtils.getWorkbookFromExcel(new File(zonefile.getAbsolutePath()));
            //获取市Sheet数量
            int zoneSheetNumber = zoneWorkbook.getNumberOfSheets();
            if (zoneSheetNumber == 0) continue;
            for (int i = 0; i < zoneSheetNumber; i++) {
                //获取市Sheet
                Sheet zoneSheet = zoneWorkbook.getSheetAt(i);
                //获取市Sheet的SheetName
                String sheetName = zoneSheet.getSheetName();
                if (exceptSheets.contains(sheetName)) continue;
                //获取市Sheet的行数
                int citySheetRowNumber = zoneSheet.getPhysicalNumberOfRows();
                //主要指标定义
                for (int j = 0; j < citySheetRowNumber; j++) {
                    Row zoneRow = zoneSheet.getRow(j);
                    if (zoneRow == null) continue;
                    Object value = ExcelUtils.getCellValue(zoneRow.getCell(0));
                    if (value != null && value instanceof String && StringUtils.isNotBlank((String) value)) {
                        String index = sheetName + "--" + value;
                        index = RegUtils.delAllSpaceForString(index); //index作为Key值
                        //获取市数据相减
                        if(!cityData.containsKey(index)) System.out.println("全市数据不包含指标:"+index);
                        List<Double> cityRowData = cityData.get(index);
                        if (CollectionUtils.isEmpty(cityRowData)) continue;
                        //获取cityRow的列数（Cell数）
                        int zoneRowCellNumber = zoneRow.getPhysicalNumberOfCells();
                        for (int k = 1; k < cityRowData.size()+1; k++) {
                            Cell zoneCell = zoneRow.getCell(k);
                            if (zoneCell == null) continue;
                            Object cellValue = ExcelUtils.getCellValue(zoneRow.getCell(k));
                            if (cellValue == null) continue;
                            if (cellValue instanceof Double)
                                cityRowData.set(k - 1, (cityRowData.get(k - 1) - ((double) cellValue)));
                            if (cellValue instanceof String) {
                                if (StringUtils.isNotBlank((String) cellValue)) {
                                    try {
                                        cityRowData.set(k - 1, (cityRowData.get(k - 1) - ((double) cellValue)));
                                    } catch (NumberFormatException e) {
                                        log.info("指标:{},数据转换发生错误,需要人为判断,错误信息:{}", index, e.getMessage());
                                    } finally {
                                        cityRowData.set(k - 1, cityRowData.get(k - 1) - 0d);
                                    }
                                } else continue;
                            }
                            //相减后重新put数据
                            cityData.put(index, cityRowData);
                        }
                    }
                }
            }


            //获取地方数据

        }

        cityData = this.sortMapBykeyAsc(cityData);

        //打印市数据

        for(Map.Entry<String, List<Double>> maps : cityData.entrySet()){
            List<Double> list = cityData.get(maps.getKey());
            System.out.println(maps.getKey()+"--"+list.toString());
        }



    }

    //升序
    public Map<String, List<Double>> sortMapBykeyAsc(Map<String, List<Double>> oriMap) {
        Map<String, List<Double>> sortedMap = new LinkedHashMap<>();
        try {
            if (oriMap != null && !oriMap.isEmpty()) {
                List<Map.Entry<String, List<Double>>> entryList = new ArrayList<Map.Entry<String, List<Double>>>(oriMap.entrySet());
                Collections.sort(entryList,
                        new Comparator<Map.Entry<String, List<Double>>>() {
                            public int compare(Map.Entry<String, List<Double>> entry2,
                                               Map.Entry<String, List<Double>> entry1) {
                                int value2 = 0, value1 = 0;
                                try {
                                    value2 = Integer.parseInt(entry2.getKey().substring(1,4));
                                    value1 = Integer.parseInt(entry1.getKey().substring(1,4));
                                } catch (NumberFormatException e) {
                                    value2 = 0;
                                    value1 = 0;
                                }
                                return value1 - value2;
                            }
                        });
                Iterator<Map.Entry<String, List<Double>>> iter = entryList.iterator();
                Map.Entry<String, List<Double>> tmpEntry = null;
                while (iter.hasNext()) {
                    tmpEntry = iter.next();
                    sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
                }
            }
        } catch (Exception e) {
        }
        return sortedMap;
    }



    @Test
    public void test() {
        this.judgement();
    }
}
