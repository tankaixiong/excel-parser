package tank.excel.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/26
 * @Version: 1.0
 * @Description: excel 与类的映射绑定
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ExcelEntity {
    String file();

    String sheet();
}
