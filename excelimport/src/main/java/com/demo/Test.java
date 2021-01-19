package com.demo;

import com.demo.excel.ExcelImportUtils;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        File file = new File("E:\\核对整理锦鹏（结果0115）.xlsx");
        ExcelImportUtils demoUtils = new ExcelImportUtils(0, 1);
        Map<String, Object> map = demoUtils.getExcelData(file);
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            List<Map<String, String>> bodyData = (List) map.get("bodyData");
            for (Map b : bodyData) {
                String id = (String) b.get("ID");
                String ptmz = (String) b.get("PTMZ");
                String khbh = (String) b.get("YHBH");
                String dbh = (String) b.get("BH");
                System.out.printf(id + ",");
                System.out.printf(ptmz + ",");
                System.out.printf(khbh + ",");
                System.out.println(dbh);
//                sb.delete(0, sb.length());
//                sb.append("insert into atest(test,n1,n2) values('").append(id).append("','").append(khbh).append("','").append(dbh).append("');");
//                ps = conn.prepareStatement(sb.toString(),
//                        ResultSet.TYPE_SCROLL_INSENSITIVE,
//                        ResultSet.CONCUR_UPDATABLE);
//                if (ps.executeUpdate() < 0) {
//                    conn.rollback();
//                }else{
//                    conn.commit();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://cs.nes.com.cn:3308/ctcetest?characterEncoding=utf-8", "ctcetest", "NES@ctceZO1B3");
//            connection = DriverManager.getConnection("jdbc:mysql://rm-2ze3340731300n995no.mysql.rds.aliyuncs.com:3306/ctceyun?characterEncoding=utf-8","ctceyun","NESctce2017");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;

    }
}
