package dat.nx.sanbongdaquoc.enums;

public enum PaymentMethod {
    CASH("Cash"),// tiền mặt
    ONLINE("Online");// chuyển khoản

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return method;
    }

    @Override
    public String toString() {
        return this.method;
    }

    public static PaymentMethod fromString(String method) {
        for (PaymentMethod paymentMethods : PaymentMethod.values()) {
            if (paymentMethods.method.equalsIgnoreCase(method)) {
                return paymentMethods;
            }
        }
        throw new IllegalArgumentException("No enum constant with status " + method);
    }
}
