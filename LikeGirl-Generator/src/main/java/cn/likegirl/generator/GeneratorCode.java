package cn.likegirl.generator;

import cn.likegirl.generator.untils.GenUtils;
import com.google.common.base.CaseFormat;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: GeneratorCode
 * @description: TODO
 * @date 2019/3/11 15:56
 */
public class GeneratorCode {

    private static final String URL = "jdbc:mysql://192.168.11.40:3306/tms_1.8.1?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String TEMP = System.getProperty("user.dir") + "\\";

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;


    public static void before() throws ClassNotFoundException {
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void after() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            before();
            String tableSql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(tableSql);
            preparedStatement.setObject(1, "tms_company_cevenue_detail");
            ResultSet rs = preparedStatement.executeQuery();
            // Table 信息
            Map<String, String> table = resultSetToList(rs).get(0);
            // 字段信息
            String columnSql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name = ? and table_schema = (select database()) order by ordinal_position";
            preparedStatement = connection.prepareStatement(columnSql);
            preparedStatement.setObject(1, "tms_company_cevenue_detail");
            rs = preparedStatement.executeQuery();
            List<Map<String, String>> columns = resultSetToList(rs);
            System.out.println(table);
            System.out.println(columns);
            // 生成代码
            System.out.println(System.getProperty("user.dir"));
            File file = new File(TEMP + "\\temp\\01\\likegirl.zip");
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            byte[] data = generatorToByteStream(table, columns);
            IOUtils.write(data, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            after();
        }
    }


    public static List<Map<String, String>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, String>> list = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                // 转驼峰式
                row.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, md.getColumnName(i)), rs.getString(i));
            }
            list.add(row);
        }
        return list;
    }

    public static byte[] generatorToByteStream(Map<String, String> table, List<Map<String, String>> columns) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GenUtils.generatorCode(table, columns, zip);
//        IOUtils.closeQuietly(zip);
        zip.close();
        return outputStream.toByteArray();
    }

}
