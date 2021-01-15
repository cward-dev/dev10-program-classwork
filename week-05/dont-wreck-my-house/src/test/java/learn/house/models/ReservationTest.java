package learn.house.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    Guest guest = new Guest(643,
            "Ambrose", "Koschek",
            "akoschekhu@mayoclinic.com",
            "(336) 3775870",
            State.NORTH_CAROLINA);

    Host host = new Host("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c",
            "Rosenkranc",
            "irosenkranc8w@reverbnation.com",
            "(970) 7391162", "7 Kennedy Plaza", "Greeley", State.COLORADO, "80638",
            new BigDecimal("180"),
            new BigDecimal("225"));

    Reservation reservation = new Reservation(1,
            LocalDate.of(2020,10,13),
            LocalDate.of(2020,10,17),
            host, guest, new BigDecimal("765.00")); // total should be 720

    Reservation test = new Reservation(1,
            LocalDate.of(2021,1,15),
            LocalDate.of(2021,1,16),
            host, guest, new BigDecimal("450.00")); // total should be 720

//    @Test
//    void shouldGetTotalTest() {
//        BigDecimal total = test.calculateTotal();
//        assertEquals(test.getTotal(), total);
//    }

    @Test
    void shouldGetTotal() {
        BigDecimal total = reservation.calculateTotal();
        assertEquals(reservation.getTotal(), total);
    }

    @Test
    void shouldNotGetTotalIfStartDateNull() {
        reservation.setStartDate(null);
        assertNull(reservation.calculateTotal());
    }

    @Test
    void shouldNotGetTotalIfEndDateNull() {
        reservation.setEndDate(null);
        assertNull(reservation.calculateTotal());
    }

    @Test
    void shouldNotGetTotalIfStandardRateNull() {
        reservation.getHost().setStandardRate(null);
        assertNull(reservation.calculateTotal());
    }

    @Test
    void shouldNotGetTotalIfStandardRateNegative() {
        reservation.getHost().setStandardRate(new BigDecimal("-1.00"));
        assertNull(reservation.calculateTotal());
    }

    @Test
    void shouldNotGetTotalIfWeekendRateNull() {
        reservation.getHost().setWeekendRate(null);
        assertNull(reservation.calculateTotal());
    }

    @Test
    void shouldNotGetTotalIfWeekendRateNegative() {
        reservation.getHost().setWeekendRate(new BigDecimal("-1.00"));
        assertNull(reservation.calculateTotal());
    }


}