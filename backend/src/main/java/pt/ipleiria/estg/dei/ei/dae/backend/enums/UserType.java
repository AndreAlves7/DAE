package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum UserType {
    MANUFACTURER(1, "Product manufacturer"),
    OPERATOR(2, "Logistics operator"),
    CONSUMER(3, "Final consumer");

    private final int code;
    private final String description;

    UserType(int code, String description) {
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
