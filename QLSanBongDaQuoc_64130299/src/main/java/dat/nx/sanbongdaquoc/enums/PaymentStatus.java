package dat.nx.sanbongdaquoc.enums;

public enum PaymentStatus {
    PAID("Đã thanh toán"),    // Đã thanh toán
    UNPAID("Chưa thanh toán"); // Chưa thanh toán

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static PaymentStatus fromString(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.status.equalsIgnoreCase(status)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant with status " + status);
    }
}
