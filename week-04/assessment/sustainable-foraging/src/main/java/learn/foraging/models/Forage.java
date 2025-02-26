package learn.foraging.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Forage {

    private String id;
    private LocalDate date;
    private Forager forager;
    private Item item;
    private double kilograms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Forager getForager() {
        return forager;
    }

    public void setForager(Forager forager) {
        this.forager = forager;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getKilograms() {
        return kilograms;
    }

    public void setKilograms(double kilograms) {
        this.kilograms = kilograms;
    }

    public BigDecimal getValue() {
        if (item == null || item.getDollarPerKilogram() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal kilos = new BigDecimal(kilograms).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forage forage = (Forage) o;
        return this.getDate().compareTo(forage.getDate()) == 0 &&
                this.getItem().equals(forage.getItem()) &&
                Objects.equals(this.getForager().getId(), forage.getForager().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, forager, item, kilograms);
    }

}
