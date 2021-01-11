package learn.foraging.domain;

import learn.foraging.data.*;
import learn.foraging.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    ForageRepository repository = new ForageRepositoryDouble();

    ReportService reports = new ReportService(repository);

    LocalDate date = LocalDate.of(2020, 1, 1);

    public final static Item RAMPS = new Item(1, "Ramps", Category.EDIBLE, new BigDecimal("5.00"));
    public final static Item PURSLANE = new Item(2, "Purslane", Category.EDIBLE, new BigDecimal("2.95"));
    public final static Item MUSHROOM = new Item(8, "Chicken of the Woods Mushroom", Category.EDIBLE, new BigDecimal("14.95"));

    @BeforeEach
    void setup() throws DataException {
        Forage forage;
        for (int i = 0; i < 10; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(RAMPS);
            forage.setKilograms(2);

            repository.add(forage);
        }

        for (int i = 0; i < 5; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(PURSLANE);
            forage.setKilograms(4.37);

            repository.add(forage);
        }

        for (int i = 0; i < 5; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(MUSHROOM);
            forage.setKilograms(2.5);

            repository.add(forage);
        }
    }

    @Test
    void shouldGetMapOfAllItemsAndAmountCollectedOnDate() {
        Result<Map<Item, Double>> actual = reports.getKilogramsOfEachItemCollected(date);
        assertTrue(actual.isSuccess());
        assertEquals(20.00, actual.getPayload().get(RAMPS));
        assertEquals(21.85, actual.getPayload().get(PURSLANE));
        assertEquals(12.50, actual.getPayload().get(MUSHROOM));
    }

    @Test
    void shouldReturnNullPayloadIfNoForagesForReportOfKgCollected() {
        Result<Map<Item, Double>> actual = reports.getKilogramsOfEachItemCollected(LocalDate.of(1987,1,1));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldGetReportOfTotalValueByCategory() {
        Result<Map<String, BigDecimal>> actual = reports.getTotalValueOfEachCategoryCollected(date);
        assertTrue(actual.isSuccess());
        assertEquals(351.33, actual.getPayload().get(Category.EDIBLE.getName()).doubleValue(), 0.01);
    }

    @Test
    void shouldReturnNullPayloadIfNoForagesForReportOfTotalValueByCategory() {
        Result<Map<String, BigDecimal>> actual = reports.getTotalValueOfEachCategoryCollected(LocalDate.of(1987,1,1));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
    }
}