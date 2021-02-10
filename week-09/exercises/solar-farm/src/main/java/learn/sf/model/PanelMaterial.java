package learn.sf.model;

import java.util.Arrays;
import java.util.List;

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

    public PanelMaterial getFromAbbreviation(String abbreviation) {
        return Arrays.stream(PanelMaterial.values())
                .filter(m -> m.abbreviation.equalsIgnoreCase(abbreviation))
                .findFirst()
                .orElse(null);
    }
}
