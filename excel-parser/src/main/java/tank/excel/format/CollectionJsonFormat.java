package tank.excel.format;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
    public Object format(Class fieldClazz, String value) {

        try {
            return objectMapper.readValue(value, fieldClazz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
