package myEnum;

public enum FieldStatus {
    AVAILABLE("Available"),//có sẵn
    MAINTENANCE("Maintenance"),//bảo trì
    BOOKED("Booked");//đã đặt

    private final String status;

    FieldStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static FieldStatus fromString(String status) {
        for (FieldStatus fs : FieldStatus.values()) {
            if (fs.getStatus().equalsIgnoreCase(status)) {
                return fs;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

