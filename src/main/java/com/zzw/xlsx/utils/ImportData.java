package com.zzw.xlsx.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ImportData {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void work() {
        List<Object[]> read = null;
        try {
            read = read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(read);
        System.out.println("## finished #");
    }
    public List<Object[]> read() throws IOException {
        List<Object[]> params = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File("D:/kangaroo.xlsx"));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bufferedInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        int ofRows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i <ofRows ; i++) {
            XSSFRow row = sheet.getRow(i);
            Object[] o = new Object[]{
                    formatNo(i),//1、收款单编号receiptCode
                    row.getCell(1).getStringCellValue(),//2、支付凭证voucherNo
                    row.getCell(0).getStringCellValue(),//3、receiptDate
                    "10",//4、receiptType
                    check(row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue()),//5、customerCode
                    row.getCell(13).getStringCellValue(),//6、paymentBank
                    row.getCell(11).getStringCellValue(),//7、paymentBankAccount
                    row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue(),//8、paymentName
                    row.getCell(9).getNumericCellValue(),//9、remitAmount
                    row.getCell(15) == null ? "" : row.getCell(15).getStringCellValue(),//10、remark
                    row.getCell(16).getStringCellValue(),//11\payeeName
                    row.getCell(18).getStringCellValue(),//12、payeeAccount
                    "20",//13、type
                    "0",//14\paymentWay
                    "20",//15、inputType
                    "1",//16、state
                    "1",//17、useState
                    "孟杜俊",//18、inputMan
                    new Date()//19、inputDate
            };
            params.add(o);

        }
        return params;
    }

    public void write(List<Object[]> params) {
        String sql = "INSERT INTO `cashreceipt` (\n" +
                "  `receiptCode`,\n" +
                "  `voucherNo`,\n" +
                "  `receiptDate`,\n" +
                "  `receiptType`,\n" +
                "  `customerCode`,\n" +
                "  `paymentBank`,\n" +
                "  `paymentBankAccount`,\n" +
                "  `paymentName`,\n" +
                "  `remitAmount`,\n" +
                "  `remark`,\n" +
                "  `payeeName`,\n" +
                "  `payeeAccount`,\n" +
                "  `paymentType`,\n" +
                "  `paymentWay`,\n" +
                "  `inputType`,\n" +
                "  `state`,\n" +
                "  `useState`,\n" +
                "  `inputMan`,\n" +
                "  `inputDate`\n" +
                ") \n" +
                "VALUES\n" +
                " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int[] ints = jdbcTemplate.batchUpdate(sql, params);
        System.out.println(ints);
    }

    private String check(Object o) {
        return o == null ? "" : o.toString();
    }

    private String formatNo(int n) {
        String no = "RV_M20190323";
        int length = String.valueOf(n).length();
        switch (length) {
            case 1:
                return no + "00000" + n;
            case 2:
                return no + "0000" + n;
            case 3:
                return no + "000" + n;
            case 4:
                return no + "00" + n;
            case 5:
                return no + "0" + n;
            default:
                return no + n;

        }

    }
}
