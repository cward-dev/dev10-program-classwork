package learn.foraging.models;

public enum Category {
    EDIBLE("Edible"),
    MEDICINAL("Medicinal"),
    INEDIBLE("Inedible"),
    POISONOUS("Poisonous");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
