package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    
    public boolean checkTimeConflictAndUpdateStatus(BookingDTO newBooking) {
        List<BookingDTO> existingBookings = bookingDAL.getAllBookings();
        System.out.println("Tổng số đơn đặt sân hiện có: " + existingBookings.size());

        for (BookingDTO existingBooking : existingBookings) {
            // Bỏ qua chính đối tượng đang được kiểm tra
            if (existingBooking.getBookingID().equals(newBooking.getBookingID())) {
                System.out.println("Bỏ qua đối tượng trùng với chính nó: " + existingBooking.getBookingID());
                continue;
            }

            System.out.println("Debug: Kiểm tra với đơn đặt sân:");
            System.out.println("- BookingDate: " + existingBooking.getBookingDate());
            System.out.println("- StartTime: " + existingBooking.getStartTime());
            System.out.println("- EndTime: " + existingBooking.getEndTime());

            if (existingBooking.getStartTime() != null && existingBooking.getEndTime() != null) {
                boolean isOverlapping = isTimeOverlapping(
                    newBooking.getBookingDate(), newBooking.getStartTime(), newBooking.getEndTime(),
                    existingBooking.getBookingDate(), existingBooking.getStartTime(), existingBooking.getEndTime()
                );

                System.out.println("So sánh với: ");
                System.out.println("- New BookingDate: " + newBooking.getBookingDate());
                System.out.println("- New StartTime: " + newBooking.getStartTime());
                System.out.println("- New EndTime: " + newBooking.getEndTime());
                System.out.println("Kết quả isOverlapping: " + isOverlapping);

                if (isOverlapping) {
                    System.out.println("Xung đột thời gian với: " + existingBooking.getTimeDetails());
                    return false; // Có xung đột -> không cập nhật trạng thái
                }
            } else {
                System.out.println("Thời gian không hợp lệ: " + existingBooking.getTimeDetails());
            }
        }

        // Nếu không có xung đột thời gian, tiến hành cập nhật trạng thái
        System.out.println("Không có xung đột thời gian. Tiến hành xác nhận trạng thái.");
        return bookingDAL.updateBookingStatus(newBooking.getBookingID(), BookingStatus.CONFIRMED);
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

}	
