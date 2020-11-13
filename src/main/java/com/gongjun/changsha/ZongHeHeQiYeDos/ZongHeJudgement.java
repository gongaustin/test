package com.gongjun.changsha.ZongHeHeQiYeDos;

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
 * @Description:新的判断方法(综合)
 * @Author: GongJun
 * @Date: Created in 9:09 2020/11/6
 */
@Slf4j
public class ZongHeJudgement {
    public static Map<String, List<Map<String, Double>>> markData = new HashMap<>();
    //全市数据
    private static String cityPath = "D:\\长沙项目\\综合&企业\\全市";
    //地区数据
    private static String zonePath = "D:\\长沙项目\\综合&企业待处理\\已处理";
    //表格关键词
    private static String keyword = "922-1";

    //获取文件
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

    public Map<String, List<Double>> getCityData() {
        //获取市表格文件
        List<File> cityFiles = this.getFiles(cityPath, new ArrayList<>(), keyword);


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
                            Object cellValue = null;
                            if (cictyCell == null) cellValue = 0d;
                            cellValue = ExcelUtils.getCellValue(cityRow.getCell(k));
                            if (cellValue instanceof Double) rowData.add((double) cellValue);
                            if (cellValue instanceof String) {
                                try {
                                    rowData.add(Double.valueOf((String) cellValue));
                                } catch (NumberFormatException e) {
                                    rowData.add(0d);
                                }
                            }
                        }
                        //添加到Map
                        if (!CollectionUtils.isEmpty(rowData) && index != null) cityData.put(index, rowData);
                    }
                }
            }
        }


        cityData = this.sortMapBykeyAsc(cityData);

        //打印市数据

        return cityData;


    }

    /**
     * @param: [indexs]  indexs:指标名称集合（区县的数据需要和市的数据对的上）
     * @description: 获取区县数据的总和
     * @author: GongJun
     * @time: Created in 9:47 2020/11/8
     * @modified:
     * @return: void
     **/
    //获取区县的数据
    public Map<String, List<Double>> getZoneSumData() {

        Map<String, List<Double>> cityData = this.getCityData();
        //获取指标的集合
        List<String> indexs = this.getIndexsFromCityData(cityData);
        //获取区县的表格文件
        List<File> zoneFiles = this.getFiles(zonePath, new ArrayList<>(), keyword);

        //定义存储区县数据加总的MAP
        Map<String, List<Double>> zoneSumData = new HashMap<>();

        for (File zoneExcelFile : zoneFiles) {
            //获取区域
            String zone = zoneExcelFile.getAbsolutePath().substring(17, 20);
            //获取Workbool
            Workbook zoneWorkbook = ExcelUtils.getWorkbookFromExcel(zoneExcelFile);
            //获取Sheet数目
            int zoneShertNumber = zoneWorkbook.getNumberOfSheets();
            //遍历Workbook获取Sheet
            for (int i = 0; i < zoneShertNumber; i++) {
                //获取Sheet
                Sheet zoneSheet = zoneWorkbook.getSheetAt(i);
                //排除规则
                String sheetName = zoneSheet.getSheetName();
                if (exceptSheets.contains(sheetName)) continue;
                //获取Sheet的行数
                int zoneSheetRowNumber = zoneSheet.getPhysicalNumberOfRows();
                //遍历SHeet的行
                for (int j = 0; j < zoneSheetRowNumber; j++) {
                    //获取row
                    Row zoneRow = zoneSheet.getRow(j);

                    if (zoneRow == null) continue;
                    //获取row的Cell数
                    int cellNumberOfRow = zoneRow.getPhysicalNumberOfCells();
                    //获取指标名称
                    String zoneIndex = null;
                    try {
                        zoneIndex = RegUtils.delAllSpaceForString(sheetName + "--" + zoneRow.getCell(0).getStringCellValue());
                    } catch (Exception e) {
//                        log.info("区县数据转换错误:{},位置:{}--{}",e.getMessage(),zoneExcelFile,sheetName);
                        continue;
                    }
                    //判断是否为空或者为市指标的存在的，否则不予处理
                    if (StringUtils.isBlank(zoneIndex) || !indexs.contains(zoneIndex)) continue;
                    //从区县获取指标否则添加
                    if (zoneSumData.containsKey(zoneIndex)) {
                        //获取数据
                        List<Double> sumData = zoneSumData.get(zoneIndex);
                        for (int k = 1; k < cellNumberOfRow; k++) {
                            Cell zoneCell = zoneRow.getCell(k);
                            Double zoneCellValue = 0d;
                            Object value = 0d;
                            try {
                                value = ExcelUtils.getCellValue(zoneCell);
                            } catch (Exception e) {
                                log.info("Cell值获取错误,位置:{},指标名称:{},第{}列数据", sheetName, zoneIndex, k + 1);
                                value = 0d;
                            }
                            if (value instanceof Double) zoneCellValue = (Double) value;
                            if (value instanceof String) {
                                if (StringUtils.isNotBlank((String) value)) try {
                                    zoneCellValue = Double.valueOf((String) value);
                                } catch (Exception e) {
                                    log.info("数据转换错误,原始值:{}", value);
                                    zoneCellValue = 0d;
                                }
                                else zoneCellValue = 0d;
                            }
                            try {
                                sumData.set(k - 1, sumData.get(k - 1) + zoneCellValue);
                            } catch (Exception e) {
                                sumData.add(k - 1, 0d);
                            }
                        }
                        zoneSumData.put(zoneIndex, sumData);
                    } else {
                        List<Double> zoneRowData = new ArrayList<>();
                        Map<String, Double> markRowMap = new HashMap<>();
                        for (int k = 1; k < cellNumberOfRow; k++) {
                            //获取Cell
                            Cell zoneCell = zoneRow.getCell(k);
                            Double zoneCellValue = 0d;
                            Object value = 0d;
                            try {
                                value = ExcelUtils.getCellValue(zoneCell);
                            } catch (Exception e) {
                                log.info("Cell值获取错误,位置:{},指标名称:{},第{}列数据", sheetName, zoneIndex, k + 1);
                                value = 0d;
                            }
                            if (value instanceof Double) zoneCellValue = (Double) value;
                            if (value instanceof String) {
                                if (StringUtils.isNotBlank((String) value)) {
                                    try {
                                        zoneCellValue = Double.valueOf((String) value);
                                    } catch (Exception e) {
                                        log.info("数据转换错误,原始值:{}", value);
                                        zoneCellValue = 0d;
                                    }
                                } else zoneCellValue = 0d;
                            }
                            zoneRowData.add(zoneCellValue);
                        }
                        if (!CollectionUtils.isEmpty(zoneRowData)) zoneSumData.put(zoneIndex, zoneRowData);
                    }
                }
            }
        }
        return zoneSumData;
    }


    public List<String> getIndexsFromCityData(Map<String, List<Double>> data) {
        if (data == null) return null;
        List<String> indexs = new ArrayList<>();
        for (String key : data.keySet()) {
            if (!indexs.contains(key)) indexs.add(key);
        }
        return indexs;
    }


    //升序
    public Map<String, List<Double>> sortMapBykeyAsc(Map<String, List<Double>> oriMap) {
        Map<String, List<Double>> sortedMap = new LinkedHashMap<>();
        try {
            if (oriMap != null && !oriMap.isEmpty()) {
                List<Map.Entry<String, List<Double>>> entryList = new ArrayList<Map.Entry<String, List<Double>>>(oriMap.entrySet());
                Collections.sort(entryList, new Comparator<Map.Entry<String, List<Double>>>() {
                    public int compare(Map.Entry<String, List<Double>> entry2, Map.Entry<String, List<Double>> entry1) {
                        int value2 = 0, value1 = 0;
                        try {
                            value2 = Integer.parseInt(entry2.getKey().substring(1, 4));
                            value1 = Integer.parseInt(entry1.getKey().substring(1, 4));
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

        Map<String, List<Double>> cityData = this.sortMapBykeyAsc(this.getCityData());


        Map<String, List<Double>> zoneSumData = this.sortMapBykeyAsc(this.getZoneSumData());

        List<String> qtys = new ArrayList<>();

//
        //开始比较
        for (String key : cityData.keySet()) {
            List<Double> cityRowData = cityData.get(key);

            List<Double> zoneSumRowData = zoneSumData.get(key);
            if (cityRowData == null || zoneSumRowData == null) continue;
            if (cityRowData.containsAll(zoneSumRowData)) continue;
            int number = cityRowData.size();
            for (int i = 0; i < number; i++) {
                //市数据
                double cityCellData = cityRowData.get(i);

                double zoneCellSumData = 0;
                try {
                    zoneCellSumData = zoneSumRowData.get(i);
                } catch (Exception e) {
                    zoneCellSumData = 0;
                }

                if (Math.abs(cityCellData - zoneCellSumData) > 5) {
                    if(Math.abs(cityCellData*2-zoneCellSumData)==0d){

                    }else{
                        log.info("指标[{}]--第{}项数据不一致,全市数据:{},区县加总:{}", key,i+1, cityCellData, zoneCellSumData);
                        if(!qtys.contains(key)) qtys.add(key);
                    }
                }

            }


        }
    }

    public Map<String,List<Integer>> getQtyMap(){
        Map<String,List<Integer>> qtyMap = new HashMap<>();

        Map<String, List<Double>> cityData = this.sortMapBykeyAsc(this.getCityData());
        Map<String, List<Double>> zoneSumData = this.sortMapBykeyAsc(this.getZoneSumData());
//
        //开始比较
        for (String key : cityData.keySet()) {
            List<Double> cityRowData = cityData.get(key);

            List<Double> zoneSumRowData = zoneSumData.get(key);
            if (cityRowData == null || zoneSumRowData == null) continue;
            if (cityRowData.containsAll(zoneSumRowData)) continue;
            int number = cityRowData.size();
            List<Integer> qtyIndex = null;
            for (int i = 0; i < number; i++) {
                //市数据
                double cityCellData = cityRowData.get(i);
                double zoneCellSumData = 0;
                try {
                    zoneCellSumData = zoneSumRowData.get(i);
                } catch (Exception e) {
                    zoneCellSumData = 0;
                }

                if (Math.abs(cityCellData - zoneCellSumData) > 5) {
                    if(Math.abs(cityCellData*2-zoneCellSumData)==0d){
                    }else{
                        qtyIndex = qtyMap.get(key);
                        if(qtyIndex == null) qtyIndex = new ArrayList<>();
                        qtyIndex.add(i);
                        qtyMap.put(key,qtyIndex);
                    }
                }
            }
        }
        return qtyMap;
    }
}
