package com.itheima.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @Author: Terrance
 * @Date: 2020-09-25 22:06
 */
public class ReadExcel {
    public static void main(String[] args) throws Exception {
        //创建工作簿
        Workbook wk = new XSSFWorkbook("D:\\read.xlsx");
        //获取工资表  下标从0开始
        Sheet sht = wk.getSheetAt(0);
        //sht.getLastRowNum(); //最后的行号
        //sht.getPhysicalNumberOfRows(); // 总共多少行
        // 遍历行 从0开始
        for (Row row : sht) {
            //row.getPhysicalNumberOfCells();
            //row.getLastCellNum();
            // 遍历单元格 从0开始
            for (Cell cell : row) {
                //获取单元格的值
                int cellType = cell.getCellType();
                if (Cell.CELL_TYPE_NUMERIC == cellType){
                    //数值的单元格
                    System.out.println(cell.getNumericCellValue() + ",");
                }else {
                    System.out.println(cell.getStringCellValue() + ",");
                }
            }
            System.out.println();
        }
        //关闭工作簿
        wk.close();
    }
}
