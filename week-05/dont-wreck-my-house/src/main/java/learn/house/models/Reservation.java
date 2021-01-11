package learn.house.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    String id;
    LocalDate startDate;
    LocalDate endDate;
    int guestId;
    BigDecimal total;

    public Reservation() {
    }

    public Reservation(String id, LocalDate startDate, LocalDate endDate, int guestId, BigDecimal total) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestId = guestId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean overlapsReservation(Reservation reservation) {
        return this.startDate.isBefore(reservation.endDate)
                || this.endDate.isAfter(reservation.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }
}
