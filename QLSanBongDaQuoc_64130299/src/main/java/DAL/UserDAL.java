package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.UserDTO;

public class UserDAL {
	private UserDTO user = new UserDTO();
	private List<UserDTO> users = new ArrayList();
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
		return users;
	}
	
	// Lấy thông tin người dùng dựa vào ID
	public UserDTO getUserByID(String UserID) {
		return user;
	}
	
	// Lấy thông tin người dùng dựa vào email
	public UserDTO getUserByEmail(String email) {
		return user;
	}

	// đăng nhập
	public boolean login(String email, String passwordHash) {
		return true;
	}

	// đăng kí
	public boolean register(String fullName, String email, String username, String password, String phoneNumber,
			String passwordHash) {
		return true;
	}
}
