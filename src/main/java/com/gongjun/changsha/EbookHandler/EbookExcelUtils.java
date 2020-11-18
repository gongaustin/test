package com.gongjun.changsha.EbookHandler;

import com.gongjun.changsha.tools.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Description:电子书工具类
 * @Author: GongJun
 * @Date: Created in 13:37 2020/11/18
 */
@Slf4j
public class EbookExcelUtils {
    final static List<String> RELATION_LIST = Arrays.asList("01第一篇 综合,922-1", "02第二篇 企业,922-2", "03第三篇 文化及相关产业,922-3", "04第四篇 科技,922-4", "05第五篇 信息化和电子商务,922-5", "06第六篇 工业,922-6", "07第七篇 建筑业,922-7", "08第八篇 批发和零售业,922-8", "09第九篇 住宿和餐饮业,922-9", "10第十篇 房地产开发经营业,922-10", "11第十一篇 服务业,922-11");
    /**
     * @param: [sheet, cell]
     * @description:读取合并表格的字符串
     * @author: GongJun
     * @time: Created in 10:37 2020/9/27
     * @modified:
     * @return: java.lang.String
     **/
    private static String achieveMergedRegionValue(Sheet sheet, Cell cell) {
        // 获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        // 便利合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            // 获得合并单元格
            CellRangeAddress ca = sheet.getMergedRegion(i);
            // 获得合并单元格的起始行, 结束行, 起始列, 结束列
            int firstC = ca.getFirstColumn();
            int firstR = ca.getFirstRow();

            if (cell.getColumnIndex() == firstC && cell.getRowIndex() == firstR) {
                return cell.getStringCellValue();
            }

        }
        return "";
    }


    /**
     * @param: [excelPath]
     * @description:获取长沙表格的标题
     * @author: GongJun
     * @time: Created in 10:58 2020/9/27
     * @modified:
     * @return: java.lang.String
     **/
    public static String achieveChangshaExcelTitle(String excelPath) {
        String title = new String("");
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File(excelPath));
        } catch (IOException e) {
            log.info("错误信息:{}", e.getMessage());
        }
        Sheet sheet = workbook.getSheetAt(0);
        //获取第一行
        Row titlerow = sheet.getRow(0);
        Cell cell = titlerow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        title = achieveMergedRegionValue(sheet, cell);
        if (StringUtils.isBlank(title)) title = cell.getStringCellValue();
        return title;
    }

    /**
     * @param: [path, name]
     * @description:表格截图的方法
     * @author: GongJun
     * @time: Created in 11:16 2020/9/27
     * @modified:
     * @return: void
     **/
    private static void excelToPng(String path, String name) {
        //加载Excel文档
        com.spire.xls.Workbook workbook = new com.spire.xls.Workbook();
        workbook.loadFromFile(path + "\\" + name);
        com.spire.xls.Worksheet ws = workbook.getActiveSheet();
        BufferedImage bufferedImage = ws.toImage(2, 1, ws.getLastRow(), ws.getLastColumn());
        String picName = name.substring(0, name.lastIndexOf("."));
        try {
            ImageIO.write(bufferedImage, "PNG", new File(path + "\\" + picName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param: [path]表格初始路径
     * @description:利用递归方法批量对表格数据进行截图
     * @author: GongJun
     * @time: Created in 11:19 2020/9/27
     * @modified:
     * @return: void
     **/
    public static void batCaptureChangshaExcelToPng(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        batCaptureChangshaExcelToPng(file2.getAbsolutePath());
                    } else {
                        String fileName = file2.getName();     //获取文件的文件名
                        String directory = file2.getParent();  //获取文件的路径
                        if (fileName.endsWith("xlsx") && !fileName.startsWith("0.xlsx")) {
                            System.out.println(fileName);
                            excelToPng(directory, fileName);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * @param: [sourceExcelPath, targerExcelParentPath]
     * @description:多个Sheet的表格拆分为单个Sheet的表格
     * @author: GongJun
     * @time: Created in 15:39 2020/11/18
     * @modified:
     * @return: void
     **/
    public static void splitSheetsToSingleExcel(String sourceExcelPath, String targerExcelParentPath) {
        Map<String, String> relationMap = new HashMap<>();
        RELATION_LIST.forEach(e -> {
            String[] strings = e.split(",");
            relationMap.put(strings[1], strings[0]);
        });
        File excelFile = new File(sourceExcelPath);
        String fileName = excelFile.getName();
        String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
        String indexName = relationMap.get(fileNameWithoutSuffix);
        // 第一步，创建一个webbook，对应一个Excel文件
        int total = 0;//3
        try {
            org.apache.poi.ss.usermodel.Workbook workbook = ExcelUtils.getWorkbookFromExcel(new File(sourceExcelPath));
            total = workbook.getNumberOfSheets();
            workbook.close();
        } catch (IOException e) {
            log.info("错误信息:{}", e.getMessage());
        }
        for (int i = 0; i < total; i++) {
            // 获取每个Sheet表
            String filePath2 = targerExcelParentPath + "\\" + indexName + "\\" + i + ".xlsx";
            Workbook workbook2 = null;
            try {
                FileUtils.copyFile(new File(sourceExcelPath), new File(filePath2));
            } catch (IOException e) {
                log.info("复制文件出错，错误信息:{}", e.getMessage());
            }
            File file = new File(filePath2);
            workbook2 = ExcelUtils.getWorkbookFromExcel(file);
            int total2 = workbook2.getNumberOfSheets();
            for (int j = total2 - 1; j >= 0; j--) {
                if (i == j) {
                    continue;
                }
                workbook2.removeSheetAt(j);
            }
            String filePath3 = targerExcelParentPath + "\\" + indexName + "\\" + i + ".xlsx";
            try {
                FileOutputStream fout = new FileOutputStream(filePath3);
                workbook2.write(fout);
                workbook2.close();
                fout.close();
            } catch (IOException e) {
                log.info("错误信息:{}", e.getMessage());
            }
            //删除文件
            //file.delete();
        }
        log.info("文件[{}]拆分完成", sourceExcelPath);
    }



    /**
     * @param: [path]
     * @param: [newPath] 生成文件保存的新路径
     * @description:利用递归方法批量重命名表格名称并写入另一个文件夹
     * @author: GongJun
     * @time: Created in 11:00 2020/9/27
     * @modified:
     * @return: void
     **/
    public void renameChangshaExcelAndCopyExcel2OtherPath(String path) {
        String sourceStr = "长沙项目";
        String targetStr = "长沙项目_处理后";
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        this.renameChangshaExcelAndCopyExcel2OtherPath(file2.getAbsolutePath());
                    } else {

                        String fileName = file2.getName();//文件名
                        //过滤不处理的文件
                        boolean flag = StringUtils.endsWith(fileName, "xlsx") && !StringUtils.startsWith(fileName, "0.xlsx");
                        if (flag) {
                            String filePath = file2.getAbsolutePath();
                            //新文件夹
                            String newPath = filePath.replaceAll(sourceStr, targetStr);
                            try {
                                FileUtils.copyFile(file2, new File(newPath));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }




}
