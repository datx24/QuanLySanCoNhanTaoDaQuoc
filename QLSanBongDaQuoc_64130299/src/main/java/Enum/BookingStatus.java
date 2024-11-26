package Enum;

public enum BookingStatus {
    PENDING("Pending"), // chờ duyệt
    CONFIRMED("Confirmed"), // xác nhận
    CANCELLED("Cancelled"), // đã hủy
    NOSHOW("No-Show"); // không xuất hiện

    private final String status;

    // Constructor
    BookingStatus(String status) {
        this.status = status;
    }

    // Getter
    public String getStatus() {
        return status;
    }

    // Override toString() để dễ dàng hiển thị
    @Override
    public String toString() {
        return this.status;
    }
    
    // Optional: Phương thức giúp chuyển đổi từ String thành enum
    public static BookingStatus fromString(String status) {
        for (BookingStatus bookingStatus : BookingStatus.values()) {
            if (bookingStatus.status.equalsIgnoreCase(status)) {
                return bookingStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant with status " + status);
    }
}

