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
    private String phoneNumber;
    private String email;
    private LocalDate bookingDate;
    private LocalTime startTime,endTime;
	
	public BookingDTO() {
	}
	
    public BookingDTO(String bookingID, String timeDetails, BookingStatus status, String userName, String fieldName,
                      PaymentStatus paymentStatus, LocalTime startTime, LocalTime endTime, LocalDate bookingDate) {
        this.bookingID = bookingID;
        this.timeDetails = timeDetails;
        this.status = status;
        this.userName = userName;
        this.fieldName = fieldName;
        this.paymentStatus = paymentStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingDate = bookingDate;
    }
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public BookingStatus getStatus() {
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
