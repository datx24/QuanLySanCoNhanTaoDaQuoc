package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class BookingBLL {
	private BookingDAL bookingDAL;
	private InvoiceDAL invoiceDAL = new InvoiceDAL();
	
	public BookingBLL(BookingDAL bookingDAL) {
		this.bookingDAL = bookingDAL;
	}
	
	// Kiểm tra các thuộc tính trước khi thêm đơn
	public boolean addBooking(String bookingID, String userID, String fieldName, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
	    // Giới hạn khung giờ từ 6:00 sáng đến 22:00 tối
	    LocalTime earliestTime = LocalTime.of(6, 0); // 6:00 sáng
	    LocalTime latestTime = LocalTime.of(22, 0); // 22:00 tối

	    if (startTime.isBefore(earliestTime) || endTime.isAfter(latestTime)) {
	        System.err.println("Thời gian đặt chỗ phải nằm trong khung giờ từ 6:00 đến 22:00.");
	        return false; // Trả về false nếu thời gian không hợp lệ
	    }

	    if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
	        System.err.println("Thời gian bắt đầu phải trước thời gian kết thúc.");
	        return false; // Trả về false nếu thời gian không hợp lệ
	    }

	    try {
	        // Gọi phương thức từ DAL để thêm đặt chỗ
	        return bookingDAL.addBooking(bookingID, userID, fieldName, bookingDate, startTime, endTime);
	    } catch (SQLException e) {
	        System.err.println("Lỗi khi thêm đơn đặt chỗ: " + e.getMessage());
	        return false;
	    }
	}
	
	//Kiểm tra thông tin đơn trước khi xóa
	public boolean deleteBookingById(String bookingID) {
	    try {
	        return bookingDAL.deleteBookingById(bookingID); // Gọi phương thức từ DAL
	    } catch (SQLException e) {
	        System.err.println("Lỗi khi xóa đặt chỗ: " + e.getMessage());
	        return false; // Trả về false nếu có lỗi
	    }
	}
	
	//Lấy tất cả đơn đặt sân
	public List<BookingDTO> getAllBookings() {
		return bookingDAL.getAllBookings();
	}

	
	//Phương thức kiểm tra tình trạng sân trống
	public boolean checkFieldAvailability(LocalDate bookingDate, String fieldId, 
			LocalDate startTime, LocalDate endTime) {
		return true;
	}
	
	// Phương thức cập nhật trạng thái đơn đặt sân sử dụng BookingDTO
    public boolean updateBookingStatus(BookingDTO selectedBooking) {
        // Get the booking ID and the new status from the BookingDTO
        String bookingId = selectedBooking.getBookingID();
        BookingStatus newStatus = selectedBooking.getStatus();

        // Gọi phương thức trong DAL để cập nhật trạng thái
        return bookingDAL.updateBookingStatus(bookingId, newStatus);
    }
	
	public List<BookingDTO> searchBookings(String fieldName, LocalDate bookingDate) {
        if ((fieldName == null || fieldName.isEmpty()) && bookingDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn tên sân hoặc ngày đặt.");
        }
        return bookingDAL.searchBookings(fieldName, bookingDate);
    }
	
    public BookingDTO getBookingById(String bookingID) {
        return bookingDAL.getBookingById(bookingID);
    }
    
    private boolean isTimeOverlapping(LocalDate bookingDate1, LocalTime startTime1, LocalTime endTime1,
            LocalDate bookingDate2, LocalTime startTime2, LocalTime endTime2) {
		// Kiểm tra nếu ngày khác nhau, không thể chồng lấn
		if (!bookingDate1.equals(bookingDate2)) {
		return false;
		}
		// Kiểm tra chồng lấn thời gian
		// Không chồng lấn nếu khoảng thời gian 1 kết thúc trước hoặc bằng khi khoảng thời gian 2 bắt đầu
		// Hoặc khoảng thời gian 1 bắt đầu sau hoặc bằng khi khoảng thời gian 2 kết thúc
		return !(endTime1.isBefore(startTime2) || endTime1.equals(startTime2) ||
		startTime1.isAfter(endTime2) || startTime1.equals(endTime2));
}

    public boolean cancelBooking(String bookingID) {
        return bookingDAL.updateBookingStatus(bookingID, BookingStatus.CANCELLED);
    }
    
    public boolean processPayment(BookingDTO booking) {
        try {
            // Cập nhật trạng thái thanh toán của booking
            booking.setPaymentStatus(PaymentStatus.PAID);

            // Cập nhật trạng thái thanh toán trong cơ sở dữ liệu
            boolean isPaymentUpdated = bookingDAL.updatePaymentStatus(booking);
            
            // Nếu thanh toán đã được cập nhật thành công, tạo hóa đơn
            if (isPaymentUpdated) {
                // Tạo hóa đơn từ BookingDTO
                InvoiceDTO invoice = invoiceDAL.createInvoiceFromBooking(booking);
                
                // Lưu hóa đơn vào cơ sở dữ liệu
                boolean isInvoiceSaved = invoiceDAL.insertInvoice(invoice);
                
                // Trả về kết quả nếu hóa đơn được lưu thành công
                return isInvoiceSaved;
            }
            
            return false;  // Nếu cập nhật thanh toán thất bại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Lấy danh sách đặt sân trạng thái chờ duyệt
    public List<BookingDTO> getPendingBookings() {
        return bookingDAL.getPendingBookings();
    }
    
    public int getTotalBookingsToday() {
        return bookingDAL.getTotalBookingsToday();
    }
    
    
}	
