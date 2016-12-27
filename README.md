# excel-parser
解析excel转换为POJO类
一，支持基础类型(int,long,double,float,short,byte,string)转换

二，针对集合(json存储格式)提供默认转换


三，可自定义动态扩展转换类型


示例：
```
        ExcelParser excelParser = ExcelParser.getInstance();
        //excelParser.addFieldFormat();
        //excelParser.setDataRowIndex(1);
        //excelParser.setTitleRowIndex(3);
        excelParser.scanPackage(new String[]{"tank.excel.entity"}, "/excel/");
```
