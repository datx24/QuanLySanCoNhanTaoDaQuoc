package DTO;

import java.math.BigDecimal;

import Enum.PaymentMethod;

public class InvoiceDTO {
	private String invoiceID;
    private String bookingID;
    private BigDecimal amount;// Tổng số tiền thanh toán
    private PaymentMethod paymentMethod;// Phương thức thanh toán (Cash, Online)
    
	public InvoiceDTO() {
	}

	public InvoiceDTO(String invoiceID, String bookingID, BigDecimal amount, PaymentMethod paymentMethod) {
		super();
		this.invoiceID = invoiceID;
		this.bookingID = bookingID;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
    
    
}
