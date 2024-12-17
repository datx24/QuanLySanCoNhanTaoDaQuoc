package dat.nx.sanbongdaquoc.repositories;

import java.math.BigDecimal;
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
	
	public FieldDAL() {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Thêm sân
	public boolean insertField(FieldDTO field) {
	    String query = "INSERT INTO fields_64130299 (FieldID, FieldName, Status, PricePerHour, Description) VALUES (?, ?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, field.getFieldID());
	        preparedStatement.setString(2, field.getFieldName());
	        preparedStatement.setString(3, field.getStatus().toString());
	        preparedStatement.setBigDecimal(4, field.getPricePerHour());
	        preparedStatement.setString(5, field.getDescription());
	        int result = preparedStatement.executeUpdate();
	        return result > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	//Cập nhật thông tin sân
	public boolean updateField(FieldDTO field) {
	    String query = "UPDATE fields_64130299 SET FieldName = ?, Status = ?, PricePerHour = ?, Description = ? WHERE FieldID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, field.getFieldName());
	        preparedStatement.setString(2, field.getStatus().toString());
	        preparedStatement.setBigDecimal(3, field.getPricePerHour());
	        preparedStatement.setString(4, field.getDescription());
	        preparedStatement.setInt(5, field.getFieldID());
	        int result = preparedStatement.executeUpdate();
	        return result > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	//Xóa sân
	public boolean deleteField(FieldDTO fieldDTO) {
	    String query = "DELETE FROM fields_64130299 WHERE FieldID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, fieldDTO.getFieldID());
	        int result = preparedStatement.executeUpdate();
	        return result > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	//Lấy tất cả sân bóng từ cơ sở dữ liệu
	public List<FieldDTO> getAllFields() {
		String query = "SELECT FieldID,FieldName,Status,PricePerHour,Description FROM fields_64130299";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery()) {
			while(resultSet.next()) {
				int fieldID = resultSet.getInt("FieldID");
				String fieldName = resultSet.getString("FieldName");
				String statusString = resultSet.getString("Status");
				// Chuyển đổi String sang enum một cách an toàn
	            FieldStatus status = FieldStatus.fromString(statusString); // Sử dụng fromString() thay vì valueOf()
				BigDecimal pricePerHour	= resultSet.getBigDecimal("PricePerHour");
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
