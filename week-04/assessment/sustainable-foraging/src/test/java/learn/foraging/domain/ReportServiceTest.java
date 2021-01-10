package learn.foraging.domain;

import learn.foraging.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    ReportService reports = new ReportService();

    List<Forage> forages = new ArrayList<>();
    LocalDate date = LocalDate.of(2020, 1, 1);

    public final static Item RAMPS = new Item(1, "Ramps", Category.EDIBLE, new BigDecimal("5.00"));
    public final static Item PURSLANE = new Item(2, "Purslane", Category.EDIBLE, new BigDecimal("2.95"));
    public final static Item MUSHROOM = new Item(8, "Chicken of the Woods Mushroom", Category.EDIBLE, new BigDecimal("14.95"));


    @BeforeEach
    void setup() throws IOException {
        Forage forage;
        for (int i = 0; i < 10; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(RAMPS);
            forage.setKilograms(2);

            forages.add(forage);
        }

        for (int i = 0; i < 5; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(PURSLANE);
            forage.setKilograms(4.37);

            forages.add(forage);
        }

        for (int i = 0; i < 5; i++) {
            forage = new Forage();
            forage.setDate(date);
            forage.setForager(new Forager());
            forage.setItem(MUSHROOM);
            forage.setKilograms(2.5);

            forages.add(forage);
        }
    }

    @Test
    void shouldGetReportOfKgCollected() {
        List<String> itemsKgCollected = reports.reportKilogramsOfEachItemCollected(forages, date);

        assertEquals("|  ID#|  Category|      Item Name| Kilograms |", itemsKgCollected.get(0));
        assertEquals("|    1|    Edible|          Ramps|   20.00kg |", itemsKgCollected.get(1));
        assertEquals("|    2|    Edible|       Purslane|   21.85kg |", itemsKgCollected.get(2));
        assertEquals("|    8|    Edible| Chicken of the|   12.50kg |", itemsKgCollected.get(3));
    }

    @Test
    void shouldGetReportOfTotalValueByCategory() {
        List<String> totalValueOfEachCategoryCollected = reports.reportTotalValueOfEachCategoryCollected(forages, date);

        assertEquals("|                          Category|   Total Value |", totalValueOfEachCategoryCollected.get(0));
        assertEquals("|                            Edible|       $351.33 |", totalValueOfEachCategoryCollected.get(1));
    }
}