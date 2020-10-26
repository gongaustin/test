package com.gongjun.changsha.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @Description: poi复制sheet的内容到另外一个sheet里
 * @Author: GongJun
 * @Date: Created in 16:30 2020/10/10
 */
public class CopySheet {
    /**
     * 新增sheet，并且复制sheet内容到新增的sheet里
     */
    private static void copySheet(HSSFWorkbook wb, HSSFSheet fromsheet, HSSFSheet newSheet, int firstrow, int lasttrow) {
        // 复制一个单元格样式到新建单元格
        if ((firstrow == -1) || (lasttrow == -1) || lasttrow < firstrow) {
            return;
        }
        // 复制合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            if ((region.getFirstRow() >= firstrow) && (region.getLastRow() <= lasttrow)) {
                newSheet.addMergedRegion(region);
            }
        }
        HSSFRow fromRow = null;
        HSSFRow newRow = null;
        HSSFCell newCell = null;
        HSSFCell fromCell = null;
        // 设置列宽
        for (int i = firstrow; i < lasttrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newSheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newSheet.setColumnHidden((short) j, true);
                    } else {
                        newSheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 复制行并填充数据
        for (int i = 0; i < lasttrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newSheet.createRow(i - firstrow);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                newCell.setCellStyle(fromCell.getCellStyle());
                CellType cType = fromCell.getCellType();
                newCell.setCellType(cType);
                switch (cType) {
                    case STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellValue(fromCell.getCellFormula());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }
}
