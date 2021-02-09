package learn.thymeleaf.models;

public class TShirt {

    private int id;
    private String name;
    private Color color;
    private Size size;

    public TShirt() {
    }

    public TShirt(int id, String name, Color color, Size size) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "TShirt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", size=" + size +
                '}';
    }
}
