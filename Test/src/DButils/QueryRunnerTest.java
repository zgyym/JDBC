package DButils;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;
import com.alibaba.druid.util.Utils;
import connectionPooling.util.Util;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class QueryRunnerTest {


    //测试插入
    @Test
    public void insert() {
        Connection conn = null;
        try {
            conn = Util.getConn();
            QueryRunner runner = new QueryRunner();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int insertCount = runner.update(conn, sql, "蔡徐坤", "caixukun@126.com", "1997-09-08");
            System.out.println(insertCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,null);
        }
    }


    //测试查询
    /*
     * BeanHandler:是ResultSetHandler接口的实现类，用于封装表中的一条记录。
     *
     * BeanHandler后最好加上泛型
     * BeanHandler<T>
     */

    @Test
    public void query(){
        Connection conn = null;
        try {
            //获取连接
            conn = Util.getConn();
            //获取QueryRunner对象
            QueryRunner runner = new QueryRunner();

            String sql = "select id,name,email,birth from customers where id = ?";
            //BeanHandler:是ResultSetHandler接口的实现类，用于封装表中的一条记录。
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            //获取查询结果
            Customer customer = runner.query(conn, sql, handler, 24);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,null);
        }



    }

    /*
     * BeanListHandler:是ResultSetHandler接口的实现类，用于封装表中的多条记录构成的集合。
     * BeanListHandler后最好加上泛型
     * BeanListHandler<T>
     */
    @Test
    public void query2(){
        Connection conn = null;
        try {
            conn = Util.getConn();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(conn, sql, handler, 24);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,null);
        }



    }

    /*
     * MapHander:是ResultSetHandler接口的实现类，对应表中的一条记录。
     * 将字段及相应字段的值作为map中的key和value
     */
    @Test
    public void query3(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = Util.getConn();
            String sql = "select id,name,email,birth from customers where id = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = runner.query(conn, sql, handler, 24);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }

    }

    /*
     * MapListHander:是ResultSetHandler接口的实现类，对应表中的多条记录。
     * 将字段及相应字段的值作为map中的key和value。将这些map添加到List中
     */
    @Test
    public void query4(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = Util.getConn();
            String sql = "select id,name,email,birth from customers where id < ?";

            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> list = runner.query(conn, sql, handler, 23);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }


    /*
     * ScalarHandler:用于查询特殊值
     */
    @Test
    public void query5(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = Util.getConn();

            String sql = "select count(*) from customers";

            ScalarHandler handler = new ScalarHandler();

            Long count = (Long) runner.query(conn, sql, handler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }

    }

    @Test
    public void query6(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = Util.getConn();

            String sql = "select max(birth) from customers";

            ScalarHandler handler = new ScalarHandler();
            Date maxBirth = (Date) runner.query(conn, sql, handler);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }

    }


    /**
     *
     * @Description 使用dbutils.jar中提供的DbUtils工具类，实现资源的关闭
     * @author shkstart
     * @date 下午4:53:09
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource1(Connection conn, Statement ps, ResultSet rs){

//		try {
//			DbUtils.close(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(ps);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			DbUtils.close(rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}


        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }


}
