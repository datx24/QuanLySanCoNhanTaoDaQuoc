package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.FieldDTO;
import Enum.FieldStatus;

public class FieldDAL {
	//Thêm sân
	public boolean addField(FieldDTO field) {
		return true;
	}
	//Cập nhật thông tin sân
	public boolean updateField(FieldDTO field) {
		return true;
	}
	
	//Xóa sân
	public boolean editField(String fieldId) {
		return true;
	}
	
	//Lấy tất cả sân bóng từ cơ sở dữ liệu
	public List<FieldDTO> getAllFields() {
		List<FieldDTO> fields = new ArrayList<>();
		return fields;
	}
	
	// Tìm kiếm sân theo status
	public boolean getFieldsByStatus(FieldStatus status) {
		return true;
	}
}
