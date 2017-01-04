package tank.excel.entity;

import tank.excel.annotation.ExcelEntity;
import tank.excel.annotation.FieldFormat;
import tank.excel.format.ListFormat;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/26
 * @Version: 1.0
 * @Description:
 */
@ExcelEntity(file = "test.xlsx", sheet = "属性")
public class Role {

    protected int indexID;//索引ID
    protected int roleID;//角色ID

    @FieldFormat(format = ListFormat.class)
    protected List<Integer[]> interval;//成长率区间

    protected List<Integer[]> attr;//成长率区间

    //@FieldFormat(format = CollectionJsonFormat.class)
    protected Map<Integer, Integer> test;//觉醒消耗

    protected Set<Integer> test2;//

    protected Boolean test3;


}
