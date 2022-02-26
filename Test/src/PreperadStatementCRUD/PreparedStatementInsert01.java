package PreperadStatementCRUD;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;


//使用PreparedStatement实现CRUD（增删改查）操作
public class PreparedStatementInsert01 {
    public static void main(String[] args) {

        InputStream is = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //读取配置文件
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
            Properties prop = new Properties();
            prop.load(is);

            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String driver = prop.getProperty("MySQLDriverClass");
            String url = prop.getProperty("url");
            //加载驱动
            Class.forName(driver);
            //获取连接
            conn = DriverManager.getConnection(url,user,password);//？：占位符

            //预编译sql语句，返回PreparedStatement对象
            String sql = "insert into customers (name,email,birth) values (?,?,?)";
            ps= conn.prepareStatement(sql);
            //填充占位符，索引从‘1’开始
            ps.setString(1,"言魔");
            ps.setString(2,"yanmo@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("2018-12-14");
            ps.setDate(3,new Date(date.getTime()));

            //执行sql语句
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
