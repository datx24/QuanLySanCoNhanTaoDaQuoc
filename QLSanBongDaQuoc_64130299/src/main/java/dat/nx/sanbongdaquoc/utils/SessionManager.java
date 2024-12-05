package dat.nx.sanbongdaquoc.utils;

import dat.nx.sanbongdaquoc.models.entities.UserDTO;

public class SessionManager {
	private static UserDTO currentUser;
	//Lấy thông tin người dùng đang đăng nhập
	public static UserDTO getCurrentUser() {
		return currentUser;
	}
	//Gán thông tin người dùng đang đăng nhập
	public static void setCurrentUser(UserDTO currentUser) {
		SessionManager.currentUser = currentUser;
	}
	
	//Xóa thông tin phiên đăng nhập
	public static void logout() {
		currentUser = null;
	}
	
	//Kiểm tra trạng thái đăng nhập
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
}
