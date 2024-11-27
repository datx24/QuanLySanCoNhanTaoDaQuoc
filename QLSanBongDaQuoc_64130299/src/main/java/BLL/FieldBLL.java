package BLL;

import DTO.FieldDTO;

import java.util.List;

import DAL.FieldDAL;
import Enum.FieldStatus;

public class FieldBLL {
	private FieldDAL fieldDAL = new FieldDAL();
	//Kiểm tra tính hợp lệ của thông tin sân bóng trước khi gọi phương thức thêm
	public boolean addField(FieldDTO fieldDTO) {
		return true;
	}
	
	//Kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi cập nhật
	public boolean updateField(FieldDTO fieldDTO) {
		return true;
	}
	
	//Thực hiện kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi xóa
	public boolean deleteField(String fieldID) {
		return true;
	}
	
	//Lấy danh sách sân bóng, có thế áp dụng bộ lọc hoặc sắp xếp nếu cần
	public List<FieldDTO> getAllFields() {
		return fieldDAL.getAllFields();
	}
	
	//Tìm kiếm sân bóng theo ID
	public FieldDTO getFieldByID(String fieldID) {
		return fieldDAL.getFieldByID(fieldID);
	}
	
	//Tìm kiếm sân bóng theo trạng thái
	public List<FieldDTO> getFieldsByStatus(FieldStatus status) {
		return fieldDAL.getAllFields(); // cài đặt thêm phần trả về
	}
	
}
