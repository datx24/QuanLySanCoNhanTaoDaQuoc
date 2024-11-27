package myEnum;

public enum PaymentMethod {
    CASH("Cash"),// tiền mặt
    ONLINE("Online");// chuyển khoản

    private final String status;

    PaymentMethod(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static PaymentMethod fromString(String status) {
        for (PaymentMethod paymentStatus : PaymentMethod.values()) {
            if (paymentStatus.status.equalsIgnoreCase(status)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant with status " + status);
    }
}
