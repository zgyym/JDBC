package connectionPooling.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class Util {


    /**
     *
     * 使用Druid数据库连接池技术
     */


    private static DataSource source;
    static{
        try {
            Properties prop = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Druid.properties");
            prop.load(is);
            source = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() throws Exception {

        Connection conn = source.getConnection();
        return conn;
    }
}
