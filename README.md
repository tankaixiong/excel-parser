
ExcelParser 解析excel转换为POJO类，支持基础类型(int,long,double,float,short,byte,string)转换;针对集合(json存储格式)提供默认转换


一，基本使用


ExcelParser excelParser = ExcelParser.getInstance();

//excelParser.addFieldFormat();

//excelParser.setDataRowIndex(1);

//excelParser.setTitleRowIndex(3);

excelParser.scanPackage(new String[]{"tank.excel.entity"}, "/excel/");//指定excel映射实体类的对应的包，excel数据文件路径


Map<Class, List> map = excelParser.getExcelMap();//获取数据

二，可自定义动态扩展转换类型

自定义解析字段示例：

2.1,添加注解

 @FieldFormat(format = ListFormat.class)
 protected List<Integer[]> myField;

2.2，实现逻辑
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

}