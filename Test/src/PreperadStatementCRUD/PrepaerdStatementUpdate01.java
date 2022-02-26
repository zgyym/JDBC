package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PrepaerdStatementUpdate01 {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();

            //2、预编译sql语句，返回PreparedStatement对象
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);

            //3、填充占位符
            ps.setString(1,"莫扎特");
            ps.setInt(2,18);

            //4、执行sql语句
            ps.execute();

            //关闭资源
            //JDBCUtils.closeResources(conn,ps);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResources(conn,ps);
        }
    }
}
