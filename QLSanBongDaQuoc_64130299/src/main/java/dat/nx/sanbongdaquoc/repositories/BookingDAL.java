package dat.nx.sanbongdaquoc.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;

public class BookingDAL {
	private BookingDTO bookingDTO = new BookingDTO();
	private List<BookingDTO> bookingDTOs = new ArrayList<>();
	// Thêm đơn đặt sân
	public boolean addBooking(BookingDTO booking) {
		return true;
	}

	// Cập nhật đơn đặt sân
	public boolean updateBooking(BookingDTO booking) {
		return true;
	}

	// Xóa đơn đặt sân
	public boolean deleteBooking(BookingDTO booking) {
		return true;
	}

	// Lấy tất cả đơn đặt sân từ cơ sở dữ liệu
	public List<BookingDTO> getAllBookings() {
		return bookingDTOs;
	}

	// Lấy danh sách đặt sân theo trạng thái
	public List<BookingDTO> getBookingByStatus(BookingStatus booking) {
		return bookingDTOs;
	}
	
	// Lấy danh sách đặt sân theo ngày
	public List<BookingDTO> getBookingByDate(LocalDate bookingDate) {
		return bookingDTOs;
	}

	// Lấy thông tin của 1 đơn đặt sân theo id
	public BookingDTO getBookingById(String bookingId) {
		return bookingDTO;
	}
}
