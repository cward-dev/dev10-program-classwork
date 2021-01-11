package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ItemRepository;
import learn.foraging.models.Category;
import learn.foraging.models.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> findByCategory(Category category) {
        return repository.findAll().stream()
                .filter(i -> i.getCategory() == category)
                .collect(Collectors.toList());
    }

    public Result<Item> add(Item item) throws DataException {
        Result<Item> result = validate(item);
        if (!result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(item);
        if (duplicate) {
            result.addErrorMessage(String.format("Item '%s' already exists.", item.getName()));
            return result;
        }

        item = repository.add(item);
        result.setPayload(item);

        return result;
    }

    public Result<Item> update(Item item) throws DataException {
        Result<Item> result = validate(item);
        if (!result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(item, true);
        if (duplicate) {
            result.addErrorMessage(String.format("Item '%s' already exists.", item.getName()));
            return result;
        }

        boolean success = repository.update(item);
        if (!success) {
            result.addErrorMessage(String.format("Item Id %s not found.", item.getId()));
        }
        result.setPayload(item);

        return result;

    }

    private Result<Item> validate(Item item) {
        Result<Item> result = new Result<>();

        if (item == null) {
            result.addErrorMessage("Item must not be null.");
            return result;
        }

        if (item.getName() == null || item.getName().isBlank()) {
            result.addErrorMessage("Item name is required.");
        }

        if (item.getCategory() == null) {
            result.addErrorMessage("Item category is required.");
        }

        if (item.getDollarPerKilogram() == null) {
            result.addErrorMessage("$/Kg is required.");
        } else if (item.getDollarPerKilogram().compareTo(BigDecimal.ZERO) < 0
                || item.getDollarPerKilogram().compareTo(new BigDecimal("7500.00")) > 0) {
            result.addErrorMessage("%/Kg must be between 0.00 and 7500.00.");
        }

        return result;
    }

    private boolean checkForDuplicate(Item item) {
        return repository.findAll().stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(item.getName()));
    }

    // Overloaded for update
    private boolean checkForDuplicate(Item item, boolean forUpdate) {
        List<Item> items = repository.findAll();

        if (forUpdate) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == item.getId()) {
                    items.remove(i);
                    break;
                }
            }
        }

        return items.stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(item.getName()));
    }
}
