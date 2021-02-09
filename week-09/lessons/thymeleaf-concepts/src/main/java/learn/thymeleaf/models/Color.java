package learn.thymeleaf.models;

public class Color {

    private int colorId;
    private String name;

    public Color() {
    }

    public Color(int colorId, String name) {
        this.colorId = colorId;
        this.name = name;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "colorId=" + colorId +
                ", name='" + name + '\'' +
                '}';
    }
}
