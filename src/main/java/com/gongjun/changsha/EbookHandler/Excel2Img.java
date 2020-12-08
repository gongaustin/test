package com.gongjun.changsha.EbookHandler;

import com.aspose.cells.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 15:24 2020/12/3
 */
@Slf4j
public class Excel2Img {
    /**
     * 根据输入的参数对Excel进行截图
     * @param sheetId         sheet页的Id
     * @param startPoint      指定区域开始的坐标
     * @param endPoint        指定区域结束的坐标
     */
    public static void convertToImage(File excelFile, int sheetId, String startPoint, String endPoint) {
        try {
            if (!LicenseUtil.GetLicense()) {
                log.info("无法获取Excel License，请联系管理员检查");
            }
            System.out.println("Start Convert Job");
            String fileName = excelFile.getName();
            String fileNameWithoutSuffix = fileName.substring(0,fileName.lastIndexOf("."));
            String picPath = excelFile.getParent()+"\\"+fileNameWithoutSuffix+".png";

            Workbook workbook = new Workbook(excelFile.getAbsolutePath());
            // 根据sheetId获取第几个sheet页
            Worksheet worksheet = workbook.getWorksheets().get(sheetId);
            String area = startPoint + ":" + endPoint;
            System.out.println("area: " + area);
            // 设置图片数据的边距
            worksheet.getPageSetup().setPrintArea(area);
            worksheet.getPageSetup().setLeftMargin(1);
            worksheet.getPageSetup().setRightMargin(1);
            worksheet.getPageSetup().setTopMargin(1);
            worksheet.getPageSetup().setBottomMargin(1);
            ImageOrPrintOptions options = new ImageOrPrintOptions();
            options.setCellAutoFit(true);
            options.setOnePagePerSheet(true);
            options.setImageFormat(ImageFormat.getPng());
            // 设置字体的样式（包含中文）
            CellsHelper.setFontDir("/usr/share/fonts/chinese/Fonts");
            SheetRender sheetRender = new SheetRender(worksheet, options);
            System.out.println("size -> " + sheetRender.getPageCount());
            for (int i = 0; i < sheetRender.getPageCount(); i++) {
                // 分页保存成图片
                sheetRender.toImage(i, picPath);
            }
            System.out.println("Finish Convert Job");
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

}
