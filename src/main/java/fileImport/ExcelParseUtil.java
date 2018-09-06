package fileImport;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-06-06
 * @desc excel文件解析类
 **/
public class ExcelParseUtil {
    public static List<Map<String, Object>> parse(FileImportDescriptor fileImportDescriptor) throws Exception {
        List<Map<String, Object>> returnList = new ArrayList<>();
        Workbook workbook;
        if ("xls".equals(fileImportDescriptor.getFileType())) {
            workbook = new HSSFWorkbook(fileImportDescriptor.getInputStream());
        } else {
            throw new Exception("生成excel工作薄失败，不是excel文件");
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row fieldRow = sheet.getRow(fileImportDescriptor.getFieldRow());
        for (int i = fileImportDescriptor.getDataStartRow(); i < sheet.getLastRowNum(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            Row currentRow = sheet.getRow(i);
            for (int j = currentRow.getFirstCellNum(); j < currentRow.getLastCellNum(); j++) {
                dataMap.put(fieldRow.getCell(j).toString(), currentRow.getCell(j).toString());
            }
            returnList.add(dataMap);
        }
        return returnList;
    }

    public static void main(String[] args) throws Exception {
        FileImportDescriptor fileImportDescriptor = new FileImportDescriptor();
        fileImportDescriptor.setDataStartRow(5);
        fileImportDescriptor.setFieldRow(4);
        File file = new File("F:\\腾讯通文件\\wujie\\订立劳动合同批量申报模板.xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        fileImportDescriptor.setInputStream(fileInputStream);
        fileImportDescriptor.setFileType("xls");
        List<Map<String, Object>> parse = parse(fileImportDescriptor);
        System.out.println(JSONObject.toJSONString(parse));
    }
}
