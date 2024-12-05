package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.util.ArrayList;
import java.util.List;
import dat.nx.sanbongdaquoc.utils.*;

public class UserBLL {
	private UserDAL userDAL = new UserDAL();
	
	//Xử lý dữ liệu đầu vào trước khi gọi DAL thêm người dùng
	public boolean addUser(UserDTO userDTO) {
		// Kiểm tra null
		if(userDTO == null) {
			System.out.println("Thông tin người dùng không được để trống");
		}
		
		//Kiểm tra các trường bắt buộc
		if(userDTO.getFullName() == null || userDTO.getFullName().trim().isEmpty()) {
			System.out.println("Tên không được để trống");
			return false;
		}
		
		if(!CommonUtils.isValidEmail(userDTO.getEmail())) {
			System.out.println("Email không hợp lệ !");
			return false;
		}
		if(!CommonUtils.isValidPassword(userDTO.getPasswordHash())) {
			System.out.println("Mật khẩu bắt buộc phải có độ dài từ 6 chữ số !");
		}
		
		if(!CommonUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
			System.out.println("Số điện thoại không hợp lệ !");
		}
		
		//Chuẩn hóa dữ liệu
		userDTO.setFullName(userDTO.getFullName().trim());
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
		//Goi login để kiểm tra đăng nhập
		boolean isValidUser = userDAL.login(email, password);
		// Nếu thông tin đăng nhập đúng
        if (isValidUser) {
        	//Lấy thông tin người dùng từ cơ sở dữ liệu
        	UserDTO currentUser = getUserByEmail(email);
        	//Lưu thông tin người dùng 
        	SessionManager.setCurrentUser(currentUser);
        	System.out.println("Đăng nhập thành công.Xin chào: " + currentUser.getFullName());
            return true;
        } else {
            System.out.println("Tên đăng nhập hoặc mật khẩu không chính xác.");
            return false;
        }
	}
	//Phương thức đăng xuất
	public boolean checkLogout() {
		if(SessionManager.isLoggedIn()) {
			SessionManager.logout();
			System.out.println("Đăng xuất thành công!");
			return true;
		} else {
			System.out.println("Bạn chưa đăng nhập!");
			return false;
		}
	}
	
	//Tìm danh sách người dùng dựa trên nhiều tiêu chí
	public List<UserDTO> searchUsers(String keyword) {
		return getAllUsers();//Xử lý thêm ở phần trả về để lọc
	}
}
