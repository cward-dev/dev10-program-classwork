package learn.foraging.ui;

import learn.foraging.data.DataException;
import learn.foraging.domain.*;
import learn.foraging.models.Category;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Controller {

    @Autowired
    private final ForagerService foragerService;

    @Autowired
    private final ForageService forageService;

    @Autowired
    private final ItemService itemService;

    @Autowired
    private final ReportService reportService;

    @Autowired
    private final View view;

    @Autowired
    public Controller(ForagerService foragerService, ForageService forageService, ItemService itemService, ReportService reportService, View view) {
        this.foragerService = foragerService;
        this.forageService = forageService;
        this.itemService = itemService;
        this.reportService = reportService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Sustainable Foraging");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_FORAGES_BY_DATE:
                    viewByDate();
                    break;
                case VIEW_ITEMS:
                    viewItems();
                    break;
                case ADD_FORAGE:
                    addForage();
                    break;
                case ADD_FORAGER:
                    addForager();
                    break;
                case ADD_ITEM:
                    addItem();
                    break;
                case UPDATE_ITEM:
                    updateItem();
                    break;
                case REPORT_KG_PER_ITEM:
                    reportKgPerItem();
                    break;
                case REPORT_CATEGORY_VALUE:
                    reportTotalValueEachCategory();
                    break;
                case GENERATE:
                    generate();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    // top level menu
    private void viewByDate() {
        LocalDate date = view.getForageDate();
        List<Forage> forages = forageService.findByDate(date);
        view.displayForages(forages);
        view.enterToContinue();
    }

    private void viewItems() {
        view.displayHeader(MainMenuOption.VIEW_ITEMS.getMessage());
        Category category = view.getItemCategory();
        List<Item> items = itemService.findByCategory(category);
        view.displayHeader("Items");
        view.displayItems(items);
        view.enterToContinue();
    }

    private void addForage() throws DataException {
        view.displayHeader(MainMenuOption.ADD_FORAGE.getMessage());
        Forager forager = getForager();
        if (forager == null) {
            return;
        }
        Item item = getItem();
        if (item == null) {
            return;
        }
        Forage forage = view.makeForage(forager, item);
        Result<Forage> result = forageService.add(forage);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forage %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void addForager() throws DataException {
        Forager forager = view.makeForager();
        Result<Forager> result = foragerService.add(forager);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forager %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void addItem() throws DataException {
        Item item = view.makeItem();
        Result<Item> result = itemService.add(item);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Item %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void updateItem() throws DataException {
        Category category = view.getItemCategory();
        Item item = view.chooseItem(itemService.findByCategory(category));

        item = view.updateItem(item);

        Result<Item> result = itemService.update(item);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Item %s updated.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void generate() throws DataException {
        GenerateRequest request = view.getGenerateRequest();
        if (request != null) {
            int count = forageService.generate(request.getStart(), request.getEnd(), request.getCount());
            view.displayStatus(true, String.format("%s forages generated.", count));
        }
    }

    private void reportKgPerItem() {
        LocalDate date = view.getForageDate();

        Result<Map<Item, Double>> itemsKgCollectedResult = reportService.getKilogramsOfEachItemCollected(date);
        Result<List<String>> reportLines = ConsoleIOReport.reportKilogramsOfEachItemCollected(itemsKgCollectedResult);

        if (!reportLines.isSuccess()) {
            view.displayStatus(false, reportLines.getErrorMessages());
        } else {
            view.displayReport(reportLines.getPayload(), date, "Kilograms of Each Item");
        }
        view.enterToContinue();
    }

    private void reportTotalValueEachCategory() {
        LocalDate date = view.getForageDate();

        Result<Map<String, BigDecimal>> valueOfCategoriesCollectedResult = reportService.getTotalValueOfEachCategoryCollected(date);
        Result<List<String>> result = ConsoleIOReport.reportTotalValueOfEachCategoryCollected(valueOfCategoriesCollectedResult);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayReport(result.getPayload(), date, "Total Value of Each Category");
        }
        view.enterToContinue();
    }

    // support methods
    private Forager getForager() {
        String lastNamePrefix = view.getForagerNamePrefix();
        List<Forager> foragers = foragerService.findByLastName(lastNamePrefix);
        return view.chooseForager(foragers);
    }

    private Item getItem() {
        Category category = view.getItemCategory();
        List<Item> items = itemService.findByCategory(category);
        return view.chooseItem(items);
    }
}
