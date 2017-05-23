package tank.excel.format;

import java.lang.reflect.Field;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class StringFieldFormat implements IFieldFormat<String> {
    @Override
    public String format(Field field, String value) {
        return value;
    }
}
