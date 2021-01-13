package learn.house.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation {

    int id;
    LocalDate startDate;
    LocalDate endDate;
    Host host;
    Guest guest;
    BigDecimal total;

    public Reservation() {
    }

    public Reservation(int id, LocalDate startDate, LocalDate endDate, Host host, Guest guest, BigDecimal total) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.host = host;
        this.guest = guest;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotal() {
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean checkForOverlapWith(Reservation reservation) {
        return (this.startDate.isBefore(reservation.endDate) && this.endDate.isAfter(reservation.startDate))
                || (this.endDate.isAfter(reservation.startDate) && this.startDate.isBefore(reservation.endDate))
                || (!this.startDate.isAfter(reservation.startDate) && !this.endDate.isBefore(reservation.endDate));
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal("0.00");
        BigDecimal standardRate = host.getStandardRate();
        BigDecimal weekendRate = host.getWeekendRate();
        DayOfWeek dayOfWeek;

        if (startDate == null || endDate == null || !startDate.isBefore(endDate)) {
            return null;
        }

        if (standardRate == null || standardRate.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        if (weekendRate == null || weekendRate.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        for (int i = 0; i < startDate.until(endDate, ChronoUnit.DAYS); i++) {
            dayOfWeek = startDate.plusDays(i).getDayOfWeek(); // TODO THIS SEEMS LIKE A BUG, MATH WORKS BUT WHY SAT/SUN?
            if (dayOfWeek == DayOfWeek.SATURDAY
                    || dayOfWeek == DayOfWeek.SUNDAY) {
                total = total.add(weekendRate);
            } else {
                total = total.add(standardRate);
            }
        }

        return total.setScale(2, RoundingMode.HALF_EVEN);
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
