package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PrepaerdStatementSelect02 {
    public static void main(String[] args) {

        String sql = "select id,name,email,birth from customers";
        queryForCustomers(sql);

    }


    /**
     * 针对于customers表的通用查询操作
     * @param sql
     * @param obj
     */
    public static void queryForCustomers(String sql,Object...obj){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();
            //预编译
            ps = conn.prepareStatement(sql);
            //填充占位符
            for(int i = 0;i < obj.length;i++){
                ps.setObject(i + 1,obj[i]);
            }
            //执行,获取结果集
            rs = ps.executeQuery();
            //获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData metaData = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            while(rs.next()){
                Customer customer = new Customer();

                ///处理结果集一行数据中的每一个列
                for(int i = 0;i < columnCount;i++){
                    //获取结果集中各列的值
                    Object columnValue = rs.getObject(i + 1);

                    //获取每一列的列名
                    String columnName = metaData.getColumnName(i + 1);
                    //获取列的别名
                    //String columnLabel = metaData.getColumnLabel(i + 1);

                    //数据库中表的列名与对应类中的属性同名
                    //通过反射机制来对cusromer对象的属性赋值
                    //给customer对象的columnName属性赋值为columnValue值

                    //找到Customer类中叫catalogName的属性
                    //Customer.class
                    Field field = Customer.class.getDeclaredField(columnName);
                    //访问私有属性
                    field.setAccessible(true);
                    //修改属性的值
                    field.set(customer,columnValue);

                }
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
