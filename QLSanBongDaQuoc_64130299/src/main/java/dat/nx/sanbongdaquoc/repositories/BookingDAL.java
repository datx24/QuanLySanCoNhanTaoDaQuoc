package dat.nx.sanbongdaquoc.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.DatabaseConnection;

public class BookingDAL {
	private BookingDTO bookingDTO = new BookingDTO();
	private List<BookingDTO> bookingDTOs = new ArrayList<>();
	private Connection connection;
	private FieldDAL fieldDAL = new FieldDAL();
	
	
	public BookingDAL() {
		try {
			this.connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Phương thức thêm đơn đặt sân
    public boolean addBooking(String bookingID, String userID, String fieldName, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) throws SQLException {
        // Lấy FieldID từ tên sân
        int fieldID = fieldDAL.getFieldIDByName(fieldName);
        if (fieldID == -1) {
            System.out.println("Tên sân không tồn tại.");
            return false; // Trả về false nếu không tìm thấy tên sân
        }

        // Kiểm tra xem sân có đang bảo trì hay không
        String checkFieldStatusQuery = "SELECT Status FROM fields_64130299 WHERE FieldID = ?";
        try (PreparedStatement checkFieldStatusStmt = connection.prepareStatement(checkFieldStatusQuery)) {
            checkFieldStatusStmt.setInt(1, fieldID);
            try (ResultSet resultSet = checkFieldStatusStmt.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("Status");
                    if ("Bảo trì".equalsIgnoreCase(status)) {
                        System.out.println("Sân đang trong tình trạng bảo trì.");
                        return false; // Trả về false nếu sân đang bảo trì
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra trạng thái sân: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại lỗi để xử lý ở tầng cao hơn
        }

        // Kiểm tra xem có đặt chỗ trùng thời gian hay không
        String checkOverlapQuery = "SELECT 1 FROM bookings_64130299 WHERE FieldID = ? AND BookingDate = ? AND (StartTime < ? AND EndTime > ?)";
        try (PreparedStatement checkOverlapStmt = connection.prepareStatement(checkOverlapQuery)) {
            checkOverlapStmt.setInt(1, fieldID);
            checkOverlapStmt.setDate(2, Date.valueOf(bookingDate));
            checkOverlapStmt.setTime(3, Time.valueOf(endTime));
            checkOverlapStmt.setTime(4, Time.valueOf(startTime));
            try (ResultSet resultSet = checkOverlapStmt.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Thời gian đặt chỗ bị trùng với một đặt chỗ hiện có.");
                    return false; // Trả về false nếu có đặt chỗ trùng
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra thời gian trùng lặp: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại lỗi để xử lý ở tầng cao hơn
        }

        // Chèn đặt chỗ mới vào cơ sở dữ liệu
        String insertBookingQuery = "INSERT INTO bookings_64130299 (BookingID, UserID, FieldID, BookingDate, StartTime, EndTime, CreatedAt) " +
                "VALUES (?, ?, ?, ?, ?, ?, current_timestamp())";
        try (PreparedStatement insertBookingStmt = connection.prepareStatement(insertBookingQuery)) {
            insertBookingStmt.setString(1, bookingID);
            insertBookingStmt.setString(2, userID);
            insertBookingStmt.setInt(3, fieldID);
            insertBookingStmt.setDate(4, Date.valueOf(bookingDate));
            insertBookingStmt.setTime(5, Time.valueOf(startTime));
            insertBookingStmt.setTime(6, Time.valueOf(endTime));

            int rowsAffected = insertBookingStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm đặt chỗ thành công!");
                return true; // Trả về true nếu đã chèn thành công
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm đặt chỗ: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại lỗi để xử lý ở tầng cao hơn
        }
        return false;
    }

	// Xóa đơn đặt sân
	public boolean deleteBookingById(String bookingID) throws SQLException {
	    String query = "DELETE FROM bookings_64130299 WHERE BookingID = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, bookingID);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu xóa thành công
	    } catch (SQLException e) {
	        System.err.println("Lỗi khi xóa đặt chỗ: " + e.getMessage());
	        throw e;
	    }
	}


	// Lấy tất cả đơn đặt sân từ cơ sở dữ liệu
	public List<BookingDTO> getAllBookings() {
	    List<BookingDTO> listAllBookings = new ArrayList<>();
	    String query = "SELECT " +
	            "b.BookingID, " +
	            "b.BookingDate, " +
	            "b.StartTime, " +
	            "b.EndTime, " +
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

	            // Lấy và ánh xạ BookingDate, StartTime, EndTime riêng biệt
	            Date sqlBookingDate = resultSet.getDate("BookingDate");
	            LocalDate bookingDate = sqlBookingDate != null ? sqlBookingDate.toLocalDate() : null;

	            Time sqlStartTime = resultSet.getTime("StartTime");
	            LocalTime startTime = sqlStartTime != null ? sqlStartTime.toLocalTime() : null;

	            Time sqlEndTime = resultSet.getTime("EndTime");
	            LocalTime endTime = sqlEndTime != null ? sqlEndTime.toLocalTime() : null;

	            // Cập nhật các thuộc tính BookingDate, StartTime, EndTime
	            bookingDTO.setBookingDate(bookingDate);
	            bookingDTO.setStartTime(startTime);
	            bookingDTO.setEndTime(endTime);

	            // Lấy TimeDetails
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
	            "CONCAT(DATE_FORMAT(b.BookingDate, '%d/%m/%Y'), ' ', DATE_FORMAT(b.StartTime, '%H:%i'), ' - ', DATE_FORMAT(b.EndTime, '%H:%i')) AS TimeDetails, " +
	            "b.Status, " +
	            "b.PaymentStatus, " +
	            "u.FullName AS UserName, " +
	            "f.FieldName, " +
	            "b.BookingDate, " +
	            "b.StartTime, " +
	            "b.EndTime " +
	            "FROM bookings_64130299 b " +
	            "JOIN users_64130299 u ON b.UserID = u.UserID " +
	            "JOIN fields_64130299 f ON b.FieldID = f.FieldID " +
	            "WHERE b.BookingID = ?";

	    BookingDTO bookingDTO = null;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, bookingID);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Lấy thông tin từ ResultSet
	            String timeDetails = resultSet.getString("TimeDetails");
	            LocalDate bookingDate = null;
	            LocalTime startTime = null;
	            LocalTime endTime = null;

	            // Lấy ngày bookingDate từ BookingDate (cột trong cơ sở dữ liệu)
	            Date sqlBookingDate = resultSet.getDate("BookingDate");
	            if (sqlBookingDate != null) {
	                bookingDate = sqlBookingDate.toLocalDate(); // Chuyển đổi từ java.sql.Date thành LocalDate
	            }

	            // Tách phần thời gian bắt đầu và kết thúc từ TimeDetails
	            if (timeDetails != null) {
	                String[] parts = timeDetails.split(" ");
	                if (parts.length == 2) {
	                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	                    // Parse ngày và thời gian bắt đầu - kết thúc
	                    bookingDate = LocalDate.parse(parts[0], dateFormatter);
	                    String[] times = parts[1].split(" - ");
	                    startTime = LocalTime.parse(times[0], timeFormatter);
	                    endTime = LocalTime.parse(times[1], timeFormatter);
	                }
	            }

	            // Tạo đối tượng BookingDTO
	            bookingDTO = new BookingDTO(
	                    resultSet.getString("BookingID"),
	                    timeDetails,
	                    BookingStatus.fromString(resultSet.getString("Status")),
	                    resultSet.getString("UserName"),
	                    resultSet.getString("FieldName"),
	                    PaymentStatus.fromString(resultSet.getString("PaymentStatus")),
	                    startTime,
	                    endTime,
	                    bookingDate // Truyền ngày bookingDate từ cơ sở dữ liệu
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookingDTO;
	}

	// Cập nhật trạng thái của đơn đặt sân
    public boolean updateBookingStatus(String bookingID, BookingStatus status) {
        String query = "UPDATE bookings_64130299 SET Status = ? WHERE BookingID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setString(1, status.getStatus());
        	preparedStatement.setString(2, bookingID);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean processPayment(BookingDTO booking) {
        try {
            booking.setPaymentStatus(PaymentStatus.PAID);
            return updatePaymentStatus(booking); // Cập nhật trạng thái trong cơ sở dữ liệu
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Cập nhật trạng thái thanh toán
    public boolean updatePaymentStatus(BookingDTO booking) {
        String sql = "UPDATE bookings_64130299 SET PaymentStatus = ? WHERE BookingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, booking.getPaymentStatus().toString());
            stmt.setString(2, booking.getBookingID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Lấy danh sách sân theo trạng thái chờ duyêt
    public List<BookingDTO> getPendingBookings(){
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT b.BookingID, u.FullName AS FullName, u.Email, u.PhoneNumber, f.FieldName, " +
                       "CONCAT(DATE(b.BookingDate), ' ', b.StartTime, ' - ', b.EndTime) AS TimeDetails " +
                       "FROM bookings_64130299 b " +
                       "JOIN users_64130299 u ON b.UserID = u.UserID " +
                       "JOIN fields_64130299 f ON b.FieldID = f.FieldID " +
                       "WHERE b.Status = 'Chờ duyệt'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                BookingDTO booking = new BookingDTO();
                booking.setBookingID(rs.getString("BookingID"));
                booking.setUserName(rs.getString("FullName"));
                booking.setEmail(rs.getString("Email"));
                booking.setPhoneNumber(rs.getString("PhoneNumber"));
                booking.setFieldName(rs.getString("FieldName"));
                booking.setTimeDetails(rs.getString("TimeDetails"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bookings;
    }
    
    public int getTotalBookingsToday() {
        String query = "SELECT COUNT(*) AS TotalBookings FROM bookings_64130299 WHERE BookingDate = CURRENT_DATE";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("TotalBookings");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tính tổng số lượng đặt sân hôm nay: " + e.getMessage());
        }
        return 0;
    }

}
