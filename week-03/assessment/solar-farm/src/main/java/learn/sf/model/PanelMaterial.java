package learn.sf.model;

public enum PanelMaterial {

    MULTICRYSTALLINE_SILICON("Multicrystalline Silicon", "c-Si"),
    MONOCRYSTALLINE_SILICON("Monocrystalline Silicon", "mono-Si"),
    AMORPHOUS_SILICON("Amorphous Silicon", "a-Si"),
    CADMIUM_TELLURIDE("Cadmium Telluride", "CdTe"),
    COPPER_INDIUM_GALLIUM_SELENIDE("Copper Indium Gallium Selenide", "CIGS");

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
