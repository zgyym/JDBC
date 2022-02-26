import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
        System.out.println(resourceAsStream);
    }
}
