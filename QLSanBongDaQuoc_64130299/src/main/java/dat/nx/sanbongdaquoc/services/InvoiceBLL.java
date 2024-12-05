package dat.nx.sanbongdaquoc.services;

import dat.nx.sanbongdaquoc.enums.*;
import dat.nx.sanbongdaquoc.models.entities.*;
import dat.nx.sanbongdaquoc.repositories.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public class InvoiceBLL {
	private InvoiceDAL invoiceDAL = new InvoiceDAL();
	
	//Xử lý trước khi thêm hóa đơn
	public boolean addInvoice(InvoiceDTO invoiceDTO) {
		return invoiceDAL.addInvoice(invoiceDTO);
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
}
