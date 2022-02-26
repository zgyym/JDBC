
import org.junit.Test;
import util.JDBCutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Scanner;

public class Homework01 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = s.nextLine();
        System.out.print("请输入邮箱：");
        String email = s.nextLine();
        System.out.print("请输入生日：");
        String birth = s.nextLine();

        String sql = "insert into customers (name,email,birth) values (?,?,?)";

        int index = update(sql, name, email, birth);

        if(index == 0){
            System.out.println("失败");
        }else{
            System.out.println("成功");
        }


    }

    /**
     *
     * 通用的增删改操作
     */
    public static int update(String sql,Object...objs){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCutil.getConnextion();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1,objs[i]);
            }
            //ps.execute();
            //return 1;
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutil.close(conn,ps);
        }

        return 0;

    }
}
