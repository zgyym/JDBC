package GetConnetion;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;


//五种连接方式
public class Test01 {
    public static void main(String[] args) throws Exception {
/*
    JDBC编程步骤：
        1、注册驱动；
        2、获取连接；
        3、获取数据库操作对象；
        4、执行sql语句；
        5、处理查询结果集；
        6、释放资源；*/



        //方式一
        //连接基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","zgy1314521");

        //获取Driver实现类对象
        Driver driver = new com.mysql.cj.jdbc.Driver();

        //获取连接
        Connection conn = driver.connect(url,info);

        System.out.println(conn);


        // 方式二：对方式一的迭代:在如下的程序中不出现第三方的api,使得程序具有更好的可移植性
        //连接基本信息
        String url1 = "jdbc:mysql://localhost:3306/test";
        Properties info1 = new Properties();
        info1.setProperty("user","root");
        info1.setProperty("password","zgy1314521");

        //获取Driver实现类对象，使用反射机制
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver1 = (Driver) clazz.newInstance();

        //获取连接
        Connection conn1 = driver1.connect(url,info);

        System.out.println(conn1);


        //方式三：使用DriverManager替代Driver
        //提供连接的基本信息
        String url2 = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "zgy1314521";

        //获取Driver实现类对象
        Class clazz1 = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver2 = (Driver) clazz1.newInstance();

        //注册驱动
        DriverManager.registerDriver(driver2);

        //获取连接
        Connection conn2 = DriverManager.getConnection(url2,user,password);

        System.out.println(conn2);




        //方式四：可以只是加载驱动，而不用手动去注册驱动
        //因为在com.mysql,cj.jdbc.Driver中声明了静态代码块，而静态代码块中实现了对驱动的注册，类加载时会执行静态代码块

        //连接的基本信息
        String url3 = "jdbc:mysql://localhost:3306/test";
        String user1 = "root";
        String password1 = "zgy1314521";

        //把MySQL中的Driver类加载到内存中
        Class.forName("com.mysql.cj.jdbc.Driver");

/*      Driver driver3 = (Driver) clazz2.newInstance();

        //注册驱动
        DriverManager.registerDriver(driver2);
*/
        //获取连接
        Connection conn3 = DriverManager.getConnection(url2,user,password);

        System.out.println(conn3);




        //方式五：（最新版）：将数据库连接需要的四个基本信息声明在配置文件中，通过读取配置文件的方式，获取链接

        //以流的形式返回配置文件
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
        Properties prop = new Properties();
        //加载配置文件
        prop.load(is);

        //根据key读取配置文件的value
        String user4 = prop.getProperty("user");
        String password4 = prop.getProperty("password");
        String url4 = prop.getProperty("url");
        String driver4 = prop.getProperty("MySQLDriverClass");

        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection con = DriverManager.getConnection(url4,user4,password4);

        System.out.println(con);



    }

}
