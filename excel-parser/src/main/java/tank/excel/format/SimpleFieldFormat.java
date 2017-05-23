package tank.excel.format;

import tank.excel.exception.FieldFormatException;

import java.lang.reflect.Field;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class SimpleFieldFormat implements IFieldFormat<Number> {

    @Override
    public Number format(Field field, String value) {
        Class t = field.getType();

        if (value == null || value == "") {
            return 0;
        }
        if (t == Integer.class || t == int.class) {
            return Integer.valueOf(value);
        } else if (t == Long.class || t == long.class) {
            return Long.valueOf(value);
        } else if (t == Short.class || t == short.class) {
            return Short.valueOf(value);
        } else if (t == Float.class || t == float.class) {
            return Float.valueOf(value);
        } else if (t == Double.class || t == double.class) {
            return Double.valueOf(value);
        } else if (t == Byte.class || t == byte.class) {
            return Byte.valueOf(value);
        } else {
            throw new FieldFormatException("没有找到对应类型" + t + "的转换器");
        }
    }

}
