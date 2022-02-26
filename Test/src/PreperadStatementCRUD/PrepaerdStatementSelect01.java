package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrepaerdStatementSelect01 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //连接数据库
            conn = JDBCUtils.getConnection();
            //预编译
            String sql = "select id,name,email,birth from customers where id in (?,?,?)";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setObject(1,1);
            ps.setObject(2,2);
            ps.setObject(3,3);

            //执行
            rs = ps.executeQuery();
            //处理结果集
            while(rs.next()){
                //这里的1、2、3、4是查询语句中的字段顺序
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);

                //将数据封装成一个对象
                Customer customer = new Customer(id,name,email,birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResources(conn,ps,rs);
        }


    }
}
