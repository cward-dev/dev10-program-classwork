package learn.thymeleaf.domain;

import learn.thymeleaf.models.Color;
import learn.thymeleaf.models.Listing;
import learn.thymeleaf.models.Size;
import learn.thymeleaf.models.TShirt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelService {

    private ArrayList<Color> colors = makeColors();
    private ArrayList<Size> sizes = makeSizes();
    private ArrayList<TShirt> shirts = makeTShirts();
    private ArrayList<Listing> listings = makeListings();

    public List<Color> getAllColors() {
        return new ArrayList<>(colors);
    }

    public Color getColorById(int colorId) {
        return colors.stream()
                .filter(i -> i.getColorId() == colorId)
                .findFirst()
                .orElse(null);
    }

    public List<Size> getAllSizes() {
        return new ArrayList<>(sizes);
    }

    public Size getSizeById(int sizeId) {
        return sizes.stream()
                .filter(i -> i.getSizeId() == sizeId)
                .findFirst()
                .orElse(null);
    }

    public List<TShirt> getAllTShirts() {
        return new ArrayList<>(shirts);
    }

    public TShirt getTShirtById(int id) {
        return shirts.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void save(TShirt shirt) {
        if (shirt.getId() > 0) {
            for (int i = 0; i < shirts.size(); i++) {
                if (shirts.get(i).getId() == shirt.getId()) {
                    shirts.set(i, shirt);
                }
            }
        } else {
            int nextId = shirts.stream().mapToInt(i -> i.getId()).max().orElse(0);
            shirt.setId(nextId + 1);
            shirts.add(shirt);
        }
    }

    public void deleteTShirtById(int id) {
        shirts.removeIf(i -> i.getId() == id);
    }

    public List<Listing> getAllListings() {
        return new ArrayList<>(listings);
    }

    public Listing getListingById(int id) {
        return listings.stream()
                .filter(i -> i.getListingId() == id)
                .findFirst()
                .orElse(null);
    }

    public void save(Listing listing) {
        if (listing.getListingId() > 0) {
            for (int i = 0; i < listings.size(); i++) {
                if (listings.get(i).getListingId() == listing.getListingId()) {
                    listings.set(i, listing);
                }
            }
        } else {
            int nextId = listings.stream().mapToInt(i -> i.getListingId()).max().orElse(0);
            listing.setListingId(nextId + 1);
            listings.add(listing);
        }
    }

    private ArrayList<Color> makeColors() {
        ArrayList<Color> result = new ArrayList<>();
        result.add(new Color(1, "Red"));
        result.add(new Color(2, "Orange"));
        result.add(new Color(3, "Yellow"));
        result.add(new Color(4, "Green"));
        result.add(new Color(5, "Blue"));
        result.add(new Color(6, "Indigo"));
        result.add(new Color(7, "Violet"));
        return result;
    }

    private ArrayList<Size> makeSizes() {
        ArrayList<Size> result = new ArrayList<>();
        result.add(new Size(1, "X-Small"));
        result.add(new Size(2, "Small"));
        result.add(new Size(3, "Medium"));
        result.add(new Size(4, "Large"));
        result.add(new Size(5, "X-Large"));
        result.add(new Size(6, "XX-Large"));
        return result;
    }

    private ArrayList<TShirt> makeTShirts() {
        ArrayList<TShirt> result = new ArrayList<>();
        result.add(new TShirt(1, "Dinosaurs", colors.get(3), sizes.get(1)));
        result.add(new TShirt(2, "Seahorse", colors.get(5), sizes.get(2)));
        result.add(new TShirt(3, "Polar Bear", colors.get(1), sizes.get(4)));
        return result;
    }

    private ArrayList<Listing> makeListings() {
        ArrayList<Listing> result = new ArrayList<>();

        result.add(new Listing(1, "Dinosaur T-Shirt",
                List.of(colors.get(0), colors.get(1), colors.get(2)),
                List.of(sizes.get(1), sizes.get(2))));

        result.add(new Listing(2, "Seahorse T-Shirt",
                colors,
                List.of(sizes.get(1), sizes.get(2), sizes.get(3), sizes.get(4))));

        result.add(new Listing(3, "Polar Bear T-Shirt",
                List.of(colors.get(1), colors.get(3), colors.get(5)),
                sizes));

        return result;
    }
}
