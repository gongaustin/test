package com.gongjun.changsha;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 14:38 2020/9/23
 */
public class excel2png {


    @Test
    public void toPng() throws Exception{
        //加载Excel文档
        Workbook workbook = new Workbook();


        workbook.loadFromFile("D:\\1.xlsx");


        Worksheet ws = workbook.getActiveSheet();

        BufferedImage bufferedImage = ws.toImage(1, 1, ws.getLastRow(), ws.getLastColumn());

        ImageIO.write(bufferedImage, "PNG", new File("D:\\1.png"));

        //将Excel文档第一个工作表中的第一个图表保存为图片

//        Worksheet ws = workbook.getWorksheets().get(0);

//        BufferedImage image= workbook.saveChartAsImage(ws,0);


//        ImageIO.write(image,"png", new File("D:\\1.png"));

    }

}
