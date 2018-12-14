package me.silentdoer.exceltest;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * HSSF对应xx.xls文件，而XSSF对应xx.xlsx文件
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws IOException, InvalidFormatException {

        final String absFile = "E:\\Desktop\\Documents\\项目资料\\运营商\\运营商报告样例\\报告数据字典.xlsx";
        //Workbook workbook = new XSSFWorkbook(new File(absFile));
        XSSFWorkbook workbook = new XSSFWorkbook(new File(absFile));
        // xx.xlsx文件下方的sheet个数
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            // 也可以通过sheet的名字来获取
            //Sheet sheet = workbook.getSheetAt(i);
            XSSFSheet sheet = workbook.getSheetAt(i);

            // 获取此sheet的开始行（即第一个有效行），用num表示，size表示总数，num表示当前数
            int startRow = sheet.getFirstRowNum();
            int endRow = sheet.getLastRowNum();

            System.out.println(MessageFormat.format("{0},{1}", startRow, endRow));

            // TODO 循环所有行（有效行）
            for(int j = startRow; j <= endRow; j++) {
                //Row row = sheet.getRow(j);
                XSSFRow row = sheet.getRow(j);
                if(Objects.isNull(row)){
                    continue;
                }
                // TODO 注意，每一行都有个开头列和结束列，因此确实不能写在上面，如果正行都是空的那么row是null
                int startColumn = row.getFirstCellNum();
                int endColumn = row.getLastCellNum();
                System.out.println(MessageFormat.format("{0},{1}", startColumn, endColumn));
                for(int m = startColumn; m <= endColumn; m++){
                    // TODO 确实可能出现null的风险，比如第一格有值，第二格没有值，第三格有值，那么获取第二格时会报错；
                    //Cell cell = row.getCell(m);
                    XSSFCell cell = row.getCell(m);
                    if(Objects.isNull(cell)){
                        continue;
                    }
                    // TODO 这里可以进一步判断，如果当前的列是最左侧的那么肯定是和上面的一样，如果是有效行里最上面的一行那么肯定是和左边的一样
                    String msg = "和上面或左边一样";
                    if(j == startRow){
                        msg = "和左边一样";
                    }
                    if(m == startColumn){
                        msg = "和上边一样";
                    }
                    var s = "uuu";
                    System.err.println(s);
                    System.out.print(MessageFormat.format("{0},", "".equals(cell.toString()) ? msg : cell.toString()));
                }
                var ssss = "中文";
                System.out.println();
                System.out.println(ssss);
            }
        }
    }
}
