package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
	private String bookingID;  
	private String userID; 
	private int fieldID;
	private LocalDate bookingDate;// Ngày khách hàng đặt sân
	private LocalTime startTime;// Giờ bắt đầu thuê sân
	private LocalTime endTime;// Giờ kết thúc thuê sân
	private String status;// Trạng thái đặt sân (Pending, Confirmed, Cancelled, No-Show)
	private String approvedBy;// ID của người quản lý xác nhận
	private String paymentStatus;// Trạng thái thanh toán (Paid, Unpaid)
	
	public BookingDTO() {
	}
	
	public BookingDTO(String bookingID, String userID, int fieldID, LocalDate bookingDate, LocalTime startTime, LocalTime endTime,
			String status, String approvedBy, String paymentStatus) {
		super();
		this.bookingID = bookingID;
		this.userID = userID;
		this.fieldID = fieldID;
		this.bookingDate = bookingDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.approvedBy = approvedBy;
		this.paymentStatus = paymentStatus;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
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

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
	
}
