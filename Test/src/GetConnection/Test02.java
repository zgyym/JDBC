package GetConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Period;
import java.util.Properties;

//练习连接方式
public class Test02 {
    public static void main(String[] args) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
        Properties prop = new Properties();
        prop.load(is);

        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String url = prop.getProperty("url");
        String driver = prop.getProperty("MySQLDriverClass");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,user,password);

        System.out.println(conn);




        /*
        连接的四个基本信息
            1、user（用户名）
            2、password（密码）
            3、url
            4、MySQL的Driver实现类对象（此处第五种方法在静态代码块中进行了注册驱动，可以类加载后直接获取连接）

         */
        Properties pr = new Properties();
        pr.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties"));

        String user1 = pr.getProperty("user");
        String password1 = pr.getProperty("password");
        String driver1 = pr.getProperty("MySQLDriverClass");
        String url1 = pr.getProperty("url");

        Class.forName(driver1);

        Connection c = DriverManager.getConnection(url1,user1,password1);
        System.out.println(c);


    }
}
