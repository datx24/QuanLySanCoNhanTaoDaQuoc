package DAL;

import java.util.List;

import DTO.FieldDTO;

public class FieldDAL {
	//Thêm sân
	public boolean addField() {
		return true;
	}
	//Cập nhật thông tin sân
	public boolean updateField() {
		return true;
	}
	
	//Xóa sân
	public boolean editField() {
		return true;
	}
	
	//Lấy tất cả sân bóng từ cơ sở dữ liệu
	public boolean getAllFields() {
		return true;
	}
	// enum đại diện trạng thái sân bóng
	public enum FieldStatus {
	    AVAILABLE, MAINTENANCE, BOOKED
	}
	
	// Tìm kiếm sân theo status
	public boolean getFieldsByStatus(FieldStatus status) {
		return true;
	}
}
