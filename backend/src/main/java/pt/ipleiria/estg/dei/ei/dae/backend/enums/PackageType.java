package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum PackageType {
    PRIMARY(1, "TRANSPORT"),
    SECONDARY(2, "SECONDARY"),
    TERTIARY(3, "TERTIARY"),
    TRANSPORT(4, "TRANSPORT");


    private final int code;
    private final String description;

    PackageType(int code, String description) {
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
