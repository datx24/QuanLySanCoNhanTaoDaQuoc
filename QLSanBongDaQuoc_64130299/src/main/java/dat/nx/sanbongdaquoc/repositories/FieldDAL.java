package dat.nx.sanbongdaquoc.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

	public boolean insertField(FieldDTO field) {
	    String query = "INSERT INTO fields_64130299 (FieldName, Status, PricePerHour, Description) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement psmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        psmt.setString(1, field.getFieldName());
	        psmt.setString(2, field.getStatus().getStatus());
	        psmt.setBigDecimal(3, field.getPricePerHour());
	        psmt.setString(4, field.getDescription());
	        // Thực thi câu lệnh INSERT
	        psmt.executeUpdate();

	        // Lấy ID tự động tạo từ cơ sở dữ liệu
	        try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int id = generatedKeys.getInt(1);
	                field.setFieldID(id);
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	//Cập nhật thông tin sân
	public boolean updateField(FieldDTO field) {
	    String query = "UPDATE fields_64130299 SET FieldName = ?, Status = ?, PricePerHour = ?, Description = ? WHERE FieldID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, field.getFieldName());
	        preparedStatement.setString(2, field.getStatus().getStatus());
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
		List<FieldDTO> listAllFields = new ArrayList<>();
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
				
				listAllFields.add(new FieldDTO(fieldID,fieldName,status,pricePerHour,description));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listAllFields;
	}
	
	// Lấy sân bóng theo id
	public FieldDTO getFieldByID(int fieldID) {
	    FieldDTO field = null; // Khởi tạo đối tượng FieldDTO để lưu trữ kết quả tìm kiếm

	    // Câu lệnh SQL để lấy thông tin sân theo FieldID
	    String query = "SELECT FieldID, FieldName, Status, PricePerHour, Description FROM fields_64130299 WHERE FieldID = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        // Thiết lập giá trị cho tham số trong câu lệnh SQL
	        preparedStatement.setInt(1, fieldID);

	        // Thực thi câu lệnh và lấy kết quả
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            // Kiểm tra nếu có kết quả trả về từ câu lệnh SELECT
	            if (resultSet.next()) {
	                int id = resultSet.getInt("FieldID");
	                String fieldName = resultSet.getString("FieldName");
	                String statusString = resultSet.getString("Status");
					// Chuyển đổi String sang enum một cách an toàn
		            FieldStatus status = FieldStatus.fromString(statusString); // Sử dụng fromString() thay vì valueOf()
	                BigDecimal pricePerHour = resultSet.getBigDecimal("PricePerHour");
	                String description = resultSet.getString("Description");

	                // Tạo đối tượng FieldDTO từ dữ liệu truy vấn và gán vào biến field
	                field = new FieldDTO(id, fieldName, status, pricePerHour, description);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return field;
	}
	
	// Tìm kiếm sân theo status
	public List<FieldDTO> getFieldsByStatus(FieldStatus status) {
	    List<FieldDTO> listFieldsByStatus = new ArrayList<>(); // Khởi tạo danh sách tạm thời để chứa kết quả
	    String query = "SELECT FieldID, FieldName, Status, PricePerHour, Description FROM fields_64130299 WHERE Status = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        // Thiết lập tham số trạng thái sân
	        preparedStatement.setString(1, status.getStatus());  // Sử dụng phương thức getStatus() từ enum FieldStatus

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                int fieldID = resultSet.getInt("FieldID");
	                String fieldName = resultSet.getString("FieldName");
	                String statusString = resultSet.getString("Status");
	                FieldStatus fieldStatus = FieldStatus.fromString(statusString);  // Chuyển String thành enum FieldStatus
	                BigDecimal pricePerHour = resultSet.getBigDecimal("PricePerHour");
	                String description = resultSet.getString("Description");

	                // Tạo đối tượng FieldDTO và thêm vào danh sách
	                listFieldsByStatus.add(new FieldDTO(fieldID, fieldName, fieldStatus, pricePerHour, description));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listFieldsByStatus; // Trả về danh sách các sân theo trạng thái
	}
}
