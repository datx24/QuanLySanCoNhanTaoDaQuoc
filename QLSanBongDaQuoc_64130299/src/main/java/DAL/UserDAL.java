package DAL;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DTO.UserDTO;

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
	public boolean login(String email, String passwordHash) {
		return true;
	}

	// đăng kí
	public boolean register(String fullName, String email, String phoneNumber, String passwordHash, 
            Timestamp createdAt, Timestamp updatedAt) {
		return true;
	}
	
}
