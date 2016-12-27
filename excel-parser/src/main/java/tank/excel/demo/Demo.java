package tank.excel.demo;

import com.google.common.base.Stopwatch;
import tank.excel.parse.ExcelParser;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class Demo {
    public static void main(String[] args) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        ExcelParser excelParser = ExcelParser.getInstance();
        //excelParser.addFieldFormat();
        //excelParser.setDataRowIndex(1);
        //excelParser.setTitleRowIndex(3);
        excelParser.scanPackage(new String[]{"tank.excel.entity"}, "/excel/");

        stopwatch.stop();

        System.out.println(stopwatch);

        System.out.println(excelParser.getExcelMap().size());
    }
}
