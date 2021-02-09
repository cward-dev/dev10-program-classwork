package learn.thymeleaf.models;

import java.util.ArrayList;
import java.util.List;

public class Listing {

    private int listingId;
    private String name;
    private List<Color> colors = new ArrayList<>();
    private List<Size> sizes = new ArrayList<>();

    public Listing() {
    }

    public Listing(int listingId, String name, List<Color> colors, List<Size> sizes) {
        this.listingId = listingId;
        this.name = name;
        this.colors = colors;
        this.sizes = sizes;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public void addColor(Color color) {
        this.colors.add(color);
    }

    public boolean hasColor(int colorId) {
        return colors.stream().anyMatch(i -> i.getColorId() == colorId);
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public void addSize(Size size) {
        this.sizes.add(size);
    }

    public boolean hasSize(int sizeid) {
        return sizes.stream().anyMatch(i -> i.getSizeId() == sizeid);
    }


    @Override
    public String toString() {
        return "Listing{" +
                "listingId=" + listingId +
                ", name='" + name + '\'' +
                ", colors=" + colors +
                ", sizes=" + sizes +
                '}';
    }
}
