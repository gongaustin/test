package com.gongjun.changsha.FangDiChanDos;

import com.gongjun.changsha.tools.ExcelUtils;
import com.gongjun.changsha.tools.FileUtils;
import com.gongjun.changsha.tools.RegUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:54 2020/11/12
 */
@Slf4j
public class QtyDos {

    public static String indexTxtPath = "";


    List<String> exceptSheets = Arrays.asList(
            //标题
            "10-00");
    //读取问题指标并存储
    public List<String> getIndexsFromTxt(){
        List<String> indexs = new ArrayList<>();
        try {
            String encoding="UTF-8";
            File file=new File(indexTxtPath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String index = RegUtils.delAllSpaceForString(lineTxt);
                    if(!indexs.contains(index)) indexs.add(index);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return indexs;
    }

    //读取数据
    public Map<String,Double> readZoneData(){
//        List<String> indexs = this.getIndexsFromTxt();
        Map<String,List<Integer>> indexsMap = this.getQtyMap();
        //获取表格的文件集合
        List<File> zoneFiles = FileUtils.getFiles( "D:\\长沙项目\\房地产\\已处理",new ArrayList<>());

        Map<String,Double> qtyDataMap = new HashMap<>();


        for (File zoneExcelFile : zoneFiles) {
            if(!zoneExcelFile.getAbsolutePath().endsWith(".XLS")) continue;
            //获取区域
            String zone = zoneExcelFile.getAbsolutePath().substring(16, 18);
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
                String bigCellVaule = "";
                for (int j = 0; j < zoneSheetRowNumber; j++) {
                    //获取row
                    Row zoneRow = zoneSheet.getRow(j);

                    if (zoneRow == null) continue;
                    //获取row的Cell数
                    int cellNumberOfRow = zoneRow.getPhysicalNumberOfCells();
                    //获取指标名称
                    String zoneIndex = null;
                    try {
                        String value = zoneRow.getCell(0).getStringCellValue();
                        if (!value.startsWith(" ")) {
                            bigCellVaule = value;
                        } else value = bigCellVaule + "--" + value;
                        zoneIndex = RegUtils.delAllSpaceForString(sheetName + "--" + value);
                    } catch (Exception e) {
//                        log.info("区县数据转换错误:{},位置:{}--{}",e.getMessage(),zoneExcelFile,sheetName);
                        continue;
                    }
                    //判断是否为空或者为市指标的存在的，否则不予处理
                    if (StringUtils.isBlank(zoneIndex) || !indexsMap.containsKey(zoneIndex)) continue;
                    List<Integer> indexs = indexsMap.get(zoneIndex);
                    if(CollectionUtils.isEmpty(indexs))continue;
                    for (int k = 1; k < cellNumberOfRow; k++) {
                        if(!indexs.contains(k-1)) continue;
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
                        }
//                        if(zoneCellValue == 0d)
                        qtyDataMap.put("指标["+zoneIndex+"]第"+(k+1)+"项,["+zone+"]数据为:",zoneCellValue);
                    }
                }
            }
        }
        return qtyDataMap;
    }




    //升序
    private Map<String, Double> sortByKey(Map<String, Double> map) {
        Map<String, Double> result = new LinkedHashMap<>(map.size());
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }


    public Map<String,List<Integer>> getQtyMap(){

        //综合
        Map<String,List<Integer>> sMap = new newJudegment().getQtyMap();
        return sMap;
    }


    @Test
    public void test(){
        Map<String,Double> map = this.sortByKey(this.readZoneData());
        Double sum = 0d;
        String index = null;
        for (String key:map.keySet()){
            if(index != null && !key.startsWith(index)){
                System.out.println(index+",[市县区]总和为:"+sum);
                System.out.println("************************************************");
                sum = 0d;
            }
            System.out.println(key+map.get(key));
            String[] keys = key.split(",");
            index = keys[0];
            if(key.startsWith(index)) sum +=map.get(key);
        }

    }
}
