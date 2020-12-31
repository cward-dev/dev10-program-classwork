package learn.sf.model;

public enum PanelMaterial {

    MULTICRYSTALLINE_SILICON("multicrystalline silicon", "c-Si"),
    MONOCRYSTALLINE_SILICON("monocrystalline silicon", "mono-Si"),
    AMORPHOUS_SILICON("amorphous silicon", "a-Si"),
    CADMIUM_TELLURIDE("cadmium telluride", "CdTe"),
    COPPER_INDIUM_GALLIUM_SELENIDE("copper indium gallium selenide", "CIGS");

    private final String name;
    private final String abbreviation;

    PanelMaterial(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
