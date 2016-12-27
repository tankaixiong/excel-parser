package tank.excel.annotation;

import tank.excel.format.IFieldFormat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/26
 * @Version: 1.0
 * @Description: excel 字段绑定自定义格式化类
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface FieldFormat {
    Class<? extends IFieldFormat> format();
}
