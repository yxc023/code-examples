package com.yangxiaochen.examples.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yangxiaochen
 * @date 16/4/28 下午6:05
 */
public class ReadAndWriteExcelExample {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        InputStream inp = new FileInputStream("apache-poi/excels/template.xlsx");
        XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(inp);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Sheet sheet : wb) {
            sb.append("<table>");
            for (Row row : sheet) {
                sb.append("<tr>");
                for (Cell cell : row) {
                    sb.append("<td>");
                    sb.append(cell);
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
            sb.append("</table>");
            System.out.println(sb.toString());
            System.out.println( i++ +"-------------------------------------------------");
        }

        wb.close();


    }
}
