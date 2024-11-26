package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Thông tin kết nối cơ sở dữ liệu MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlysanbong_64130299";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = ""; 

    // Phương thức kết nối cơ sở dữ liệu và trả về đối tượng Connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Kết nối đến cơ sở dữ liệu và trả về đối tượng Connection
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver MySQL JDBC", e);
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi kết nối tới cơ sở dữ liệu MySQL", e);
        }
    }
}