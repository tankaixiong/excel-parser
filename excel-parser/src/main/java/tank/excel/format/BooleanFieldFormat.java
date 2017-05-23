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
public class BooleanFieldFormat implements IFieldFormat<Boolean> {

    @Override
    public Boolean format(Field field, String value) {
        Class t = field.getType();
        if (value == null || value == "") {
            return false;
        }
        if (t == Boolean.class || t == boolean.class) {

            if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                return Boolean.valueOf(value);
            } else if ("1".equals(value)) {
                return true;
            } else if ("0".equals(value)) {
                return false;
            } else {
                throw new FieldFormatException("无法解析类型boolean =" + value);
            }

        } else {
            throw new FieldFormatException("没有找到对应类型" + t + "的转换器");
        }
    }

}
