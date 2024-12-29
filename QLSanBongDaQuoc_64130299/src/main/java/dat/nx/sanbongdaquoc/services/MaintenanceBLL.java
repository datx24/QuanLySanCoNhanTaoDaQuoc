package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class MaintenanceBLL {
	private MaintenanceDAL maintenanceDAL;
	
	public MaintenanceBLL(MaintenanceDAL maintenanceDAL) {
		this.maintenanceDAL = maintenanceDAL;
	}


    // Thêm lịch bảo trì (có kiểm tra trùng ngày)
    public boolean addMaintenance(MaintenanceDTO maintenance) {
            return maintenanceDAL.addMaintenance(maintenance);
    }

    // Sửa lịch bảo trì (có kiểm tra trùng ngày)
    public boolean updateMaintenance(MaintenanceDTO maintenance) {
        return maintenanceDAL.updateMaintenance(maintenance);
    }

    // Xóa lịch bảo trì
    public boolean deleteMaintenance(MaintenanceDTO maintenance) {
        return maintenanceDAL.deleteMaintenance(maintenance);
    }

    // Phương thức kiểm tra trùng lịch bảo trì
    public boolean checkMaintenanceOverlap(String fieldName, LocalDate startDate, LocalDate endDate) {
        // Lấy danh sách các lịch bảo trì của sân dựa vào fieldName
        List<MaintenanceDTO> maintenances = maintenanceDAL.getMaintenanceByFieldName(fieldName);

        // Kiểm tra xem lịch mới có trùng với lịch bảo trì cũ không
        for (MaintenanceDTO maintenance : maintenances) {
            LocalDate existingStartDate = maintenance.getStartDate().toLocalDate();
            LocalDate existingEndDate = maintenance.getEndDate().toLocalDate();

            // Kiểm tra trùng lịch
            if (!(endDate.isBefore(existingStartDate) || startDate.isAfter(existingEndDate))) {
                return true; // Có trùng lịch
            }
        }
        return false; // Không trùng lịch
    }

	
	//Lấy tất cả lịch bảo trì
	public List<MaintenanceDTO> getAllMaintenance() {
		return maintenanceDAL.getAllMaintenances();
	}
	
	//Lấy lịch bảo trì theo sân
	public List<MaintenanceDTO> getMaintenanceByFieldName(String fieldName) {
		return maintenanceDAL.getMaintenanceByFieldName(fieldName);
	}
}
