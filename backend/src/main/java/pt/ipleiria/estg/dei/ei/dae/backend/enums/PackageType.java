package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum PackageType {
    PRIMARY(1, "Primary packaging"),
    SECONDARY(2, "Secondary packaging"),
    TERTIARY(3, "Tertiary packaging"),
    TRANSPORT(4, "Transport packaging");


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
