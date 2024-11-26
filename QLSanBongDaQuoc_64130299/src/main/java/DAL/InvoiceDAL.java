package DAL;

import java.util.ArrayList;
import java.util.List;

import DTO.InvoiceDTO;
import Enum.PaymentMethod;

public class InvoiceDAL {
	private InvoiceDTO invoiceDTO = new InvoiceDTO();
	private List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
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
} 
