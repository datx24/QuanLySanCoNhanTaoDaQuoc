package dat.nx.sanbongdaquoc.repositories;

import java.sql.Connection;
import java.sql.Date;
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

	//Tìm kiếm danh sách đặt sân bóng dựa vào tên sân hoặc ngày đặt sân hoặc cả hai
	public List<BookingDTO> searchBookings(String fieldName, LocalDate bookingDate) {
	    List<BookingDTO> filteredBookings = new ArrayList<>();
	    StringBuilder query = new StringBuilder(
	        "SELECT b.BookingID, " +
	        "CONCAT(b.StartTime, ' - ', b.EndTime) AS TimeDetails, " +
	        "b.Status, b.PaymentStatus, u.FullName, f.FieldName " +
	        "FROM bookings_64130299 b " +
	        "JOIN users_64130299 u ON b.UserID = u.UserID " +
	        "JOIN fields_64130299 f ON b.FieldID = f.FieldID " +
	        "WHERE 1=1"
	    );

	    if (fieldName != null && !fieldName.isEmpty()) {
	        query.append(" AND f.FieldName = ?");
	    }
	    if (bookingDate != null) {
	        query.append(" AND b.BookingDate = ?");
	    }

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
	        int paramIndex = 1;
	        if (fieldName != null && !fieldName.isEmpty()) {
	        	preparedStatement.setString(paramIndex++, fieldName);
	        }
	        if (bookingDate != null) {
	        	preparedStatement.setDate(paramIndex, Date.valueOf(bookingDate));
	        }

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                BookingDTO bookingDTO = new BookingDTO();
	                bookingDTO.setBookingID(resultSet.getString("BookingID"));
	                bookingDTO.setTimeDetails(resultSet.getString("TimeDetails"));
	                bookingDTO.setStatus(BookingStatus.fromString(resultSet.getString("Status")));
	                bookingDTO.setPaymentStatus(PaymentStatus.fromString(resultSet.getString("PaymentStatus")));
	                bookingDTO.setUserName(resultSet.getString("FullName"));
	                bookingDTO.setFieldName(resultSet.getString("FieldName"));
	                filteredBookings.add(bookingDTO);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return filteredBookings;
	}
	
	// Lấy thông tin của 1 đơn đặt sân theo id
	public BookingDTO getBookingById(String bookingID) {
	    String query = "SELECT " +
	            "b.BookingID, " +
	            "CONCAT(DATE_FORMAT(b.StartTime, '%H:%i'), ' - ', DATE_FORMAT(b.EndTime, '%H:%i'), ', ', DATE_FORMAT(b.BookingDate, '%d/%m/%Y')) AS TimeDetails, " +
	            "b.Status, " +
	            "b.PaymentStatus, " +
	            "u.FullName AS UserName, " +
	            "f.FieldName " +
	            "FROM bookings_64130299 b " +
	            "JOIN users_64130299 u ON b.UserID = u.UserID " +
	            "JOIN fields_64130299 f ON b.FieldID = f.FieldID " +
	            "WHERE b.BookingID = ?";
	    BookingDTO bookingDTO = null;
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, bookingID);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            bookingDTO = new BookingDTO();
	            bookingDTO.setBookingID(resultSet.getString("BookingID"));
	            bookingDTO.setTimeDetails(resultSet.getString("TimeDetails"));
	            bookingDTO.setStatus(BookingStatus.fromString(resultSet.getString("Status")));
	            bookingDTO.setUserName(resultSet.getString("UserName"));
	            bookingDTO.setFieldName(resultSet.getString("FieldName"));
	            bookingDTO.setPaymentStatus(PaymentStatus.fromString(resultSet.getString("PaymentStatus")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookingDTO;
	}
}
