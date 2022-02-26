package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SelectSummary01 {
    public static void main(String[] args) {
        String sql = "select id,name,email from customers where id = ?";
        Customer customer = getInformation(Customer.class,sql, 1);
        System.out.println(customer);
    }


    /**
     *使用PreparedStatement实现针对不同表的通用的查询操作
     *返回表中的一条记录
     */

    public static <T> T getInformation(Class<T> clazz, String sql, Object...obj){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();
            //预编译
            ps = conn.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1,obj[i]);
            }

            //执行，获取结果集
            rs = ps.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取结果集的列数
            int columnCount = metaData.getColumnCount();

            if (rs.next()){
                //创建对象
                T t = clazz.getDeclaredConstructor().newInstance();
                //处理结果集中一行数据的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);
                    //获取列的别名
                    String columnLabel = metaData.getColumnLabel(i + 1);


                    //对t对象的指定属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps,rs);
        }



        return null;


    }
}
