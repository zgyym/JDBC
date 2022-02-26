package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;
import PreperadStatementCRUD.bean.Order;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SelectSummary02 {
    public static void main(String[] args) {
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id < ?";
        List<Order> list = getInformation(Order.class, sql,7);
        for (Order order : list) {
            System.out.println(order);
        }


    }

    /**
     *
     * 使用PreparedStatement实现对不同表的查询操作
     * 返回多条记录
     */

    public static <T> List<T> getInformation(Class<T> clazz, String sql, Object...objs){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();
            //预编译
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1,objs[i]);
            }
            //执行，获取结果集
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取结果集的列数
            int columnCount = metaData.getColumnCount();
            list = new ArrayList<T>();
            while(rs.next()){
                T t = clazz.getDeclaredConstructor().newInstance();
                //处理一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);
                    //获取列的别名
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);

                }

                list.add(t);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps,rs);
        }

        return null;
    }

}
