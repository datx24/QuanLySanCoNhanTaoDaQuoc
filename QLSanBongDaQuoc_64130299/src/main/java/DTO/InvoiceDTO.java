package DTO;

import java.math.BigDecimal;

public class InvoiceDTO {
	private String invoiceID;
    private String bookingID;
    private BigDecimal amount;// Tổng số tiền thanh toán
    private String paymentMethod;// Phương thức thanh toán (Cash, Online)
}
