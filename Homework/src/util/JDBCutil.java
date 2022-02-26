package util;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCutil {


    /**
     *
     * 连接数据库
     * @return
     * @throws Exception
     */
    public static Connection getConnextion() throws Exception {
        //以流的形式返回，文件放在类路径下
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
        Properties prop = new Properties();
        prop.load(is);

        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String url = prop.getProperty("url");
        String driver = prop.getProperty("MySQLDriver");

        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, user, password);

        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conn;

    }



    public static void close(Connection conn, PreparedStatement ps,ResultSet rs){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    public static void close(Connection conn, PreparedStatement ps){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


}
