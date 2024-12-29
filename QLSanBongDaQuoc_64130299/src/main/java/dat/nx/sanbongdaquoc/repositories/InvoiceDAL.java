package dat.nx.sanbongdaquoc.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.utils.DatabaseConnection;

public class InvoiceDAL {
	private InvoiceDTO invoiceDTO = new InvoiceDTO();
	private List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
	private Connection connection;
	
	
	public InvoiceDAL() {
		super();
		try {
			this.connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isPaymentCompleted(String bookingID) {
	    String sql = "SELECT PaymentStatus FROM bookings_64130299 WHERE BookingID = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, bookingID);
	        
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            String paymentStatus = resultSet.getString("PaymentStatus");
	            // Kiểm tra nếu PaymentStatus = "Đã thanh toán" hoặc bất kỳ trạng thái nào tương đương
	            return "Đã thanh toán".equalsIgnoreCase(paymentStatus);
	        }
	        return false; // Nếu không tìm thấy BookingID
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Nếu có lỗi xảy ra
	    }
	}
	
	//Thêm hóa đơn thanh toán
	public boolean insertInvoice(InvoiceDTO invoice) {
        String query = "INSERT INTO invoices_64130299 (BookingID, Amount, PaymentMethod, CreatedAt) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, invoice.getBookingID());
            stmt.setBigDecimal(2, invoice.getAmount());
            stmt.setString(3, invoice.getPaymentMethod().getStatus());
            stmt.setTimestamp(4, invoice.getCreatedAt());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// Phương thức xóa hóa đơn
    public boolean deleteInvoice(String invoiceID) {
        String sql = "DELETE FROM invoices_64130299 WHERE invoiceID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, invoiceID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// Lấy danh sách tất cả hóa đơn từ cơ sở dữ liệu
	public List<InvoiceDTO> getAllInvoices() {
	    String query = "SELECT InvoiceID, BookingID, Amount, PaymentMethod, CreatedAt FROM invoices_64130299";

	    try (PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet resultSet = stmt.executeQuery()) {
	        
	        // Xóa danh sách cũ để đảm bảo dữ liệu không bị trùng lặp
	        invoiceDTOs.clear();

	        while (resultSet.next()) {
	            // Tạo đối tượng InvoiceDTO và gán dữ liệu từ kết quả truy vấn
	            InvoiceDTO invoice = new InvoiceDTO();
	            invoice.setInvoiceID(resultSet.getString("InvoiceID"));
	            invoice.setBookingID(resultSet.getString("BookingID"));
	            invoice.setAmount(resultSet.getBigDecimal("Amount"));
	            invoice.setPaymentMethod(PaymentMethod.fromString(resultSet.getString("PaymentMethod")));
	            invoice.setCreatedAt(resultSet.getTimestamp("CreatedAt"));

	            // Thêm đối tượng vào danh sách
	            invoiceDTOs.add(invoice);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Trả về danh sách hóa đơn
	    return invoiceDTOs;
	}
	
	// Phương thức tìm kiếm theo bookingID, startDate, và endDate
    public List<InvoiceDTO> searchBookings(String bookingID, LocalDate startDate, LocalDate endDate) {
        List<InvoiceDTO> results = new ArrayList<>();
        String query = "SELECT * FROM invoices_64130299 WHERE 1=1";  // Câu truy vấn cơ bản

        StringBuilder sql = new StringBuilder(query); // Tạo câu truy vấn linh hoạt

        // Thêm điều kiện vào câu truy vấn nếu bookingID không rỗng
        if (bookingID != null && !bookingID.isEmpty()) {
            sql.append(" AND bookingID LIKE ?");
        }
        // Thêm điều kiện cho ngày bắt đầu (startDate)
        if (startDate != null) {
            sql.append(" AND createdAt >= ?");
        }
        // Thêm điều kiện cho ngày kết thúc (endDate)
        if (endDate != null) {
            sql.append(" AND createdAt <= ?");
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1; // Chỉ mục tham số trong PreparedStatement

            // Nếu bookingID có giá trị, set giá trị cho câu truy vấn
            if (bookingID != null && !bookingID.isEmpty()) {
                statement.setString(paramIndex++, "%" + bookingID + "%");
            }

            // Nếu startDate có giá trị, set giá trị cho câu truy vấn
            if (startDate != null) {
                Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
                statement.setTimestamp(paramIndex++, startTimestamp);
            }

            // Nếu endDate có giá trị, set giá trị cho câu truy vấn
            if (endDate != null) {
                Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(23, 59, 59));
                statement.setTimestamp(paramIndex++, endTimestamp);
            }

            // Thực thi câu truy vấn
            ResultSet rs = statement.executeQuery();

            // Xử lý kết quả trả về từ câu truy vấn
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO(
                        rs.getString("invoiceID"),
                        rs.getString("bookingID"),
                        rs.getBigDecimal("amount"),
                        PaymentMethod.fromString(rs.getString("paymentMethod")), // Chuyển đổi từ String thành enum PaymentMethod
                        rs.getTimestamp("createdAt")
                );
                results.add(invoice); // Thêm đối tượng vào danh sách kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
	
 // Phương thức cập nhật phương thức thanh toán của hóa đơn
    public boolean updatePaymentMethod(String invoiceID, PaymentMethod paymentMethod) {
        String sql = "UPDATE invoices_64130299 SET PaymentMethod = ? WHERE InvoiceID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set giá trị cho câu truy vấn
            statement.setString(1, paymentMethod.toString()); // Cập nhật phương thức thanh toán
            statement.setString(2, invoiceID); // Xác định hóa đơn cần cập nhật

            // Thực thi câu truy vấn
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    // Phương thức lấy danh sách hóa đơn trong khoảng thời gian
    public List<InvoiceDTO> getInvoicesByDateRange(Timestamp startDate, Timestamp endDate) {
        // Kiểm tra nếu startDate và endDate là giống nhau
        // Nếu chúng giống nhau, ta sẽ đặt endDate thành cuối ngày (23:59:59)
        if (startDate.equals(endDate)) {
            // Chuyển đổi startDate sang LocalDateTime
            LocalDateTime startDateTime = startDate.toLocalDateTime();
            // Điều chỉnh endDate thành 23:59:59 của ngày hôm đó
            LocalDateTime endDateTime = startDateTime.with(LocalTime.MAX);
            endDate = Timestamp.valueOf(endDateTime);  // Chuyển đổi lại thành Timestamp
        }

        List<InvoiceDTO> invoices = new ArrayList<>();
        
        // Truy vấn SQL để lấy hóa đơn trong khoảng thời gian từ startDate đến endDate
        String sql = "SELECT * FROM invoices_64130299 WHERE createdAt BETWEEN ? AND ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Thiết lập tham số cho câu lệnh SQL (startDate và endDate)
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                // Lặp qua kết quả truy vấn và thêm các hóa đơn vào danh sách
                while (rs.next()) {
                    InvoiceDTO invoice = new InvoiceDTO();
                    invoice.setInvoiceID(rs.getString("invoiceID"));
                    invoice.setAmount(rs.getBigDecimal("amount"));
                    invoice.setCreatedAt(rs.getTimestamp("createdAt"));
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return invoices; // Trả về danh sách hóa đơn
    }
	
    //Tạo hóa đơn sau khi xác nhận thanh toán thành công từ lớp booking
    public InvoiceDTO createInvoiceFromBooking(BookingDTO booking) {
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setBookingID(booking.getBookingID());
        invoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return invoice;
    }
} 
