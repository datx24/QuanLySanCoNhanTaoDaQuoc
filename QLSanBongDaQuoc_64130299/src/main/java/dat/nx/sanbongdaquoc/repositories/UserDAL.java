package dat.nx.sanbongdaquoc.repositories;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.*;

public class UserDAL {
	private UserDTO userDTO = new UserDTO();
	private List<UserDTO> userDTOs = new ArrayList<UserDTO>();

	// thêm người dùng
	public boolean insertUser(UserDTO user) {
	    String query = "INSERT INTO users_64130299 (FullName, Email, PhoneNumber, PasswordHash, isAdmin) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement psmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        psmt.setString(1, user.getFullName());
	        psmt.setString(2, user.getEmail());
	        psmt.setString(3, user.getPhoneNumber());

	        // Nếu mật khẩu không tồn tại, đặt NULL và isAdmin = 0 (khách hàng)
	        if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
	            psmt.setString(4, user.getPasswordHash());
	            psmt.setInt(5, 1); // Quản lý
	        } else {
	            psmt.setNull(4, Types.VARCHAR);
	            psmt.setInt(5, 0); // Khách hàng
	        }

	        psmt.executeUpdate();

	        // Lấy ID của người dùng vừa được tạo
	        try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                String id = generatedKeys.getString(1);
	                user.setUserID(id); // Gán ID vào đối tượng user
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
		    return false;
	    }
        return true;
	}

	// cập nhật thông tin người dùng
	public boolean updateUser(UserDTO user) {
		String query = "UPDATE users_64130299 SET FullName = ?, Email = ?, PhoneNumber = ? WHERE UserID = ?";
		
		//Kết nối cơ sở dữ liệu và thực thi câu lệnh
		try(PreparedStatement psmt = DatabaseConnection.getConnection().prepareStatement(query)) {
			psmt.setString(1,user.getFullName());
			psmt.setString(2,user.getEmail());
			psmt.setString(3,user.getPhoneNumber());
			psmt.setString(4,user.getUserID());
			
		//Thực thi câu lệnh và kiểm tra kết quả
		int rowsAffected = psmt.executeUpdate();
		return rowsAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// xóa người dùng
	public boolean deleteUser(UserDTO user) {
		String query = "DELETE FROM users_64130299 WHERE UserID = ?";
		try(Connection conn = DatabaseConnection.getConnection();
			PreparedStatement psmt = conn.prepareStatement(query)) {
			psmt.setString(1, user.getUserID());
			int rowsAffected  = psmt.executeUpdate();
			
			return rowsAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// Lấy tất cả người dùng từ cơ sở dữ liệu
	public List<UserDTO> getAllUsers() {
	    String query = "SELECT * FROM users_64130299 WHERE IsAdmin = 0";
	    List<UserDTO> userDTOs = new ArrayList<>();
	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            String userID = rs.getString("UserID");
	            String fullName = rs.getString("FullName");
	            String email = rs.getString("Email");
	            String phoneNumber = rs.getString("PhoneNumber");
	            String passwordHash = rs.getString("PasswordHash");
	            boolean isAdmin = rs.getBoolean("IsAdmin");
	            Timestamp createdAt = rs.getTimestamp("CreatedAt");
	            Timestamp updatedAt = rs.getTimestamp("UpdatedAt");
	            userDTOs.add(new UserDTO(userID, fullName, email, phoneNumber, passwordHash, isAdmin, createdAt, updatedAt));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return userDTOs;
	}


	// Lấy thông tin người dùng dựa vào ID
	public UserDTO getUserByID(String userID) {
		String query = "SELECT * From users_64130299 where UserID = ?";
		try(Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, userID);
			ResultSet resultSet  = preparedStatement.executeQuery();
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

	// Lấy thông tin danh sách người dùng dựa vào tên
	public List<UserDTO> getUserByName(String name) {
		String query = "SELECT * FROM users_64130299 WHERE FullName LIKE ?";
		
		try(Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			//Thêm ký tự đại diện cho tham số
			preparedStatement.setString(1, "%" + name + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				//Tạo đối tượng và thêm vào danh sách
				userDTO.setUserID(resultSet.getString("UserID"));
				userDTO.setFullName(resultSet.getString("FullName"));
				userDTO.setEmail(resultSet.getString("Email"));
				userDTO.setPhoneNumber(resultSet.getString("PhoneNumber"));
				userDTOs.add(userDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi không thể tìm kiếm tên người dùng !");
		}
		return userDTOs;
	}

	// đăng nhập
	public boolean login(String email, String plainPassword) {
		// Mã hóa kiểu SHA 256 khi người dùng nhập vào
		String passwordHash = CommonUtils.encodePassword(plainPassword);

		// Thay đổi tên cột nếu cần thiết
		String sql = "SELECT COUNT(*) FROM users_64130299 WHERE Email = ? AND PasswordHash = ?";
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
	
	//Kiểm tra email hoặc số điiện thoại đã tồn tại chưa
	public boolean isEmailPhoneExist(String email, String phoneNumber) {
        String query = "SELECT COUNT(*) FROM users_64130299 WHERE Email = ? OR PhoneNumber = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, phoneNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	//Kiểm tra xem mật khẩu đã tồn tại chưa
	public boolean isPasswordExist(String plainPassword) {
        String hashedPassword = CommonUtils.encodePassword(plainPassword); // Mã hóa mật khẩu

        String query = "SELECT COUNT(*) FROM users_64130299 WHERE PasswordHash = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu tìm thấy mật khẩu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	// Tìm kiếm người dùng bằng email
	public String getUserIdByEmail(String email) {
	    String sql = "SELECT UserID FROM users_64130299 WHERE Email = ?";
	    String userId = null;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, email);  // Truy vấn bằng email

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            userId = rs.getString("UserID");  // Lấy UserID dưới dạng String
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userId;
	}
}
