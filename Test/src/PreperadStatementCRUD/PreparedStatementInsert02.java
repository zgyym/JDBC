package PreperadStatementCRUD;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class PreparedStatementInsert02 {
    public static void main(String[] args) {
        InputStream is = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
            Properties prop = new Properties();
            prop.load(is);

            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String driver = prop.getProperty("MySQLDriverClass");
            String url = prop.getProperty("url");

            Class.forName(driver);

            conn = DriverManager.getConnection(url,user,password);

            String sql = "insert into customers (name,email,birth) values (?,?,?)";
            //获取preparedStatement对象
            ps = conn.prepareStatement(sql);
            ps.setString(1,"憨色");
            ps.setString(2,"hanser@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("2018-12-14");
            ps.setDate(3,new Date(date.getTime()));

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
