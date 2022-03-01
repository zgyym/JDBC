package Dao.test;

import Dao.CustomerDAOImpl;
import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOImplTest {

    private CustomerDAOImpl dao = new CustomerDAOImpl();

    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1999-11-04");
            Customer cust = new Customer(1, "于小飞", "xiaofei@126.com",new Date(date.getTime()));
            dao.insert(conn, cust);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }

    }

    @Test
    public void testDeleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            dao.deleteById(conn, 13);


            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }

    @Test
    public void testUpdateConnectionCustomer() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer cust = new Customer(18,"贝多芬","beiduofen@126.com",new Date(453465656L));
            dao.update(conn, cust);


            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }

    @Test
    public void testGetCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Customer cust = dao.getCustomerById(conn, 19);
            System.out.println(cust);


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }

    @Test
    public void testGetAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            List<Customer> list = dao.getAll(conn);
            list.forEach(System.out::println);


            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }

    @Test
    public void testGetCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Long count = dao.getCount(conn);

            System.out.println("表中的记录数为：" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }

    }

    @Test
    public void testGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Date maxBirth = dao.getMaxBirth(conn);

            System.out.println("最大的生日为：" + maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn, null);

        }
    }

}
