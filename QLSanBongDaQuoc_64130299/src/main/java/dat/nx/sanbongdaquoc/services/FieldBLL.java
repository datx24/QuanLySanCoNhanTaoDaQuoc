package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.util.List;

public class FieldBLL {
	private FieldDAL fieldDAL;
	
	public FieldBLL(FieldDAL fieldDAL) {
		this.fieldDAL = new FieldDAL();
	}

	//Kiểm tra tính hợp lệ của thông tin sân bóng trước khi gọi phương thức thêm
	public boolean addField(FieldDTO fieldDTO) {
		return fieldDAL.addField(fieldDTO);
	}
	
	//Kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi cập nhật
	public boolean updateField(FieldDTO fieldDTO) {
		return fieldDAL.updateField(fieldDTO);
	}
	
	//Thực hiện kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi xóa
	public boolean deleteField(String fieldID) {
	    FieldDTO fieldDTO = getFieldByID(fieldID);
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
