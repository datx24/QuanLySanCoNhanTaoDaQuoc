package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.InvoiceDTO;
import Enum.PaymentMethod;

public class InvoiceDAL {
	//Thêm hóa đơn thanh toán
	public boolean addInvoice(InvoiceDTO invoice) {
		return true;
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
		List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();
		return invoices;
	}
	
	//Lấy hóa đơn theo ID
	public InvoiceDTO getInvoiceByID(String invoiceID) {
		InvoiceDTO invoice = new InvoiceDTO();
		return invoice;
	}
	
	//Lấy hóa đơn dựa trên BookingID
	public InvoiceDTO getInvoiceByBookingID(String bookingID) {
		InvoiceDTO invoice = new InvoiceDTO();
		return invoice;
	}
	
	//Phương thức kiểm tra trạng thái thanh toán của hóa đơn 
	public PaymentMethod checkPaymentStatus(String invoiceID) {
		return null;
	}
} 
