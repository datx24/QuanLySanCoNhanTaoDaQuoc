package dat.nx.sanbongdaquoc.models.entities;

import java.math.BigDecimal;

import dat.nx.sanbongdaquoc.enums.*;

public class FieldDTO {
	private int fieldID;// ID duy nhất cho mỗi sân bóng
    private String fieldName;// Tên sân bóng
    private FieldStatus status;// Trạng thái của sân bóng (có sẵn, bảo trì, đă đặt)
    private BigDecimal pricePerHour;// Giá thuê sân mỗi giờ
    private String description;// Mô tả sân bóng
    
	public FieldDTO() {
	}

	public FieldDTO(int fieldID, String fieldName, FieldStatus status, BigDecimal pricePerHour, 
			String description) {
		super();
		this.fieldID = fieldID;
		this.fieldName = fieldName;
		this.status = status;
		this.pricePerHour = pricePerHour;
		this.description = description;
	}

	public int getFieldID() {
		return fieldID;
	}

	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public FieldStatus getStatus() {
		return status;
	}

	public void setStatus(FieldStatus status) {
		this.status = status;
	}

	public BigDecimal getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(BigDecimal pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusString() {
        return status.getStatus();
    }
}
