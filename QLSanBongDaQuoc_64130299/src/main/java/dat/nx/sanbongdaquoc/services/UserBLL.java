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
		
		if(!CommonUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
			System.out.println("Số điện thoại không hợp lệ !");
		}
		
		//Chuẩn hóa dữ liệu
		userDTO.setFullName(userDTO.getFullName().trim());
		return userDAL.insertUser(userDTO);
	}
	
	//Kiểm tra dữ liệu hợp lệ trước khi gọi DAL để cập nhật thông tin người dùng
	public boolean updateUser(UserDTO userDTO) {
		if(userDTO.getFullName() == null || userDTO.getFullName().trim().isEmpty()) {
			System.out.println("Tên người dùng không được để trống !");
			return false;
		}
		
		if(!CommonUtils.isValidEmail(userDTO.getEmail())) {
			System.out.println("Email không hợp lệ");
			return false;
		}
		
		if(!CommonUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
			System.out.println("Số điện thoại không hợp lệ");
			return false;
		}
		return userDAL.updateUser(userDTO);
	}
	
	//Thực hiện kiểm tra hoặc xác nhận trước khi xóa người dùng
	public boolean deleteUser(UserDTO userDTO) {
		// Kiểm tra nếu người dùng không tồn tại
		if(userDTO == null) {
			System.out.println("Người dùng không tồn tại");
		}
		
		//Kiểm tra nếu người dùng là admin thì không được xóa
		if(userDTO.isAdmin() == true) {
			System.out.println("Không thể xóa người dùng admin");
		}
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
	
	//Tìm người dùng theo tên
	public List<UserDTO> searchUserByName(String name) {
		if(name == null || name.isEmpty()) {
			System.out.println("Tên không được để trống");
		}
		return userDAL.getUserByName(name);
	}
	
	//Kiểm tra thông tin đăng nhập
	public boolean checkLogin(String email, String password) {
	    // Kiểm tra tính hợp lệ của email
	    if (!CommonUtils.isValidEmail(email)) {
	        System.out.println("Email không hợp lệ");
	        return false;
	    }

	    // Kiểm tra tính hợp lệ mật khẩu
	    if (!CommonUtils.isValidPassword(password)) {
	        System.out.println("Mật khẩu không hợp lệ");
	        return false;
	    }

	    // Gọi login để kiểm tra đăng nhập
	    boolean isValidUser = userDAL.login(email, password);
	    // Nếu thông tin đăng nhập đúng
	    if (isValidUser) {
	        // Lấy userId từ email
	        String userId = userDAL.getUserIdByEmail(email);

	        // Lấy thông tin người dùng từ cơ sở dữ liệu bằng userId
	        UserDTO currentUser = getUserByID(userId);

	        // Lưu thông tin người dùng
	        SessionManager.setCurrentUser(currentUser);
	        System.out.println("Đăng nhập thành công. Xin chào: " + currentUser.getFullName());
	        return true;
	    } else {
	        System.out.println("Tên đăng nhập hoặc mật khẩu không chính xác.");
	        return false;
	    }
	}
	
	 //Kiểm tra thông tin người dùng (email, số điện thoại, mật khẩu)
	 public boolean isUserInfoValid(String email, String phoneNumber, String plainPassword) {
	        // Mã hóa mật khẩu
	        String hashedPassword = CommonUtils.encodePassword(plainPassword);

	        // Kiểm tra email hoặc số điện thoại đã tồn tại
	        if (userDAL.isEmailPhoneExist(email, phoneNumber)) {
	            return false; // Email hoặc số điện thoại đã tồn tại
	        }

	        // Kiểm tra mật khẩu đã tồn tại
	        if (userDAL.isPasswordExist(hashedPassword)) {
	            return false; // Mật khẩu đã tồn tại
	        }

	        return true; // Thông tin hợp lệ
	    }
	 
	 //Phương thức đăng kí
	 public boolean registerUser(String fullName, String email, String phoneNumber, String plainPassword) {
	     // Kiểm tra tính hợp lệ của số điện thoại
	     if (!CommonUtils.isValidPhoneNumber(phoneNumber)) {
	         System.out.println("[DEBUG] Số điện thoại không hợp lệ: " + phoneNumber);
	         return false;
	     }
	     
	     // Kiểm tra tính hợp lệ của email
	     if (!CommonUtils.isValidEmail(email)) {
	         System.out.println("[DEBUG] Email không hợp lệ: " + email);
	         return false;
	     }

	     // Kiểm tra tính hợp lệ mật khẩu
	     if (!CommonUtils.isValidPassword(plainPassword)) {
	         System.out.println("[DEBUG] Mật khẩu không hợp lệ: " + plainPassword);
	         return false;
	     }
	     
	     // Kiểm tra dữ liệu đầu vào
	     if (!isUserInfoValid(email, phoneNumber, plainPassword)) {
	         System.out.println("[DEBUG] Thông tin người dùng không hợp lệ. Email hoặc số điện thoại đã tồn tại: "
	                 + "Email=" + email + ", PhoneNumber=" + phoneNumber);
	         return false;
	     }

	     // Tạo đối tượng UserDTO
	     UserDTO newUser = new UserDTO();
	     newUser.setFullName(fullName);
	     newUser.setEmail(email);
	     newUser.setPhoneNumber(phoneNumber);
	     newUser.setPasswordHash(CommonUtils.encodePassword(plainPassword));

	     // Debug thông tin UserDTO
	     System.out.println("[DEBUG] Đối tượng UserDTO được tạo: " + newUser);

	     // Gọi phương thức DAL để lưu người dùng
	     boolean isInserted = userDAL.insertUser(newUser);
	     if (isInserted) {
	    	 System.out.println(isInserted);
	         System.out.println("[DEBUG] Người dùng đã được thêm thành công vào cơ sở dữ liệu: " + newUser);
	     } else {
	         System.out.println("[DEBUG] Thêm người dùng vào cơ sở dữ liệu thất bại.");
	     }
	     return isInserted;
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
