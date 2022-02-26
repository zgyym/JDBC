package PreperadStatementCRUD.Util;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *操作数据库的工具类
 *
 */
public class JDBCUtils {


    /**
     * 获取数据库的连接
     * @return 获取的连接对象
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
        Properties prop = new Properties();
        prop.load(is);

        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String url = prop.getProperty("url");
        String driver = prop.getProperty("MySQLDriverClass");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,user,password);
        
        return conn;
    }


    /**
     * 关闭资源
     * @param conn 连接对象
     * @param ps preparedstatement对象
     */
    public static void closeResources(Connection conn, PreparedStatement ps){

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    /**
     * 关闭资源
     * @param conn 连接对象
     * @param ps preparedstatement对象
     */
    public static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs){

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    /**通用的增删改查操作
     * @param sql
     * @param obj
     */
    public static void alter(String sql,Object...obj){

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //连接数据库
            conn = getConnection();
            //预编译
            ps = conn.prepareStatement(sql);
            //填充占位符
            for(int i = 0;i < obj.length;i++){
                ps.setObject(i + 1,obj[i]);
            }
            //执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //关闭资源
            closeResources(conn,ps);
        }

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


    /**
     *
     * 使用PreparedStatement实现对不同表的查询操作
     * 返回多条记录
     */

    public static <T> List<T> getInformations(Class<T> clazz, String sql, Object...objs){
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
