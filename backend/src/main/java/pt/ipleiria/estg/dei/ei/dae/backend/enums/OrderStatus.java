package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum OrderStatus {
    PENDING(1, "Pending order"),
    IN_DELIVERY(1, "Order in delivery process"),
    DELIVERED(2, "Delivered order");


    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
