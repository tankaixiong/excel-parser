package tank.excel.parse;

import org.apache.commons.collections4.functors.AbstractQuantifierPredicate;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/26
 * @Version: 1.0
 * @Description:
 */
public class Temp {
    public static void main(String[] args) {


        Reflections reflections = new Reflections("org.apache.commons.collections4.functors");

        Set allClasses =
                reflections.getSubTypesOf(AbstractQuantifierPredicate.class);

        for (Object allClass : allClasses) {
            System.out.println(allClass);
        }

    }
}
