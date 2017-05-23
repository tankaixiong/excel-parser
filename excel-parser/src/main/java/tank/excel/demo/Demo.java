package tank.excel.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import tank.excel.parse.ExcelParser;

import java.util.List;
import java.util.Map;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class Demo {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        ExcelParser excelParser = ExcelParser.getInstance();
        //excelParser.addFieldFormat();
        //excelParser.setDataRowIndex(1);
        //excelParser.setTitleRowIndex(3);
        excelParser.scanPackage(new String[]{"tank.excel.entity"}, "/excel/");

        stopwatch.stop();

        System.out.println(stopwatch);

        //System.out.println(excelParser.getExcelMap().size());

        Map<Class, List> map = excelParser.getExcelMap();
        for (Map.Entry<Class, List> entry : map.entrySet()) {

            List list = entry.getValue();

            for (Object item : list) {

                System.out.println(objectMapper.writeValueAsString(item));
            }
        }

    }
}
