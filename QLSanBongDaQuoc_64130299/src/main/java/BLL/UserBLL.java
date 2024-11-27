package BLL;

import DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;
import Utils.CommonUtils;

import DAL.UserDAL;

public class UserBLL {
	private UserDAL userDAL = new UserDAL();
	
	//Xử lý dữ liệu đầu vào trước khi gọi DAL thêm người dùng
	public boolean addUser(UserDTO userDTO) {
		return userDAL.insertUser(userDTO);
	}
	
	//Kiểm tra dữ liệu hợp lệ trước khi gọi DAL để cập nhật thông tin người dùng
	public boolean updateUser(UserDTO userDTO) {
		return userDAL.updateUser(userDTO);
	}
	
	//Thực hiện kiểm tra hoặc xác nhận trước khi xóa người dùng
	public boolean deleteUser(UserDTO userDTO) {
		return userDAL.deleteUser(userDTO);
	}
	
	//Lấy danh sách từ DAL và có thể áp dụng bộ lọc hoặc sắp xếp nếu cần
	public List<UserDTO> getAllUsers() {
		return userDAL.getAllUsers();
	}
	
	//Tìm người dùng theo ID
	public UserDTO getUserByID(String userID) {
		return userDAL.getUserByID(userID);
	}
	
	//Tìm người dùng theo email
	public UserDTO getUserByEmail(String email) {
		return userDAL.getUserByEmail(email);
	}
	
	//Kiểm tra thông tin đăng nhập
	public boolean checkLogin(String email, String password) {
		//Kiểm tra tính hợp lệ của email
		if (!CommonUtils.isValidEmail(email)) {
            System.out.println("Email không hợp lệ");
            return false;
        }
		
		//Kiểm tra tính hợp lệ mật khẩu
		if (!CommonUtils.isValidPassword(password)) {
            System.out.println("Mật khẩu không hợp lệ");
            return false;
        }
		//Goijlogin để kiểm tra đăng nhập
		boolean isValidUser = userDAL.login(email, password);
		// Nếu thông tin đăng nhập đúng
        if (isValidUser) {
            return true;
        } else {
            System.out.println("Tên đăng nhập hoặc mật khẩu không chính xác.");
            return false;
        }
	}
	
	//Kiểm tra thông tin đăng kí
	public boolean checkRegister(UserDTO userDTO) {
		return userDAL.register(
                userDTO.getFullName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getPasswordHash(),
                userDTO.getCreatedAt(),
                userDTO.getUpdatedAt()
        );
	}
	
	//Tìm danh sách người dùng dựa trên nhiều tiêu chí
	public List<UserDTO> searchUsers(String keyword) {
		return getAllUsers();//Xử lý thêm ở phần trả về để lọc
	}
}
