package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
	
	//Xử lý trước khi cập nhật hóa đơn
	public boolean updateInvoice(InvoiceDTO invoiceDTO) {
		return invoiceDAL.updateInvoice(invoiceDTO);
	}
	
	//Xử lý trước khi xóa hóa đơn
	public boolean deleteInvoice(String invoiceID) {
		InvoiceDTO invoiceDTO = getInvoiceByID(invoiceID);
		return invoiceDAL.deleteInvoice(invoiceDTO);
	}
	
	//Lấy danh sách tất cả hóa đơn
	public List<InvoiceDTO> getAllInvoices() {
		return invoiceDAL.getAllInvoices();
	}
	
	//Lấy hóa đơn theo id
	public InvoiceDTO getInvoiceByID(String invoiceID) {
		return invoiceDAL.getInvoiceByID(invoiceID);
	}
	
	//Lấy hóa đơn theo bookingID
	public InvoiceDTO getInvoiceByBookingID(String invoiceID) {
		return invoiceDAL.getInvoiceByBookingID(invoiceID);
	}
	
	//Kiểm tra trạng thái thanh toán hóa đơn
	public PaymentMethod checkPaymentMethodStatus(String invoiceID) {
		return invoiceDAL.checkPaymentStatus(invoiceID);
	}
	
	//Tính tổng số tiền của hóa đơn trong khoảng thời gian
	public BigDecimal calculateTotalAmountByDateRange(Timestamp startDate, Timestamp endDate) {
    	return invoiceDAL.calculateTotalAmountByDateRange(startDate, endDate);
    }
	
	//Lấy danh sách các hóa đơn chưa thanh toán
	public List<InvoiceDTO> getUnpaidInvoices() {
		return invoiceDAL.getAllInvoices();//thay đổi cách trả về
	}
	
	//Xử lý thanh toán hóa đơn
	public boolean processPayment(String invoiceID, PaymentMethod method) {
		InvoiceDTO invoiceDTO = getInvoiceByID(invoiceID);
		return updateInvoice(invoiceDTO);
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
