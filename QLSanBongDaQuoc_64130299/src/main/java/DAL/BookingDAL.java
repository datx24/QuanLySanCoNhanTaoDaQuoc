package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.BookingDTO;
import Enum.BookingStatus;

public class BookingDAL {
	// Thêm đơn đặt sân
	public boolean addBooking(BookingDTO booking) {
		return true;
	}

	// Cập nhật đơn đặt sân
	public boolean updateBooking(BookingDTO booking) {
		return true;
	}

	// Xóa đơn đặt sân
	public boolean deleteBooking(String bookingID) {
		return true;
	}

	// Lấy tất cả đơn đặt sân từ cơ sở dữ liệu
	public List<BookingDTO> getAllBookings() {
		List<BookingDTO> bookings = new ArrayList<>();
		return bookings;
	}

	// Lấy danh sách đặt sân theo trạng thái
	public List<BookingDTO> getBookingByStatus(BookingStatus booking) {
		List<BookingDTO> bookings = new ArrayList<BookingDTO>();
		return bookings;
	}

	// Lấy thông tin của 1 đơn đặt sân theo id
	public BookingDTO getBookingById(String bookingId) {
		BookingDTO booking = new BookingDTO();
		return booking;
	}
}
