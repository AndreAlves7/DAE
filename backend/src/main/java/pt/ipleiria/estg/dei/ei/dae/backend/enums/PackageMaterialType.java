package pt.ipleiria.estg.dei.ei.dae.backend.enums;

public enum PackageMaterialType {
    CARDBOARD(1, "Cardboard packaging"),
    PLASTIC(2, "Plastic packaging"),
    METAL(3, "Metal packaging");


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
