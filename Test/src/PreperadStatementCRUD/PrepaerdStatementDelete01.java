package PreperadStatementCRUD;

import PreperadStatementCRUD.Util.JDBCUtils;

public class PrepaerdStatementDelete01 {
    public static void main(String[] args) {

        String sql = "delete from customers where id = ?";
        JDBCUtils.alter(sql,23);
    }
}
