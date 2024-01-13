package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum PackageType {

    PRIMARY(1, "Individual Product packaging"),
    SECONDARY(2, "Product case packaging"),
    TERTIARY(3, "Shipment packaging");


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
