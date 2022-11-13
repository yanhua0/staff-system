/*
 * www.unisinsight.com Inc.
 * Copyright (c) 2018 All Rights Reserved
 */
package org.zjl.staff.utils;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


@Slf4j
public class POIExcelUtils {


    public static XSSFWorkbook creatWorkbook(String name) {
        ClassPathResource classPathResource = new ClassPathResource("excel/" + name);
        XSSFWorkbook workbook=null;
        try (InputStream in = new BufferedInputStream(classPathResource.getInputStream())) {
            workbook =new XSSFWorkbook(in);
        } catch (IOException e) {
            log.error("根据模板创建导出文件失败", e);
        }
        return workbook;
    }
    public static void getOutStreamResponse(XSSFWorkbook wb, String fName) {
        log.info("开始导出文件:{}",fName);
        // 下载文件的默认名称
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            response.setHeader("content-Type", "application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode
                    (fName, "UTF-8")+".xlsx");
        } catch (UnsupportedEncodingException e) {
            log.info("不支持的encode",e);
        }
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("导出文件异常：{}",e);
        }
        log.info("导出文件成功：{}",fName);
    }
    @SneakyThrows
    public static void main(String[] args){
        InputStream in = new FileInputStream(new File("D:\\JavaProject\\staff-system\\src\\main\\resources\\excel\\template.xlsx"));
        XSSFWorkbook workbook =new XSSFWorkbook(in);
        Sheet sheet=workbook.getSheetAt(1);

        Row row=sheet.getRow(61);
        row.getCell(1).getStringCellValue();
//        row.setHeight((short)(19*20));
//        XSSFCellStyle cellStyleR = workbook.createCellStyle();
//        cellStyleR.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//        cellStyleR.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//        cellStyleR.setBorderRight(XSSFCellStyle.BORDER_THIN);
//        cellStyleR.setBorderTop(XSSFCellStyle.BORDER_THIN);
//
//
//        row.setRowStyle(cellStyleR);
//        XSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Cell cell=row.createCell(2);


        cell.setCellValue("10月");
        FileOutputStream fileOutputStream=new FileOutputStream(new File("D:\\JavaProject\\staff-system\\target\\1.xlsx"));
        workbook.write(fileOutputStream);
        fileOutputStream.close();

    }
}
