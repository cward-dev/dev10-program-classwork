package learn.thymeleaf.models;

public class Size {

    private int sizeId;
    private String name;

    public Size() {
    }

    public Size(int sizeId, String name) {
        this.sizeId = sizeId;
        this.name = name;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Size{" +
                "sizeId=" + sizeId +
                ", name='" + name + '\'' +
                '}';
    }
}
