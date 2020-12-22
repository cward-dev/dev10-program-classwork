package learn.cards;

public enum Suit {

    SPADES("Spades", 4), HEARTS("Hearts", 3), DIAMONDS("Diamonds", 2), CLUBS("Clubs", 1);

    private String name;
    private int value;

    Suit(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
