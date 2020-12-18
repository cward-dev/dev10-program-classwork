package learn.commerce;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order order = new Order(25);

    @Test
    void shouldHaveOrderId() {
        assertEquals(25, order.getOrderId());
    }

    @Test
    void shouldAddValidItems() {
        LineItem grassSeed = new LineItem("Grass Seed", 19.99, 2);
        boolean result = order.add(grassSeed);
        assertTrue(result);
        assertEquals(1, order.getLineItems().length);
        assertEquals(grassSeed, order.getLineItems()[0]);

        LineItem gardenRake = new LineItem("Garden Rake", 44.99, 1);
        result = order.add(gardenRake);
        assertTrue(result);
        assertEquals(2, order.getLineItems().length);
        assertEquals(gardenRake, order.getLineItems()[1]);

        LineItem hose = new LineItem("Garden Hose - 50ft", 38.49, 1);
        result = order.add(hose);
        assertTrue(result);
        assertEquals(3, order.getLineItems().length);
        assertEquals(hose, order.getLineItems()[2]);

        LineItem freeGardenHoe = new LineItem("Free Garden Hoe", 0.00, 1);
        result = order.add(freeGardenHoe);
        assertTrue(result);
        assertEquals(4, order.getLineItems().length);
        assertEquals(freeGardenHoe, order.getLineItems()[3]);
    }

    // 1. Add test shouldNotAddInvalidItems: confirm that it's not possible to add items with <= 0 quantity or < 0 price.
    // 2. Test the order.getTotal() in various scenarios and confirm it's correct.
    // 3. If you tackle `order.remove`, test the method thoroughly.

    @Test
    void shouldNotAddInvalidItems() {
        LineItem grassSeed = new LineItem("Grass Seed", -3.50, 2);
        boolean result = order.add(grassSeed);
        assertFalse(result);
        assertEquals(0, order.getLineItems().length);

        LineItem gardenRake = new LineItem("Grass Seed", 44.99, 0);
        result = order.add(grassSeed);
        assertFalse(result);
        assertEquals(0, order.getLineItems().length);
    }

    @Test
    void shouldReturnTotalForSingleItems() {
        order.add(new LineItem("Grass Seed", 19.99, 1));
        order.add(new LineItem("Garden Rake", 44.99, 1));
        order.add(new LineItem("Garden Hose - 50ft", 38.49, 1));
        assertEquals(103.47, order.getTotal(), 0.001);
    }

    @Test
    void shouldReturnTotalForBulkItems() {
        order.add(new LineItem("Grass Seed", 19.99, 2));
        order.add(new LineItem("Garden Rake", 44.99, 4));
        order.add(new LineItem("Garden Hose - 50ft", 38.49, 1));
        assertEquals(258.43, order.getTotal(), 0.001);
    }

    @Test
    void shouldRemoveByIndex() {
        order.add(new LineItem("Grass Seed", 19.99, 2));
        order.add(new LineItem("Garden Rake", 44.99, 4));
        order.add(new LineItem("Garden Hose - 50ft", 38.49, 1));
        assertTrue(order.remove(1));
        assertEquals("Grass Seed", order.getLineItems()[0].getItemName());
        assertEquals("Garden Hose - 50ft", order.getLineItems()[1].getItemName());
        assertEquals(2, order.getLineItems().length);
    }

    @Test
    void shouldRemoveByReference() {
        LineItem rake = new LineItem("Garden Rake", 44.99, 4);
        order.add(rake);
        assertTrue(order.remove(rake));
        assertEquals(0, order.getLineItems().length);
    }

}