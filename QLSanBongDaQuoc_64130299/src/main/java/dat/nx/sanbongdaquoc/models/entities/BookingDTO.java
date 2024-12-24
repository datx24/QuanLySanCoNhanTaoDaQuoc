package dat.nx.sanbongdaquoc.models.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import dat.nx.sanbongdaquoc.enums.*;

public class BookingDTO {
	private String bookingID;  
	private String userID; 
	private int fieldID;
	private String timeDetails;
	private BookingStatus  status;// Trạng thái đặt sân (Pending, Confirmed, Cancelled, No-Show)
	private String approvedBy;// ID của người quản lý xác nhận
	private PaymentStatus paymentStatus;// Trạng thái thanh toán (Paid, Unpaid)
	private Timestamp createdAt;
	private String userName;
    private String fieldName;
    private LocalDate bookingDate;
	
	public BookingDTO() {
	}

	public BookingDTO(String bookingID, String userID, int fieldID, String timeDetails, BookingStatus status,
			String approvedBy, PaymentStatus paymentStatus, Timestamp createdAt, String userName, String fieldName,
			LocalDate bookingDate) {
		super();
		this.bookingID = bookingID;
		this.userID = userID;
		this.fieldID = fieldID;
		this.timeDetails = timeDetails;
		this.status = status;
		this.approvedBy = approvedBy;
		this.paymentStatus = paymentStatus;
		this.createdAt = createdAt;
		this.userName = userName;
		this.fieldName = fieldName;
		this.bookingDate = bookingDate;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getTimeDetails() {
		return timeDetails;
	}

	public void setTimeDetails(String timeDetails) {
		this.timeDetails = timeDetails;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getFieldID() {
		return fieldID;
	}

	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	public BookingStatus  getStatus() {
		return status;
	}

	public void setStatus(BookingStatus  status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	
}
