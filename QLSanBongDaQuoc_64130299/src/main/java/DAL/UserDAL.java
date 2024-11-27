package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DTO.UserDTO;
import Utils.CommonUtils;
import Utils.DatabaseConnection;

public class UserDAL {
	private UserDTO userDTO = new UserDTO();
	private List<UserDTO> userDTOs = new ArrayList();

	// thêm người dùng
	public boolean insertUser(UserDTO user) {
		return true;
	}

	// xóa người dùng
	public boolean updateUser(UserDTO user) {
		return true;
	}

	// cập nhật người dùng
	public boolean deleteUser(UserDTO user) {
		return true;
	}

	// Lấy tất cả người dùng từ cơ sở dữ liệu
	public List<UserDTO> getAllUsers() {
		return userDTOs;
	}

	// Lấy thông tin người dùng dựa vào ID
	public UserDTO getUserByID(String UserID) {
		return userDTO;
	}

	// Lấy thông tin người dùng dựa vào email
	public UserDTO getUserByEmail(String email) {
		return userDTO;
	}

	// đăng nhập
	public boolean login(String email, String plainPassword) {
		//Mã hóa kiểu SHA 256 khi người dùng nhập vào
		String passwordHash = CommonUtils.encodePassword(plainPassword);
		String sql = "SELECT COUNT(*) FROM users_64130299 WHERE email = ? AND password = ?";
		try (Connection conn = DatabaseConnection.getConnection(); // Kết nối từ DatabaseConnection
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			stmt.setString(2, passwordHash);

			ResultSet rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// đăng kí
	public boolean register(String fullName, String email, String phoneNumber, String passwordHash, Timestamp createdAt,
			Timestamp updatedAt) {
		return true;
	}

}
