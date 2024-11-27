package BLL;

import DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;

import DAL.UserDAL;

public class UserBLL {
	UserDAL userDAL = new UserDAL();
	List<UserDAL> userDALs = new ArrayList<>();
	
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
	public boolean checkLogin(String email, String passwordHash) {
		return userDAL.login(email, passwordHash);
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
