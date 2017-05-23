import com.google.common.base.Stopwatch;
import org.junit.Test;
import tank.excel.entity.Role;
import tank.excel.parse.ExcelParser;

import java.util.List;
import java.util.Map;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2017/5/23
 * @Version: 1.0
 * @Description:
 */
public class ExcelTest {

    @Test
    public void parserTest() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        ExcelParser excelParser = ExcelParser.getInstance();
        //excelParser.addFieldFormat();
        //excelParser.setDataRowIndex(1);
        //excelParser.setTitleRowIndex(3);
        excelParser.scanPackage(new String[]{"tank.excel.entity"}, "/excel/");

        stopwatch.stop();

        System.out.println(stopwatch);

        Map<Class, List> map = excelParser.getExcelMap();

        for (List list : map.values()) {
            for (Object item : list) {
                Role role = (Role) item;
                System.out.println(role.getAttr());
            }
        }

    }


}
