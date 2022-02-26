package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Order;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PrepaerdStatementSelect04 {
    public static void main(String[] args) {
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order`";
        queryForOrder(sql);
    }


    /**针对于order表的通用查询操作
     * @param sql
     * @param obj
     */
    public static void queryForOrder(String sql,Object...obj){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //连接
            conn = JDBCUtils.getConnection();
            //预编译
            ps = conn.prepareStatement(sql);
            //填充占位符
            for(int i = 0;i < obj.length;i++){
                ps.setObject(i + 1,obj[i]);
            }
            //执行，获取结果集
            resultSet = ps.executeQuery();
            //获取字符集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取字符集列数
            int columnCount = metaData.getColumnCount();
            //处理结果集
            while(resultSet.next()){
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获取字段值
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取字段名
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //反射修改属性值
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,columnValue);

                }
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps,resultSet);
        }


    }
}
