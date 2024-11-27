package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.FieldDTO;
import Enum.FieldStatus;

public class FieldDAL {
	private FieldDTO fieldDTO = new FieldDTO();
	private List<FieldDTO> fieldDTOs = new ArrayList<>();
	//Thêm sân
	public boolean addField(FieldDTO field) {
		return true;
	}
	//Cập nhật thông tin sân
	public boolean updateField(FieldDTO field) {
		return true;
	}
	
	//Chỉnh sửa sân
	public boolean editField(String fieldId) {
		return true;
	}
	
	//Xóa sân
	public boolean deleteField(FieldDTO fieldDTO) {
		return true;
	}
	
	//Lấy tất cả sân bóng từ cơ sở dữ liệu
	public List<FieldDTO> getAllFields() {
		return fieldDTOs;
	}
	
	//Lấy sân bóng theo id
	public FieldDTO getFieldByID(String fieldID) {
		return fieldDTO;
	}
	
	
	// Tìm kiếm sân theo status
	public FieldDTO getFieldsByStatus(FieldStatus status) {
		return fieldDTO;
	}
}
