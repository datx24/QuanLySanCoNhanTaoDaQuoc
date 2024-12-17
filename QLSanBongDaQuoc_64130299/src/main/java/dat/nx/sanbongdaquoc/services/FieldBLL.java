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
	    // Kiểm tra tính hợp lệ của thông tin sân
	    if (fieldDTO.getFieldName().isEmpty() || fieldDTO.getPricePerHour() == null || fieldDTO.getDescription().isEmpty()) {
	        return false; // Nếu thông tin không hợp lệ, trả về false
	    }
	    // Thực hiện thêm sân bóng vào cơ sở dữ liệu
	    return fieldDAL.insertField(fieldDTO);
	}

	
	//Kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi cập nhật
	public boolean updateField(FieldDTO fieldDTO) {
	    // Kiểm tra tính hợp lệ của thông tin sân
	    if (fieldDTO.getFieldID() <= 0 || fieldDTO.getFieldName().isEmpty() || fieldDTO.getPricePerHour() == null || fieldDTO.getDescription().isEmpty()) {
	        return false; // Nếu thông tin không hợp lệ, trả về false
	    }

	    // Kiểm tra xem sân bóng có tồn tại trong cơ sở dữ liệu không (dựa vào FieldID)
	    if (getFieldByID(fieldDTO.getFieldID()) == null) {
	        return false; // Nếu sân bóng không tồn tại, không thể cập nhật
	    }

	    // Thực hiện cập nhật sân bóng vào cơ sở dữ liệu
	    return fieldDAL.updateField(fieldDTO);
	}
	
	//Thực hiện kiểm tra xem sân bóng có tồn tại không (dựa vào id) trước khi xóa
	public boolean deleteField(int id) {
	    // Kiểm tra nếu sân bóng tồn tại trước khi xóa
	    FieldDTO fieldDTO = getFieldByID(id);

	    // Nếu không tìm thấy sân bóng với FieldID, trả về false
	    if (fieldDTO == null) {
	        return false; // Không thể xóa vì sân không tồn tại
	    }

	    // Thực hiện xóa sân bóng khỏi cơ sở dữ liệu
	    return fieldDAL.deleteField(fieldDTO);
	}
	
	//Lấy danh sách sân bóng, có thế áp dụng bộ lọc hoặc sắp xếp nếu cần
	public List<FieldDTO> getAllFields() {
		return fieldDAL.getAllFields();
	}
	
	//Tìm kiếm sân bóng theo ID
	public FieldDTO getFieldByID(int fieldID) {
		return fieldDAL.getFieldByID(fieldID);
	}
	
	// Phương thức lọc sân theo trạng thái
    public List<FieldDTO> getFieldsByStatus(FieldStatus status) {
        return fieldDAL.getFieldsByStatus(status);  // Trả về danh sách các sân theo trạng thái
    }
}
