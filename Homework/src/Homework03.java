import org.junit.Test;
import util.JDBCutil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Homework03 {


    public static void main(String[] args) {
    }


    /**
     *
     *通用的查询操作
     * @param sql
     * @param objs
     * @throws Exception
     */
    public static <T> List<T> select(Class<T> clazz, String sql, Object...objs){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCutil.getConnextion();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1,objs[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<T> list = new ArrayList<T>();
            while (rs.next()){
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
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
            JDBCutil.close(conn,ps,rs);
        }

        return null;

    }


    public static <T> T getInformation(Class<T> clazz, String sql, Object...obj){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JDBCutil.getConnextion();
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
            JDBCutil.close(conn,ps,rs);
        }

        return null;
    }
}
