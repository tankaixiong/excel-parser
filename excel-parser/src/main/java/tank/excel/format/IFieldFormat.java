package tank.excel.format;

import java.lang.reflect.Field;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description: 所有类型转换的需要实现该接口
 */
public interface IFieldFormat<T> {

    T format(Field field, String value);
}
