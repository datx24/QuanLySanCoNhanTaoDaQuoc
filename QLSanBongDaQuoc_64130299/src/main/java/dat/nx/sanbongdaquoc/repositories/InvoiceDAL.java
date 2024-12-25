package dat.nx.sanbongdaquoc.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	//Cập nhật hóa đơn thanh toán
	public boolean updateInvoice(InvoiceDTO invoice) {
		return true;
	}
	
	//Xóa hóa đơn thanh toán
	public boolean deleteInvoice(InvoiceDTO invoice) {
		return true;
	}
	
	//Lấy danh sách tất cả hóa đơn từ cơ sở dữ liệu 
	public List<InvoiceDTO> getAllInvoices() {
		return invoiceDTOs;
	}
	
	//Lấy hóa đơn theo ID
	public InvoiceDTO getInvoiceByID(String invoiceID) {
		return invoiceDTO;
	}
	
	//Lấy hóa đơn dựa trên BookingID
	public InvoiceDTO getInvoiceByBookingID(String bookingID) {
		return invoiceDTO;
	}
	
	//Phương thức kiểm tra trạng thái thanh toán của hóa đơn 
	public PaymentMethod checkPaymentStatus(String invoiceID) {
		return null;
	}
	
	//Phương thức tính tổng số tiền của 1 hóa đơn trong 1 khoảng thời gian
    public BigDecimal calculateTotalAmountByDateRange(Timestamp startDate, Timestamp endDate) {
    	BigDecimal totalAmount = BigDecimal.ZERO;
    	return totalAmount;
    }
    //Tạo hóa đơn sau khi xác nhận thanh toán thành công từ lớp bookinh
    public InvoiceDTO createInvoiceFromBooking(BookingDTO booking) {
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setBookingID(booking.getBookingID());
        invoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return invoice;
    }
} 
