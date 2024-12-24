package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.time.LocalDate;
import java.util.List;


public class BookingBLL {
	private BookingDAL bookingDAL;
	
	public BookingBLL(BookingDAL bookingDAL) {
		this.bookingDAL = bookingDAL;
	}

	//Kiểm tra các thuộc tính trước khi thêm đơn
	public boolean addBooking(BookingDTO bookingDTO) {
		return bookingDAL.addBooking(bookingDTO);
	}
	
	//Kiểm tra các thuộc tính trước khi cập nhật đơn
	public boolean updateBooking(BookingDTO bookingDTO) {
		return bookingDAL.updateBooking(bookingDTO);
	}
	
	//Kiểm tra thông tin đơn trước khi xóa
	public boolean deleteBooking(String bookingId) {
		BookingDTO bookingDTO = bookingDAL.getBookingById(bookingId);
		return bookingDAL.deleteBooking(bookingDTO);
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
	
	//Phương thức xác nhận hoặc thay đổi trạng thái đơn đặt sân
	public boolean updateBookingStatus(String bookingId, BookingStatus newStatus) {
		return true;
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
}	
