package dat.nx.sanbongdaquoc.models.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import dat.nx.sanbongdaquoc.enums.*;

public class BookingDTO {
	private String bookingID;  
	private String userID; 
	private int fieldID;
	private LocalDate bookingDate;// Ngày khách hàng đặt sân
	private LocalTime startTime;// Giờ bắt đầu thuê sân
	private LocalTime endTime;// Giờ kết thúc thuê sân
	private BookingStatus  status;// Trạng thái đặt sân (Pending, Confirmed, Cancelled, No-Show)
	private String approvedBy;// ID của người quản lý xác nhận
	private PaymentStatus paymentStatus;// Trạng thái thanh toán (Paid, Unpaid)
	private Timestamp createdAt;
	
	public BookingDTO() {
	}
	
	public BookingDTO(String bookingID, String userID, int fieldID, LocalDate bookingDate, LocalTime startTime, LocalTime endTime,
			BookingStatus  status, String approvedBy, PaymentStatus paymentStatus,Timestamp createdAt) {
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
		this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
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
	
	
	
}
