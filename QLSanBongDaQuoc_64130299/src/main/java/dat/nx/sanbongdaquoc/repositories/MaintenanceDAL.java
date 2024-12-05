package dat.nx.sanbongdaquoc.repositories;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.models.entities.*;

public class MaintenanceDAL {
	private MaintenanceDTO maintenanceDTO = new MaintenanceDTO();
	private List<MaintenanceDTO> maintenanceDTOs = new ArrayList<>();

	// Thêm lịch bảo trì
	public boolean addMaintenance(MaintenanceDTO maintenance) {
		return true;
	}

	// Cập nhật thông tin bảo trì
	public boolean updateMaintenance(MaintenanceDTO maintenance) {
		return true;
	}

	// Xóa lịch bảo trì
	public boolean deleteMaintenance(MaintenanceDTO maintenance) {
		return true;
	}

	// Lấy thông tin bảo trì theo ID
	public MaintenanceDTO getMaintenanceByID(String maintenanceID) {
		return maintenanceDTO;
	}

	// Lấy tất cả lịch bào trì
	public List<MaintenanceDTO> getAllMaintenance() {
		return maintenanceDTOs;
	}

	// Lấy lịch bảo trì theo sân
	public List<MaintenanceDTO> getMaintenanceByFieldID(int fieldID) {
		return maintenanceDTOs;
	}

	// Kiểm tra lịch bảo trì có bị trùng hay không
	public boolean checkMaintenanceOverlap(int fieldID, Date startDate, Date endDate) {
		return true;
	}
	
	// Lấy tất cả lịch bảo trì theo thời gian
	public List<MaintenanceDTO> getMaintenanceByDateRange(Date startDate, Date endDate){
		return maintenanceDTOs;
	}
	
	// Lấy lịch bảo trì sắp tới
	public List<MaintenanceDTO> getUpcomingMaintenance(){
		return maintenanceDTOs;
	}
	
	// Tìm kiếm lịch bảo trì theo mô tả
	public List<MaintenanceDTO> searchMaintenanceByDescription(String keyword){
		return maintenanceDTOs;
	}

}
