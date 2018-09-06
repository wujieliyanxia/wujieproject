package importdata;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-01
 * @desc 将易联众的信息导入库中
 **/
public class ImportDataFromYlz {
    private DruidDataSource dataSource;

    public void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 将Excel转为Map
     *
     * @param orgFilePath 易联众机构id对应正确的机构id
     */
    public static Map<String, String> excFileToMap(String orgFilePath) {
        Map<String, String> returnMap = new HashMap<>();
        File excelFile = new File(orgFilePath);
        HSSFWorkbook orgWorkbook = null;
        try {
            orgWorkbook = new HSSFWorkbook(new FileInputStream(excelFile));
            HSSFSheet orgSheet = orgWorkbook.getSheetAt(0);
            HSSFRow firstRow = orgSheet.getRow(0);
            int tyrzId = 0;// 正确机构id存放位置
            int abz345 = 0;// 易联众机构id存放位置
            for (int j = firstRow.getFirstCellNum(); j < firstRow.getLastCellNum(); j++) {
                if ("TYRZID".equals(firstRow.getCell(j).getStringCellValue())) tyrzId = j;
                if ("ABZ345".equals(firstRow.getCell(j).getStringCellValue())) abz345 = j;
            }
            for (int i = orgSheet.getFirstRowNum(); i < orgSheet.getLastRowNum(); i++) {
                HSSFRow row = orgSheet.getRow(i);
                String key = row.getCell(abz345).toString();
                String value = row.getCell(tyrzId).toString();
                returnMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (orgWorkbook != null) orgWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnMap;
    }

    /**
     * 将Excel中的user信息存入List中
     *
     * @param userFilePath Excel地址
     * @param ylzOrgMap    机构对应id
     * @return List<Map<String, String>>
     */
    public static List<Map<String, String>> excelToList(String userFilePath, Map<String, String> ylzOrgMap) {
        List<Map<String, String>> returnList = new ArrayList<>();
        File excelFile = new File(userFilePath);
        HSSFWorkbook userWorkbook = null;
        try {
            userWorkbook = new HSSFWorkbook(new FileInputStream(excelFile));
            HSSFSheet userSheet = userWorkbook.getSheetAt(0);
            HSSFRow firstRow = userSheet.getRow(0);
            for (int i = userSheet.getFirstRowNum() + 1; i < userSheet.getLastRowNum(); i++) {
                HSSFRow userRow = userSheet.getRow(i);
                Map<String, String> userMap = new HashMap<>();
                for (int j = userRow.getFirstCellNum(); j < userRow.getLastCellNum(); j++) {
                    String key = firstRow.getCell(j).toString();
                    String value = userRow.getCell(j).toString();
                    userMap.put(key, value);
                }
                // 转换机构id
                String directorgId = userMap.get("DIRECTORGID");
                String finalOrgId = ylzOrgMap.get(directorgId);
                if (finalOrgId != null && !"".equals(finalOrgId)) {
                    userMap.put("DIRECTORGID", finalOrgId);
                    returnList.add(userMap);
                } else {
                    System.out.println("机构id在机构id对应Excel中未找到");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (userWorkbook != null) {
                try {
                    userWorkbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }

    public void insertUser(List<Map<String, String>> insertList) {
        Connection conn = null;
        PreparedStatement ppst = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            ppst = conn.prepareStatement("select * from usertable");
            rs = ppst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                ppst.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        test();
        test1();
    }

    public static void test(){
        throw new RuntimeException("AAA");
    }

    public static void test1(){
        throw new RuntimeException("AAA");
    }
}
