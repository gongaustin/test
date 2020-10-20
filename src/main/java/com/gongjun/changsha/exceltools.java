package com.gongjun.changsha;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @Description: 处理Excel工具类
 * @Author: GongJun
 * @Date: Created in 9:04 2020/9/23
 */
public class exceltools {


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

    public static String getMergedRegionValue(Sheet sheet, Cell cell) {
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
                return getCellContent(cell);
            }

        }
        return "";
    }


    //获取标题

    private static String getCellContent(Cell cell) {
        return cell.getStringCellValue();
    }


    //表格sheet处理

    //拆分sheet为单独的表格
    @Test
    public void splitExcelSheetsToSingle() throws Exception {
        String filePath = "D:\\长沙项目\\9-22发统计局电子表\\922-11.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        int total = workbook.getNumberOfSheets();//3
        workbook.close();
        System.out.println(total);
        for (int i = 0; i < total; i++) {// 获取每个Sheet表
            String filePath2 = "D:\\长沙项目\\拆分后\\12第十一篇 服务业\\" + i + ".xlsx";
            copyFile(filePath, filePath2);
            File file = new File(filePath2);
            XSSFWorkbook workbook2 = new XSSFWorkbook(new FileInputStream(file));
            int total2 = workbook2.getNumberOfSheets();


            for (int j = total2 - 1; j >= 0; j--) {
                if (i == j) {
                    continue;
                }
                workbook2.removeSheetAt(j);
            }
            String filePath3 = "D:\\长沙项目\\拆分后\\12第十一篇 服务业\\" + i + ".xlsx";
            FileOutputStream fout = new FileOutputStream(filePath3);
            workbook2.write(fout);
            workbook2.close();
            fout.close();
//            file.delete();//删除文件
        }
        System.out.println("ok");
    }

    public String getTitleName(String excelPath) {
        String title = null;
        try {

            org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = workbook.getSheetAt(0);
            //获取第一行
            Row titlerow = sheet.getRow(0);
            //有多少列
            int cellNum = titlerow.getLastCellNum();
            Cell cell = titlerow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            title = getMergedRegionValue(sheet, cell);
            if (title == null || title.length() == 0) {
                title = cell.getStringCellValue();
            }
//            for (int i = 0; i < cellNum; i++) {
//                //根据索引获取对应的列
//                Cell cell=titlerow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                //设置列的类型是字符串
//                cell.setCellType(CellType.STRING);
//                String titleValue=cell.getStringCellValue();
////                String actuallValue=titleValue.substring(0, titleValue.indexOf("("));
////                System.out.println(actuallValue);
//                title = titleValue;
//            }

        } catch (EncryptedDocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return title;

    }

    public void sheetTodo() throws Exception {

        String filePath = "D:\\长沙项目\\测试数据\\表格处理\\1-4部分数据.xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        int total = workbook.getNumberOfSheets();//获取sheet数量
        workbook.close();

        XSSFSheet xssfSheet = workbook.getSheetAt(0);

        String sheetName = xssfSheet.getSheetName();//sheet名称，用于校验模板是否正确


    }

    //批量重命名excel文件
    public void renameExcel(String path) {
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
                        renameExcel(file2.getAbsolutePath());
                    } else {

                        String fileName = file2.getName();//文件名
//                        System.out.println("your name is:"+fileName);
                        String directory = file2.getParent();  //文件夹
//                        System.out.println("your parent is:"+directory);
//                        System.out.println("文件:" + file2.getAbsolutePath());  //文件夹+文件名
                        if (fileName.endsWith("xlsx") && !fileName.startsWith("0.xlsx")) {
                            String name = getTitleName(directory + "\\" + fileName);
                            name.replaceAll("", "  ");
                            String pathName = directory + "\\" + name + ".xlsx";
                            pathName = pathName.replaceAll("长沙项目", "长沙项目_处理后");
                            System.out.println(pathName);
                            try {
//                                FileUtils.copyFile(file2, new File(pathName));
                                System.out.println(fileName);
                                excelToPng(directory, fileName);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            boolean result = file2.renameTo(new File(pathName));
                        }

                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }

    }

    @Test
    public void rename() {
        renameExcel("D:\\长沙项目_处理后\\拆分后\\");
    }

    //表格截图

    public void excelToPng(String path, String name) throws Exception {
        //加载Excel文档
        Workbook workbook = new Workbook();


        workbook.loadFromFile(path + "\\" + name);


        Worksheet ws = workbook.getActiveSheet();

        BufferedImage bufferedImage = ws.toImage(2, 1, ws.getLastRow(), ws.getLastColumn());

        name = name.substring(0, name.lastIndexOf("."));

        ImageIO.write(bufferedImage, "PNG", new File(path + "\\" + name + ".png"));

    }

}


