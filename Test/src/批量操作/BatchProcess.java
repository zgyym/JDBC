package 批量操作;

import PreperadStatementCRUD.Util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BatchProcess {


    /**
     *
     * 批量操作一般是指批量插入
     */
    @Test
    public void insert01(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);
            for(int i = 1;i <= 20000;i++){
                ps.setObject(1,"name_" + i);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps);
        }

    }


    /**
     *批量插入的优化：减少IO次数
     * 1.addBatch()、executeBatch()、clearBatch()
     * 2.mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
     * 		 ?rewriteBatchedStatements=true 写在配置文件的url后面
     * 3.使用更新的mysql 驱动：mysql-connector-java-5.1.37-bin.jar
     */
    @Test
    public void insert02(){
        Connection conn = null;
        PreparedStatement ps = null;

        long strat = System.currentTimeMillis();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);
            for(int i = 1;i <= 1000000;i++){
                ps.setObject(1,"name_" + i);


                //1、攒sql
                ps.addBatch();

                if(i % 5000 == 0){
                    //2、执行batch
                    ps.executeBatch();

                    //清空batch
                    ps.clearBatch();
                }

            }

            long end = System.currentTimeMillis();
            System.out.println(end - strat);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps);
        }
    }


    /**
     *最终版：设置连接不允许自动提交数据
     * conn.
     */
    @Test
    public void insert03(){
        Connection conn = null;
        PreparedStatement ps = null;


        try {
            long strat = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();

            //设置不允许自动提交数据
            conn.setAutoCommit(false);

            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);
            for(int i = 1;i <= 1000000;i++){
                ps.setObject(1,"name_" + i);


                //1、攒sql
                ps.addBatch();

                if(i % 5000 == 0){
                    //2、执行batch
                    ps.executeBatch();

                    //清空batch
                    ps.clearBatch();
                }

            }

            //提交数据
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println(end - strat);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps);
        }
    }
}
