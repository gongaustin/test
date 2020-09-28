package com.gongjun.changsha;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.spire.xls.core.spreadsheet.HTMLOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 长沙项目附件IO处理
 * @Author: GongJun
 * @Date: Created in 10:04 2020/9/27
 */
public class changshaXlsHandle {
    /**
     * @param: [srcPathStr, desPathStr]
     * @description: 复制文件的方法
     * @author: GongJun
     * @time: Created in 10:10 2020/9/27
     * @modified:
     * @return: void
     **/
    private static void copyFile(String srcPathStr, String desPathStr) {
        try {
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);
            byte datas[] = new byte[1024 * 8];
            int len = 0;
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param: []
     * @description: 拆分单个表格（含多个Sheet的表格）测试的方法
     * @author: GongJun
     * @time: Created in 10:13 2020/9/27
     * @modified:
     * @return: void
     **/
    @Test
    public void splitSheetsToXls() throws Exception {
        String filePath = "D:\\长沙项目\\9-22发统计局电子表\\922-11.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        int total = workbook.getNumberOfSheets();//3
        workbook.close();
        System.out.println(total);
        for (int i = 0; i < total; i++) {
            // 获取每个Sheet表
            String filePath2 = "D:\\长沙项目\\拆分后\\12第十一篇 服务业\\"+i+".xlsx";
            copyFile(filePath, filePath2);
            File file = new File(filePath2);
            XSSFWorkbook workbook2 = new XSSFWorkbook(new FileInputStream(file));
            int total2 = workbook2.getNumberOfSheets();
            for (int j = total2-1; j >= 0 ; j--) {
                if (i == j) {
                    continue;
                }
                workbook2.removeSheetAt(j);
            }
            String filePath3 = "D:\\长沙项目\\拆分后\\12第十一篇 服务业\\"+i+".xlsx";
            FileOutputStream fout = new FileOutputStream(filePath3);
            workbook2.write(fout);
            workbook2.close();
            fout.close();
            //删除文件
            //file.delete();
        }
        System.out.println("处理完成");
    }

    /**
     * @param: [excelPath]
     * @description: 获取表格的标题
     * @author: GongJun
     * @time: Created in 10:58 2020/9/27
     * @modified:
     * @return: java.lang.String
     **/
    public String achieveChangshaXlsTitle(String excelPath){
        String title = new String("");
        org.apache.poi.ss.usermodel.Workbook workbook= null;
        try {
            workbook = WorkbookFactory.create(new File(excelPath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        Sheet sheet=workbook.getSheetAt(0);
            //获取第一行
            Row titlerow=sheet.getRow(0);
            Cell cell=titlerow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            title = this.achieveMergedRegionValue(sheet,cell);
            if(StringUtils.isBlank(title)) title = cell.getStringCellValue();
        return title;
    }



    /**
     * @param: [sheet, cell]
     * @description: 获取合并表格的内容
     * @author: GongJun
     * @time: Created in 10:37 2020/9/27
     * @modified:
     * @return: java.lang.String
     **/
    public static String achieveMergedRegionValue(Sheet sheet, Cell cell) {
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
     * @param: [path]
     * @param: [newPath] 生成文件保存的新路径
     * @description: 利用递归方法批量重命名表格名称并写入另一个文件夹
     * @author: GongJun
     * @time: Created in 11:00 2020/9/27
     * @modified:
     * @return: void
     **/
    public void renameChangshaExcel(String path){
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
                        this.renameChangshaExcel(file2.getAbsolutePath());
                    } else {

                        String fileName = file2.getName();//文件名
                        //过滤不处理的文件
                        boolean flag = StringUtils.endsWith(fileName,"xlsx") && !StringUtils.startsWith(fileName,"0.xlsx");
                        if(flag){
                            String filePath = file2.getAbsolutePath();
                            //新文件夹
                            String newPath = filePath.replaceAll("长沙项目","长沙项目_处理后");
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

    /**
     * @param: [path, name]
     * @description: 表格截图的方法
     * @author: GongJun
     * @time: Created in 11:16 2020/9/27
     * @modified:
     * @return: void
     **/
    public void excelToPng(String path,String name){
        //加载Excel文档
        Workbook workbook = new Workbook();
        workbook.loadFromFile(path+"\\"+name);
        Worksheet ws = workbook.getActiveSheet();
        BufferedImage bufferedImage = ws.toImage(2, 1, ws.getLastRow(), ws.getLastColumn());
        String picName = name.substring(0, name.lastIndexOf("."));
        try {
            ImageIO.write(bufferedImage, "PNG", new File(path+"\\"+picName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param: [path, name]
     * @description: excel转为html
     * @author: GongJun
     * @time: Created in 14:25 2020/9/27
     * @modified:
     * @return: void
     **/
    public void excelToHtml(String path,String name){

        //加载Excel文档
        Workbook workbook = new Workbook();
        workbook.loadFromFile(path+"\\"+name);
        Worksheet ws = workbook.getActiveSheet();
        int a = ws.getLastColumn();
        for (int i = 1; i <= a; i++) {
            ws.autoFitColumn(i);
        }

        String htmlName = name.substring(0, name.lastIndexOf("."));
        //操作html，去掉水印

        ws.saveToHtml(path+"\\"+htmlName+".html",HTMLOptions.Default);

    }



    /**
     * @param: [path] 表格初始路径
     * @description: 利用递归方法批量对表格数据进行截图
     * @author: GongJun
     * @time: Created in 11:19 2020/9/27
     * @modified:
     * @return: void
     **/
    public void captureChangshaExcelToPng(String path){
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
                        this.captureChangshaExcelToPng(file2.getAbsolutePath());
                    } else {
                        String fileName = file2.getName();     //获取文件的文件名
                        String directory = file2.getParent();  //获取文件的路径
                        if(fileName.endsWith("xlsx")&&!fileName.startsWith("0.xlsx")){
                                System.out.println(fileName);
                                this.excelToPng(directory,fileName);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }

    }
    
    /**
     * @param: []
     * @description: 拆分表格的测试方法
     * @author: GongJun
     * @time: Created in 11:37 2020/9/27
     * @modified: 
     * @return: void
     **/
    @Test
    public void splitExcels() throws Exception{
        this.splitSheetsToXls();
    }
    
    
    /**
     * @param: []
     * @description: 批量重命名表格的测试方法
     * @author: GongJun
     * @time: Created in 11:38 2020/9/27
     * @modified: 
     * @return: void
     **/
    @Test
    public void renameExcel() throws Exception{
        this.renameChangshaExcel("");
    }
    
    /**
     * @param: []
     * @description: 批量表格截图的测试方法
     * @author: GongJun
     * @time: Created in 11:40 2020/9/27
     * @modified: 
     * @return: void
     **/
    @Test
    public void xlsToPng(){
        this.captureChangshaExcelToPng("");
    }
    
    /**
     * @param: []
     * @description: 表格转html的测试方法
     * @author: GongJun
     * @time: Created in 14:36 2020/9/27
     * @modified: 
     * @return: void
     **/
    @Test
    public void xlsToHtml(){

        this.excelToHtml("D:","1.xlsx");
        
    }


    /**
     * @param: []
     * @description: 用用测试方法
     * @author: GongJun
     * @time: Created in 17:27 2020/9/27
     * @modified: 
     * @return: void
     **/
    @Test
    public void test(){
        this.excelToPng("D:","1.xlsx");
    }






}
