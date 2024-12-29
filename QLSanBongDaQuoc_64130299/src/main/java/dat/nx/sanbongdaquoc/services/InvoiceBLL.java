package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class InvoiceBLL {
	private InvoiceDAL invoiceDAL;
	
	public InvoiceBLL(InvoiceDAL invoiceDAL) {
		this.invoiceDAL = invoiceDAL;
	}

	
	//Xử lý trước khi thêm hóa đơn
	public InvoiceDTO createInvoice(BookingDTO booking, PaymentMethod paymentMethod) {
        if (booking == null || paymentMethod == null) {
            throw new IllegalArgumentException("Đơn đặt sân hoặc phương thức thanh toán không được null.");
        }

        // Tạo hóa đơn mới
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setBookingID(booking.getBookingID());
        invoice.setAmount(BigDecimal.valueOf(100)); // Có thể tính tổng giá trị từ booking nếu có
        invoice.setPaymentMethod(paymentMethod);
        invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        boolean success = invoiceDAL.insertInvoice(invoice);

        if (!success) {
            throw new RuntimeException("Không thể lưu hóa đơn vào cơ sở dữ liệu.");
        }

        return invoice;
    }
	
	// Xử lý trước khi xóa hóa đơn
    public boolean deleteInvoice(String invoiceID) {
        return invoiceDAL.deleteInvoice(invoiceID);
    }
	

	//Lấy danh sách tất cả hóa đơn
	public List<InvoiceDTO> getAllInvoices() {
		return invoiceDAL.getAllInvoices();
	}
	
	// Phương thức tìm kiếm hóa đơn theo bookingID, startDate, endDate
    public List<InvoiceDTO> searchInvoices(String bookingID, LocalDate startDate, LocalDate endDate) {
        // Gọi phương thức tìm kiếm từ DAL
        return invoiceDAL.searchBookings(bookingID, startDate, endDate);
    }
    
    // Phương thức gọi DAL để cập nhật phương thức thanh toán
    public boolean updatePaymentMethod(String invoiceID, PaymentMethod paymentMethod) {
        return invoiceDAL.updatePaymentMethod(invoiceID, paymentMethod);
    }
	
    // Phương thức tính tổng số tiền trong khoảng thời gian
    public BigDecimal calculateTotalAmountByDateRange(Timestamp startDate, Timestamp endDate) {
        // Lấy danh sách hóa đơn trong khoảng thời gian từ DAL
        List<InvoiceDTO> invoices = invoiceDAL.getInvoicesByDateRange(startDate, endDate);
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Tính tổng số tiền
        for (InvoiceDTO invoice : invoices) {
            totalAmount = totalAmount.add(invoice.getAmount());
        }

        // Định dạng số tiền theo chuẩn VND (thêm dấu phân cách cho hàng nghìn)
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##"); // Định dạng cho dấu phân cách và phần thập phân

        // Loại bỏ phần thập phân nếu là 0
        String formattedAmount = decimalFormat.format(totalAmount);

        // In ra số tiền theo định dạng VND
        System.out.println("Tổng số tiền trong khoảng thời gian: " + formattedAmount + " VND");

        return totalAmount;
    }

    // Phương thức tính tổng số hóa đơn trong khoảng thời gian
    public int calculateTotalInvoicesByDateRange(Timestamp startDate, Timestamp endDate) {
        // Lấy danh sách hóa đơn trong khoảng thời gian từ DAL
        List<InvoiceDTO> invoices = invoiceDAL.getInvoicesByDateRange(startDate, endDate);

        // Trả về số lượng hóa đơn
        return invoices.size();
    }
	
	//Lấy danh sách các hóa đơn chưa thanh toán
	public List<InvoiceDTO> getUnpaidInvoices() {
		return invoiceDAL.getAllInvoices();//thay đổi cách trả về
	}
	
	//Lấy hóa đơn theo mã khách hàng
	public List<InvoiceDTO> getInvoicesByUserID(String UserID) {
		return invoiceDAL.getAllInvoices();
	}
	
	//Tạo báo cáo tổng hợp hóa đơn
	public String generateInvoiceSumaryReport() {
		List<InvoiceDTO> invoices = invoiceDAL.getAllInvoices();
		return "Invoice Summary: " + invoices.size() + " invoices found.";
	}
	
	//Tạo hóa đơn sau khi xác nhận thanh toán thành công từ lớp bookinh
    public InvoiceDTO createInvoiceFromBooking(BookingDTO booking) {
        return invoiceDAL.createInvoiceFromBooking(booking);
    }
}
