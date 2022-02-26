package Blob;

import PreperadStatementCRUD.Util.JDBCUtils;
import PreperadStatementCRUD.bean.Customer;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class BlobTest01 {
    @Test
    public void insertForBlob(){
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers (name,email,birth,photo) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            //获取绝对路径，文件必须放在类路径下
            //其他情况默认当前路径可以是项目根目录
            String path = Thread.currentThread().getContextClassLoader().getResource("1.jpg").getPath();
            fis = new FileInputStream(new File(path));
            ps.setObject(1,"yanmo");
            ps.setObject(2,"312312213@qq.com");
            ps.setObject(3,"1999-11-07");
            ps.setBlob(4,fis);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn,ps);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    @Test
    public void selectForBlob(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,32);
            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date date = rs.getDate("birth");

                Customer customer = new Customer(id,name,email,date);
                System.out.println(customer);


                //将Blob类型的字段下载下来，以文件的方式保存在本地
                //下载到模块根目录下
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("yammo.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while((len =is.read(buffer)) != -1){
                    fos.write(buffer,0,len);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(fos != null){
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JDBCUtils.closeResources(conn,ps,rs);
    }



}
