package dat.nx.sanbongdaquoc.DAL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.DTO.*;
import dat.nx.sanbongdaquoc.utils.*;

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
		String query = "SELECT * FROM users_64130299 ";
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String userID = rs.getString("UserID");
				String fullName = rs.getString("FullName");
				String email = rs.getString("Email");
				String phoneNumber = rs.getString("PhoneNumber");
				String passwordHash = rs.getString("PasswordHash");
				boolean idAdmin = rs.getBoolean("IsAdmin");
				Timestamp createdAt = rs.getTimestamp("CreatedAt");
				Timestamp updatedAt = rs.getTimestamp("UpdatedAt");
				userDTOs.add(new UserDTO(userID,fullName,email,phoneNumber,passwordHash,idAdmin, createdAt,updatedAt));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDTOs;
	}

	// Lấy thông tin người dùng dựa vào ID
	public UserDTO getUserByID(String userID) {
		String query = "SELECT * From users_64130299 where UserID = ?";
		Connection connection;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			connection = DatabaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userID);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				userDTO.setUserID(resultSet.getString("UserID"));
				userDTO.setFullName(resultSet.getString("FullName"));
				userDTO.setEmail(resultSet.getString("Email"));
				userDTO.setPhoneNumber(resultSet.getString("PhoneNumber"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDTO;
	}

	// Lấy thông tin người dùng dựa vào email
	public UserDTO getUserByEmail(String email) {
		return userDTO;
	}

	// đăng nhập
	public boolean login(String email, String plainPassword) {
		// Mã hóa kiểu SHA 256 khi người dùng nhập vào
		String passwordHash = CommonUtils.encodePassword(plainPassword);

		// Thay đổi tên cột nếu cần thiết
		String sql = "SELECT COUNT(*) FROM users_64130299 WHERE email = ? AND passwordHash = ?";
		try (Connection conn = DatabaseConnection.getConnection(); // Kết nối từ DatabaseConnection
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			stmt.setString(2, passwordHash);

			ResultSet rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true; // Đăng nhập thành công
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Đăng nhập thất bại
	}

	// đăng kí
	public boolean register(String fullName, String email, String phoneNumber, String passwordHash, Timestamp createdAt,
			Timestamp updatedAt) {
		return true;
	}

}
