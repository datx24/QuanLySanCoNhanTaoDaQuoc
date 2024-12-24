package dat.nx.sanbongdaquoc.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.DatabaseConnection;

public class BookingDAL {
	private BookingDTO bookingDTO = new BookingDTO();
	private List<BookingDTO> bookingDTOs = new ArrayList<>();
	private Connection connection;
	
	
	public BookingDAL() {
		try {
			this.connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	    List<BookingDTO> listAllBookings = new ArrayList<>();
	    String query = "SELECT " +
	            "b.BookingID, " +
	            "CONCAT(DATE_FORMAT(b.StartTime, '%H:%i'), ' - ', DATE_FORMAT(b.EndTime, '%H:%i'), ', ', DATE_FORMAT(b.BookingDate, '%d/%m/%Y')) AS TimeDetails, " +
	            "b.Status, " +
	            "b.PaymentStatus, " +
	            "u.FullName, " +
	            "f.FieldName " +
	            "FROM bookings_64130299 b " +
	            "JOIN users_64130299 u ON b.UserID = u.UserID " +
	            "JOIN fields_64130299 f ON b.FieldID = f.FieldID";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        while (resultSet.next()) {
	            BookingDTO bookingDTO = new BookingDTO();
	            bookingDTO.setBookingID(resultSet.getString("BookingID"));
	            bookingDTO.setTimeDetails(resultSet.getString("TimeDetails"));
	            bookingDTO.setStatus(BookingStatus.fromString(resultSet.getString("Status")));
	            bookingDTO.setPaymentStatus(PaymentStatus.fromString(resultSet.getString("PaymentStatus")));
	            bookingDTO.setUserName(resultSet.getString("FullName"));
	            bookingDTO.setFieldName(resultSet.getString("FieldName"));
	            listAllBookings.add(bookingDTO);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching bookings from database: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return listAllBookings;
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
