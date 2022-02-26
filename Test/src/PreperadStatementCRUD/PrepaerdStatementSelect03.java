package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrepaerdStatementSelect03 {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order`";
            ps = conn.prepareStatement(sql);
            //填充占位符


            //执行，获取结果集
            resultSet = ps.executeQuery();

            while(resultSet.next()){

                int id = (int) resultSet.getObject(1);
                String name = (String) resultSet.getObject(2);
                Date date = (Date) resultSet.getObject(3);

                Order order = new Order(id,name,date);
                System.out.println(order);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps,resultSet);
        }



    }
}
