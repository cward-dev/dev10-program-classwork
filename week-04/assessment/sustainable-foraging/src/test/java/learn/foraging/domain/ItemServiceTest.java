package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ItemRepositoryDouble;
import learn.foraging.models.Category;
import learn.foraging.models.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    ItemService service = new ItemService(new ItemRepositoryDouble());

    @Test
    void shouldNotSaveNullName() throws DataException {
        Item item = new Item(0, null, Category.EDIBLE, new BigDecimal("5.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveBlankName() throws DataException {
        Item item = new Item(0, "   \t\n", Category.EDIBLE, new BigDecimal("5.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveNullCategory() throws DataException {
        Item item = new Item(0, "Test Item", null, new BigDecimal("5.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveNullDollars() throws DataException {
        Item item = new Item(0, "Test Item", Category.EDIBLE, null);
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveNegativeDollars() throws DataException {
        Item item = new Item(0, "Test Item", Category.EDIBLE, new BigDecimal("-5.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveTooLargeDollars() throws DataException {
        Item item = new Item(0, "Test Item", Category.EDIBLE, new BigDecimal("9999.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveDuplicateName() throws DataException {
        Item item = new Item(0, "Test Item", Category.EDIBLE, new BigDecimal("5.00"));
        Result<Item> result = service.add(item);

        assertTrue(result.isSuccess());

        item.setId(100);
        result = service.add(item);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldSave() throws DataException {
        Item item = new Item(0, "Test Item", Category.EDIBLE, new BigDecimal("5.00"));

        Result<Item> result = service.add(item);

        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldUpdate() throws DataException {
        Item item = new Item(1, "Chanterelle", Category.POISONOUS, new BigDecimal("9.99"));

        Result<Item> result = service.update(item);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
        assertEquals(Category.POISONOUS, result.getPayload().getCategory());
    }

    @Test
    void shouldNotUpdateDuplicateName() throws DataException {
        Item item = new Item(2, "Test Item", Category.EDIBLE, new BigDecimal("5.00"));
        Result<Item> addResult = service.add(item);
        assertTrue(addResult.isSuccess());

        item.setName("Chanterelle");

        Result<Item> result = service.update(item);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullItem() throws DataException {
        Item item = null;

        Result<Item> result = service.update(item);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMissingName() throws DataException {
        Item item = new Item(1, " ", Category.POISONOUS, new BigDecimal("9.99"));

        Result<Item> result = service.update(item);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullCategory() throws DataException {
        Item item = new Item(1, "Chanterelle", null, new BigDecimal("9.99"));

        Result<Item> result = service.update(item);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullBigDecimal() throws DataException {
        Item item = new Item(1, "Chanterelle", Category.POISONOUS, null);

        Result<Item> result = service.update(item);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

}