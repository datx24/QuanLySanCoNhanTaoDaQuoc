package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.FieldDTO;
import Enum.FieldStatus;

public class FieldDAL {
	private FieldDTO field = new FieldDTO();
	private List<FieldDTO> fields = new ArrayList<>();
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
		return fields;
	}
	
	//Lấy sân bóng theo id
	public FieldDTO getFieldByID(String fieldID) {
		return field;
	}
	
	
	// Tìm kiếm sân theo status
	public FieldDTO getFieldsByStatus(FieldStatus status) {
		return field;
	}
}
