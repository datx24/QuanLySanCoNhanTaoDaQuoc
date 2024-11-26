package DAL;

import DTO.UserDTO;

public class UserDAL {
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

	// đăng nhập
	public boolean login(String email,String passwordHash) {
		return true;
	}

	// đăng kí
	public boolean register(String fullName,String email,String username,
			String password,String phoneNumber,String passwordHash) {
		return true;
	}
}
