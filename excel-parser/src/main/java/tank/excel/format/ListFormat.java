package tank.excel.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class ListFormat implements IFieldFormat<List<Integer[]>> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Integer[]> format(Field field, String value) {

        try {
            //Class fieldClazz = field.getType();

            return objectMapper.readValue(value, new TypeReference<List<Integer[]>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static void main(String[] args) throws JsonProcessingException {
        Map<Integer, Integer> a = new HashMap<>();
        a.put(1, 32);
        a.put(3, 32);
        System.out.println(objectMapper.writeValueAsString(a));


    }
}
