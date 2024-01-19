package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum PackageMaterialType {
    CARDBOARD(1, "CARDBOARD"),
    PLASTIC(2, "PLASTIC"),
    METAL(3, "METAL");


    private final int code;
    private final String description;

    PackageMaterialType(int code, String description) {
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
