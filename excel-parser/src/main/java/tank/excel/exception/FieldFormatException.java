package tank.excel.exception;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/27
 * @Version: 1.0
 * @Description: 类字段转换异常
 */
public class FieldFormatException extends RuntimeException {
    public FieldFormatException(String msg) {
        super(msg);
    }
}
