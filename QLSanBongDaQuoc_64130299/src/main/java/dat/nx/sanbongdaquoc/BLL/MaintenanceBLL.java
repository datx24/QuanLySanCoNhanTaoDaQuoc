package dat.nx.sanbongdaquoc.BLL;

import dat.nx.sanbongdaquoc.DTO.*;

import java.sql.Date;
import java.util.List;

import dat.nx.sanbongdaquoc.DAL.*;

public class MaintenanceBLL {
	private MaintenanceDAL maintenanceDAL = new MaintenanceDAL();
	
	//Xử lý logic trước khi thêm lịch bảo trì
	public boolean addMaintenanceIfNotOverlap(MaintenanceDTO maintenanceDTO) {
		//Cài đặt xử lý trước khi thêm
		return maintenanceDAL.addMaintenance(maintenanceDTO);
	}
	
	//Cập nhật thông tin bảo trì
	public boolean updateMaintenance(MaintenanceDTO maintenanceDTO) {
		return maintenanceDAL.updateMaintenance(maintenanceDTO);
	}
	
	//Xóa lịch bảo trì
	public boolean deleteMaintenance(String maintenanceID) {
		MaintenanceDTO maintenanceDTO = getMaintenanceByID(maintenanceID);
		return maintenanceDAL.deleteMaintenance(maintenanceDTO);
	}
	
	//Lấy thông tin bảo trì theo ID
	public MaintenanceDTO getMaintenanceByID(String maintenanceID) {
		return maintenanceDAL.getMaintenanceByID(maintenanceID);
	}
	
	//Lấy tất cả lịch bảo trì
	public List<MaintenanceDTO> getAllMaintenance() {
		return maintenanceDAL.getAllMaintenance();
	}
	
	//Lấy lịch bảo trì theo sân
	public List<MaintenanceDTO> getMaintenanceByFieldID(int fieldID) {
		return maintenanceDAL.getMaintenanceByFieldID(fieldID);
	}
	
	//Kiểm tra lịch bảo trì có trùng không
	public boolean checkMaintenanceOverlap(int fieldID, Date startDate, Date endDate) {
		return maintenanceDAL.checkMaintenanceOverlap(fieldID, startDate, endDate);
	}
	
	//Lấy tất cả lịch bảo trì theo thời gian
	public List<MaintenanceDTO> getMaintenanceByDateRange(Date startDate, Date endDate) {
        return maintenanceDAL.getMaintenanceByDateRange(startDate, endDate);
    }
	
	// Lấy lịch bảo trì sắp tới
    public List<MaintenanceDTO> getUpcomingMaintenance() {
        return maintenanceDAL.getUpcomingMaintenance();
    }
}
