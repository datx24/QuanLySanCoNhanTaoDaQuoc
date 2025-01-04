package dat.nx.sanbongdaquoc.enums;

public enum FieldStatus {
    AVAILABLE("Có sẵn"),
    MAINTENANCE("Bảo trì");

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

