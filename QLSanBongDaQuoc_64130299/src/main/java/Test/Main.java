package Test;

import java.sql.Connection;
import java.sql.SQLException;

import Utils.DatabaseConnection;

public class Main {
	  public static void main(String[] args) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            if (connection != null) {
	                System.out.println("Kết nối cơ sở dữ liệu thành công!");
	            } else {
	                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
	            }
	        } catch (SQLException e) {
	            System.err.println("Đã xảy ra lỗi khi kết nối cơ sở dữ liệu:");
	            e.printStackTrace();
	        }
	    }
}
