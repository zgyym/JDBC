package connectionPooling;

import Dao.CustomerDAOImpl;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import connectionPooling.util.Util;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

public class DruidTest {


    @Test
    public void getConnection(){
        try {
            Properties prop = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Druid.properties");
            prop.load(is);
            DataSource source = DruidDataSourceFactory.createDataSource(prop);
            Connection conn = source.getConnection();
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void conntion() throws Exception {
        Connection conn = Util.getConn();
        System.out.println(conn);
    }



    @Test
    public void testGetAll() {
        Connection conn = null;
        try {
            CustomerDAOImpl dao = new CustomerDAOImpl();
            conn = Util.getConn();

            List<Customer> list = dao.getAll(conn);
            list.forEach(System.out::println);


            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }
}
