package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;

public class Test {
    public static void main(String[] args) {
        String sql = "update `order` set order_name = ? where order_id = ?";
        JDBCUtils.alter(sql,"DD",2);
    }
}
