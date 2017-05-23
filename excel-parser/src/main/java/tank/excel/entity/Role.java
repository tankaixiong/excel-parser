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

    public int getIndexID() {
        return indexID;
    }

    public void setIndexID(int indexID) {
        this.indexID = indexID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public List<Integer[]> getInterval() {
        return interval;
    }

    public void setInterval(List<Integer[]> interval) {
        this.interval = interval;
    }

    public List<Integer[]> getAttr() {
        return attr;
    }

    public void setAttr(List<Integer[]> attr) {
        this.attr = attr;
    }

    public Map<Integer, Integer> getTest() {
        return test;
    }

    public void setTest(Map<Integer, Integer> test) {
        this.test = test;
    }

    public Set<Integer> getTest2() {
        return test2;
    }

    public void setTest2(Set<Integer> test2) {
        this.test2 = test2;
    }

    public Boolean getTest3() {
        return test3;
    }

    public void setTest3(Boolean test3) {
        this.test3 = test3;
    }
}
