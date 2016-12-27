package tank.excel.parse;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description:
 */
public class ExcelPojo {
    private String sheetName;
    private Class clazz;

    public ExcelPojo(String sheetName, Class clazz) {
        this.sheetName = sheetName;
        this.clazz = clazz;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
