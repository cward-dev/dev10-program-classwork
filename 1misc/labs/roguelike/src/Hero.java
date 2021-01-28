public class Hero extends GameObject {

    private final String name;
    private int energyCount = 15;

    // Create a hero with a name and an initial position.
    public Hero(String name, char symbol, int x, int y) {
        super(symbol, x, y);
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getEnergyCount() { return energyCount; }

    // setters
    public void setEnergyCount(int energyCount) { this.energyCount = energyCount; }

    // movement
    public void moveLeft() {
        this.setX(this.getX() - 1);
    }

    public void moveRight() {
        this.setX(this.getX() + 1);
    }

    public void moveUp() {
        this.setY(this.getY() - 1);
    }

    public void moveDown() {
        this.setY(this.getY() + 1);
    }
}
