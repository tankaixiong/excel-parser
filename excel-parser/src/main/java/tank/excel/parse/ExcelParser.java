package tank.excel.parse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tank.excel.annotation.ExcelEntity;
import tank.excel.annotation.FieldFormat;
import tank.excel.exception.AnnotationParamException;
import tank.excel.exception.ExcelException;
import tank.excel.exception.FieldFormatException;
import tank.excel.format.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: tank
 * @Email: kaixiong.tan@qq.com
 * @Date: 2016/12/26
 * @Version: 1.0
 * @Description: 读取并转换
 */
public class ExcelParser {
    private static Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);
    private static ExcelParser context = new ExcelParser();
    private static Map<Class, List> excelMap = new HashMap<>();//转换后的数据集合


    private Map<String, List<ExcelPojo>> workbookSheet = new HashMap<>();
    private Map<Class<?>, IFieldFormat<?>> customFieldFormatMap;//缓存转换器实例
    private Map<Class<?>, IFieldFormat<?>> fieldFormatMap;

    private int titleRowIndex = 1;//读取字段行的索引
    private int dataRowIndex = 3;//数据行的索引

    private ExcelParser() {
        customFieldFormatMap = new HashMap<>();
        fieldFormatMap = new HashMap<>();

        //常用基础类型
        SimpleFieldFormat simpleFieldFormat = new SimpleFieldFormat();

        fieldFormatMap.put(Integer.class, simpleFieldFormat);
        fieldFormatMap.put(int.class, simpleFieldFormat);

        fieldFormatMap.put(Long.class, simpleFieldFormat);
        fieldFormatMap.put(long.class, simpleFieldFormat);

        fieldFormatMap.put(Double.class, simpleFieldFormat);
        fieldFormatMap.put(double.class, simpleFieldFormat);

        fieldFormatMap.put(Float.class, simpleFieldFormat);
        fieldFormatMap.put(float.class, simpleFieldFormat);

        fieldFormatMap.put(Short.class, simpleFieldFormat);
        fieldFormatMap.put(short.class, simpleFieldFormat);

        fieldFormatMap.put(Byte.class, simpleFieldFormat);
        fieldFormatMap.put(byte.class, simpleFieldFormat);

        //boolean 型
        BooleanFieldFormat booleanFieldFormat = new BooleanFieldFormat();
        fieldFormatMap.put(Boolean.class, booleanFieldFormat);
        fieldFormatMap.put(boolean.class, booleanFieldFormat);


        //字符串
        fieldFormatMap.put(String.class, new StringFieldFormat());
        //集合格式
        CollectionJsonFormat collectionJsonFormat = new CollectionJsonFormat();
        fieldFormatMap.put(Map.class, collectionJsonFormat);
        fieldFormatMap.put(List.class, collectionJsonFormat);
        fieldFormatMap.put(Set.class, collectionJsonFormat);


    }

    public void setTitleRowIndex(int titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public void setDataRowIndex(int dataRowIndex) {
        this.dataRowIndex = dataRowIndex;
    }

    public void addFieldFormat(Class<T> fieldType, IFieldFormat<T> fieldFormat) {
        fieldFormatMap.put(fieldType, fieldFormat);
    }

    public static ExcelParser getInstance() {
        return context;
    }

    public void scanPackage(String[] packages, String excelDir) {
        for (String packageDir : packages) {
            findAllEntity(packageDir);
        }

        Iterator<Map.Entry<String, List<ExcelPojo>>> entryIterator = workbookSheet.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<ExcelPojo>> entry = entryIterator.next();
            readExcel(excelDir + "/" + entry.getKey(), entry.getValue());
        }
        workbookSheet.clear();
        workbookSheet = null;

        customFieldFormatMap.clear();
        customFieldFormatMap = null;
    }

    private void findAllEntity(String packageDir) {
        //定义扫描包目录
        Reflections reflections = new Reflections(packageDir);
        Set<Class<?>> excelEntityClazz =
                reflections.getTypesAnnotatedWith(ExcelEntity.class);

        for (Class<?> clazz : excelEntityClazz) {

            excelMap.put(clazz, new ArrayList());

            ExcelEntity anno = clazz.getAnnotation(ExcelEntity.class);
            if (anno == null) {
                continue;
            }

            if (anno.file() == null || anno.file() == "" || anno.sheet() == null || anno.sheet() == "") {
                throw new AnnotationParamException("参数不能为空");
            }
            List<ExcelPojo> excelPojoList = workbookSheet.get(anno.file());
            if (excelPojoList == null) {
                excelPojoList = new ArrayList<>();
                workbookSheet.put(anno.file(), excelPojoList);
            }
            excelPojoList.add(new ExcelPojo(anno.sheet(), clazz));

        }
    }

    public static Map<Class, List> getExcelMap() {
        return excelMap;
    }


    private void readExcel(String file, List<ExcelPojo> sheetNameList) {

        InputStream inp = null;
        Workbook wb = null;
        try {
            //InputStream inp = new FileInputStream("workbook.xls");
            inp = ExcelParser.class.getResourceAsStream(file);
            //inp = new FileInputStream(filePath);

            wb = WorkbookFactory.create(inp);

            for (ExcelPojo excelPojo : sheetNameList) {
                Sheet sheet = wb.getSheet(excelPojo.getSheetName());

                Map<Integer, String> indexTitle = new HashMap<>();//列与字段名的映射关系

                Row titleRow = sheet.getRow(titleRowIndex);
                if (titleRow == null) {
                    throw new ExcelException("没有设置表字段行");
                }
                int cellNum = titleRow.getLastCellNum();
                for (int i = 0; i < cellNum; i++) {
                    //title行为对应类的字段
                    Cell titleRowCell = titleRow.getCell(i);
                    if (titleRowCell == null) {
                        continue;
                    }
                    titleRowCell.setCellType(CellType.STRING);
                    String titleName = titleRowCell.getStringCellValue();
                    if (titleName == null || titleName == "") {
                        continue;
                    }
                    indexTitle.put(i, titleName.trim());
                }


                int rowNum = sheet.getLastRowNum();
                for (int r = dataRowIndex; r < rowNum; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }

                    Class objClazz = excelPojo.getClazz();
                    Object obj = objClazz.newInstance();

                    for (int c = 0; c < cellNum; c++) {

                        String titleName = indexTitle.get(c);//以有字段头的数据才会被写入
                        if (titleName == null) {
                            continue;
                        }

                        Cell cell = row.getCell(c);
                        if (cell == null) {
                            continue;
                        }
                        //处理函数单元格问题，先将单元格内容转为String
                        cell.setCellType(CellType.STRING);
                        String cellValue = cell.getStringCellValue();

                        Field field = null;
                        try {
                            field = objClazz.getDeclaredField(titleName);
                        } catch (NoSuchFieldException ex) {
                            LOGGER.warn("{},{}没有对应的类字段映射", excelPojo.getSheetName(), titleName);
                            continue;
                        }

                        field.setAccessible(true);

                        IFieldFormat fieldFormat = null;

                        //优先查看有没有指定转换类
                        FieldFormat fieldFormatAnno = field.getAnnotation(FieldFormat.class);
                        if (fieldFormatAnno != null) {
                            fieldFormat = customFieldFormatMap.get(fieldFormatAnno.format());
                            if (fieldFormat == null) {
                                fieldFormat = fieldFormatAnno.format().newInstance();
                                customFieldFormatMap.put(fieldFormatAnno.format(), fieldFormat);
                            }
                        } else {
                            fieldFormat = fieldFormatMap.get(field.getType());
                        }
                        if (fieldFormat == null) {
                            throw new FieldFormatException("没有找到对应类型" + field.getType() + "的转换器");
                        }
                        try {
                            Object fieldValue = fieldFormat.format(field, cellValue);//格式化，可做扩展
                            field.set(obj, fieldValue);
                        } catch (Exception e) {
                            LOGGER.error("表:{},sheet:{}, 行:{}, 列:{}", file, sheet.getSheetName(), r, c);
                            LOGGER.error("{}", e);
                        }


                    }

                    excelMap.get(objClazz).add(obj);//加入集合

                }

                indexTitle.clear();
                indexTitle = null;
            }


        } catch (Exception e) {
            LOGGER.error("{}", e);
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
