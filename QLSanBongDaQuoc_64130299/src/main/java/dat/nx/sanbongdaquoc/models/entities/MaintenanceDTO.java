package dat.nx.sanbongdaquoc.models.entities;

import java.sql.Date;

public class MaintenanceDTO {
	private String maintenanceID;
    private String fieldName; 
    private int fieldId;
    private Date startDate;
    private Date endDate;
    private String description;// Mô tả chi tiết lý do hoặc thông tin bảo trì
    private String createdBy;// ID của người quản lý lập lịch bảo trình
    
	public MaintenanceDTO() {
	}

	public MaintenanceDTO(String maintenanceID, String fieldName, Date startDate, Date endDate, String description,
			String createdBy) {
		super();
		this.maintenanceID = maintenanceID;
		this.fieldName = fieldName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.createdBy = createdBy;
	}
	
	

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMaintenanceID() {
		return maintenanceID;
	}

	public void setMaintenanceID(String maintenanceID) {
		this.maintenanceID = maintenanceID;
	}

	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
    
    
}
