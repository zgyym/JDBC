import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.junit.Test;

import java.util.Scanner;

public class Homework02 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("四级/六级：");
        int type = s.nextInt();

        System.out.print("身份证号：");
        String idCard = s.next();

        System.out.print("准考证号：");
        String examCard = s.next();

        System.out.print("学生姓名：");
        String studentName = s.next();

        System.out.print("所在城市：");
        String location = s.next();

        System.out.print("考试成绩：");
        int grade = s.nextInt();


        String sql = "insert into examstudent (Type,IDCard,ExamCard,StudentName,Location,Grade) values (?,?,?,?,?,?)";
        int index = Homework01.update(sql, type, idCard, examCard, studentName, location, grade);
        if (index == 0){
            System.out.println("失败");
        }else{
            System.out.println("成功");
        }





    }






}
