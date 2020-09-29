package com.itheima.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: Terrance
 * @Date: 2020-09-25 22:16
 */
public class WriteExcel {
    public static void main(String[] args) throws Exception{
        //创建工作簿
        Workbook wk = new XSSFWorkbook();
        //创建工作表
        Sheet sht = wk.createSheet("中国");
        //创建行  下标从0开始
        Row row = sht.createRow(0);
        //创建单元格 下标从0开始
        Cell cell = row.createCell(0);

        //给单元格赋值
        // 姓名、年龄、地址
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("地址");

        // 内容
        row = sht.createRow(1);
        row.createCell(0).setCellValue("小明");
        row.createCell(1).setCellValue(20);
        row.createCell(2).setCellValue("深圳");

        row = sht.createRow(2);
        row.createCell(0).setCellValue("小李");
        row.createCell(1).setCellValue(30);
        row.createCell(2).setCellValue("四川");
        // 保存工作簿到本地
        wk.write(new FileOutputStream(new File("d:\\writeExcel.xlsx")));
        // 关闭工作簿
        wk.close();
    }
}
