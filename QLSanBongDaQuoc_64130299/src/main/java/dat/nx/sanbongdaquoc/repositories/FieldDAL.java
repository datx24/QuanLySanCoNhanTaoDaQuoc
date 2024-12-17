package dat.nx.sanbongdaquoc.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.DatabaseConnection;

public class FieldDAL {
	private FieldDTO fieldDTO = new FieldDTO();
	private List<FieldDTO> fieldDTOs = new ArrayList<>();
	private Connection connection;
	
	
	public FieldDAL(Connection connection) {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
		String query = "SELECT FieldID,FieldName,Status,PricePerHour,Description";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery()) {
			while(resultSet.next()) {
				int fieldID = resultSet.getInt("FieldID");
				String fieldName = resultSet.getString("FieldName");
				String statusString = resultSet.getString("Status");
				// chuyển đổi String sang enum
				FieldStatus status = FieldStatus.valueOf(statusString.toUpperCase()); 
				double pricePerHour	= resultSet.getDouble("PricePerHour");
				String description = resultSet.getString("Description");
				
				fieldDTOs.add(new FieldDTO(fieldID,fieldName,status,pricePerHour,description));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
