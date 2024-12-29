package dat.nx.sanbongdaquoc.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.DatabaseConnection;

public class MaintenanceDAL {
	private MaintenanceDTO maintenanceDTO = new MaintenanceDTO();
	private List<MaintenanceDTO> maintenanceDTOs = new ArrayList<>();
	private Connection connection;

	public MaintenanceDAL() {
		try {
			this.connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Thêm lịch bảo trì
    public boolean addMaintenance(MaintenanceDTO maintenance) {
        String getFieldIdSql = "SELECT FieldID FROM fields_64130299 WHERE FieldName = ?";
        String insertMaintenanceSql = "INSERT INTO maintenance_64130299 (FieldID, StartDate, EndDate, Description) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Lấy FieldID từ FieldName
            int fieldId = -1;
            try (PreparedStatement getFieldIdStmt = connection.prepareStatement(getFieldIdSql)) {
                getFieldIdStmt.setString(1, maintenance.getFieldName());
                ResultSet resultSet = getFieldIdStmt.executeQuery();
                if (resultSet.next()) {
                    fieldId = resultSet.getInt("FieldID");
                } else {
                    System.err.println("FieldName không tồn tại: " + maintenance.getFieldName());
                    return false; // Không tìm thấy FieldName
                }
            }

            // Thêm lịch bảo trì
            try (PreparedStatement insertMaintenanceStmt = connection.prepareStatement(insertMaintenanceSql)) {
                insertMaintenanceStmt.setInt(1, fieldId);
                insertMaintenanceStmt.setDate(2, new Date(maintenance.getStartDate().getTime()));
                insertMaintenanceStmt.setDate(3, new Date(maintenance.getEndDate().getTime()));
                insertMaintenanceStmt.setString(4, maintenance.getDescription());

                int rowsInserted = insertMaintenanceStmt.executeUpdate();
                return rowsInserted > 0; // Trả về true nếu thêm thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin bảo trì
    public boolean updateMaintenance(MaintenanceDTO maintenance) {
        String query = "UPDATE maintenance_64130299 "
                     + "SET FieldID = (SELECT FieldID FROM fields_64130299 WHERE FieldName = ?), StartDate = ?, EndDate = ?, Description = ? "
                     + "WHERE MaintenanceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maintenance.getFieldName());
            stmt.setDate(2, maintenance.getStartDate());
            stmt.setDate(3, maintenance.getEndDate());
            stmt.setString(4, maintenance.getDescription());
            stmt.setString(5, maintenance.getMaintenanceID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật lịch bảo trì: " + e.getMessage());
            return false;
        }
    }

    // Xóa lịch bảo trì
    public boolean deleteMaintenance(MaintenanceDTO maintenance) {
        String query = "DELETE FROM maintenance_64130299 WHERE MaintenanceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, maintenance.getMaintenanceID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa lịch bảo trì: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra lịch bảo trì có bị trùng hay không
    public boolean checkMaintenanceOverlap(String fieldName, Date startDate, Date endDate) {
        String query = "SELECT COUNT(*) AS overlapCount "
                     + "FROM maintenance_64130299 m "
                     + "JOIN fields_64130299 f ON m.FieldID = f.FieldID "
                     + "WHERE f.FieldName = ? "
                     + "AND NOT (m.EndDate < ? OR m.StartDate > ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fieldName);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("overlapCount") > 0; // Có ít nhất 1 lịch trùng
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra trùng lịch bảo trì: " + e.getMessage());
        }
        return false;
    }
	
	//Phương thức lấy tất cả lịch bảo trì
	public List<MaintenanceDTO> getAllMaintenances() {
	    List<MaintenanceDTO> maintenanceList = new ArrayList<>();
	    String query = "SELECT m.MaintenanceID, f.FieldName, m.StartDate, m.EndDate, m.Description "
	                 + "FROM maintenance_64130299 m "
	                 + "JOIN fields_64130299 f ON m.FieldID = f.FieldID;";

	    try (PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            MaintenanceDTO maintenance = new MaintenanceDTO();
	            maintenance.setMaintenanceID(rs.getString("MaintenanceID"));
	            maintenance.setFieldName(rs.getString("FieldName")); // Chỉ lấy tên sân
	            maintenance.setStartDate(rs.getDate("StartDate"));
	            maintenance.setEndDate(rs.getDate("EndDate"));
	            maintenance.setDescription(rs.getString("Description"));
	            maintenanceList.add(maintenance);
	        }
	    } catch (SQLException e) {
	        System.err.println("Lỗi khi truy vấn tất cả lịch bảo trì: " + e.getMessage());
	    }
	    return maintenanceList;
	}

	// Lấy lịch bảo trì theo tên sân
	public List<MaintenanceDTO> getMaintenanceByFieldName(String fieldName) {
	    List<MaintenanceDTO> maintenanceDTOs = new ArrayList<>();
	    String query = "SELECT m.MaintenanceID, f.FieldName, m.StartDate, m.EndDate, m.Description "
	                 + "FROM maintenance_64130299 m "
	                 + "JOIN fields_64130299 f ON m.FieldID = f.FieldID "
	                 + "WHERE f.FieldName LIKE ?";  // Dùng LIKE để tìm kiếm gần đúng

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, "%" + fieldName + "%");  // Tìm kiếm chuỗi chứa "fieldName"
	        ResultSet resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            MaintenanceDTO maintenanceDTO = new MaintenanceDTO();
	            maintenanceDTO.setMaintenanceID(resultSet.getString("MaintenanceID"));
	            maintenanceDTO.setFieldName(resultSet.getString("FieldName"));
	            maintenanceDTO.setStartDate(resultSet.getDate("StartDate"));
	            maintenanceDTO.setEndDate(resultSet.getDate("EndDate"));
	            maintenanceDTO.setDescription(resultSet.getString("Description"));
	            maintenanceDTOs.add(maintenanceDTO);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return maintenanceDTOs;
	}


}
