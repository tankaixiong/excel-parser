package tank.excel.format;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description: 类型为集合，数据为json格式
 */
public class CollectionJsonFormat implements IFieldFormat {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object format(Field field, String value) {

        try {
            Class fieldClazz = field.getType();

            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) type;

                Type[] typeArguments = paramType.getActualTypeArguments();

                List<Class<?>> argClazzList = new ArrayList<>();
                for (Type arg : typeArguments) {
                    Class argClazz = (Class) arg;
                    argClazzList.add(argClazz);
                }

                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(fieldClazz,
                        argClazzList.toArray(new Class<?>[argClazzList.size()]));

                return objectMapper.readValue(value, javaType);

            } else {

                return objectMapper.readValue(value, fieldClazz);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
