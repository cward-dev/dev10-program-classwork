package learn.foraging.ui;

import learn.foraging.domain.Result;
import learn.foraging.models.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }


    public State getState() {
        return io.readState("State Name or Abbreviation: ");
    }

    public LocalDate getForageDate() {
        displayHeader(MainMenuOption.VIEW_FORAGES_BY_DATE.getMessage());
        return io.readLocalDate("Select a date [MM/dd/yyyy]: ");
    }

    public String getForagerNamePrefix() {
        return io.readRequiredString("Forager last name starts with: ");
    }

    public Forager chooseForager(List<Forager> foragers) {
        if (foragers.size() == 0) {
            io.println("No foragers found");
            return null;
        }

        int index = 1;
        for (Forager forager : foragers.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%s: %s %s%n", index++, forager.getFirstName(), forager.getLastName());
        }
        index--;

        if (foragers.size() > 25) {
            io.println("More than 25 foragers found. Showing first 25. Please refine your search.");
        }
        io.println("0: Exit");
        String message = String.format("Select a forager by their index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return foragers.get(index - 1);
    }

    public Category getItemCategory() {
        displayHeader("Item Categories");
        int index = 1;
        for (Category c : Category.values()) {
            io.printf("%s: %s%n", index++, c);
        }
        index--;

        String message = String.format("Select a Category [1-%s]: ", index);
        return Category.values()[io.readInt(message, 1, index) - 1];
    }

    //Overloaded for update
    public Category getItemCategory(Item item) {
        displayHeader("Item Categories");
        int index = 1;
        int currentValue = 0;
        for (Category c : Category.values()) {
            if (item.getCategory() == c) {
                currentValue = index;
            }
            io.printf("%s: %s%n", index++, c);
        }
        index--;
        String message = String.format("Select a Category [%s]: ", currentValue);
        return Category.values()[io.readInt(message, 1, index, currentValue) - 1];
    }

    public Item chooseItem(List<Item> items) {

        displayItems(items);

        if (items.size() == 0) {
            return null;
        }

        while (true) {
            int itemId = io.readInt("Select an item id: ");
            Item item = items.stream()
                    .filter(i -> i.getId() == itemId)
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                return item;
            }

            displayStatus(false, String.format("No item with id %s found.", itemId));
        }
    }

    public Forage makeForage(Forager forager, Item item) {
        Forage forage = new Forage();
        forage.setForager(forager);
        forage.setItem(item);
        forage.setDate(io.readLocalDate("Forage date [MM/dd/yyyy]: "));
        String message = String.format("Kilograms of %s: ", item.getName());
        forage.setKilograms(io.readDouble(message, 0.001, 250.0));
        return forage;
    }

    public Forager makeForager() {
        displayHeader(MainMenuOption.ADD_FORAGER.getMessage());
        Forager forager = new Forager();
        forager.setFirstName(io.readRequiredString("Forager First Name: "));
        forager.setLastName(io.readRequiredString("Forager Last Name: "));
        forager.setState(io.readState("Forager State: "));
        return forager;
    }

    public Forager updateForager(Forager forager) {
        displayHeader(MainMenuOption.UPDATE_FORAGER.getMessage());
        Forager updatedForager = new Forager();

        updatedForager.setId(forager.getId());
        updatedForager.setFirstName(io.readString(String.format("Forager First Name [%s]: ", forager.getFirstName())));
        if (updatedForager.getFirstName().trim().length() == 0) {
            updatedForager.setFirstName(forager.getFirstName());
        }
        updatedForager.setLastName(io.readString(String.format("Forager Last Name [%s]: ", forager.getLastName())));
        if (updatedForager.getLastName().trim().length() == 0) {
            updatedForager.setLastName(forager.getLastName());
        }
        updatedForager.setState(io.readState(String.format("Forager State Name or Abbreviation [%s]: ", forager.getState().getAbbreviation()), forager));

        return updatedForager;
    }

    public Item makeItem() {
        displayHeader(MainMenuOption.ADD_ITEM.getMessage());
        Item item = new Item();
        item.setCategory(getItemCategory());
        item.setName(io.readRequiredString("Item Name: "));
        item.setDollarPerKilogram(io.readBigDecimal("$/Kg: ", BigDecimal.ZERO, new BigDecimal("7500.00")));
        return item;
    }

    public Item updateItem(Item item) {
        displayHeader(MainMenuOption.UPDATE_ITEM.getMessage());
        Item updatedItem = new Item();

        updatedItem.setId(item.getId());
        updatedItem.setCategory(getItemCategory(item));
        updatedItem.setName(io.readString(String.format("Item Name [%s]: ", item.getName())));
        if (updatedItem.getName().trim().length() == 0) {
            updatedItem.setName(item.getName());
        }
        updatedItem.setDollarPerKilogram(io.readBigDecimal(String.format("$/Kg [$%s]: ",
                item.getDollarPerKilogram().setScale(2, RoundingMode.HALF_EVEN)),
                BigDecimal.ZERO,
                new BigDecimal("7500.00"),
                item.getDollarPerKilogram()));
        return updatedItem;
    }

    public GenerateRequest getGenerateRequest() {
        displayHeader(MainMenuOption.GENERATE.getMessage());
        LocalDate start = io.readLocalDate("Select a start date [MM/dd/yyyy]: ");
        if (start.isAfter(LocalDate.now())) {
            displayStatus(false, "Start date must be in the past.");
            return null;
        }

        LocalDate end = io.readLocalDate("Select an end date [MM/dd/yyyy]: ");
        if (end.isAfter(LocalDate.now()) || end.isBefore(start)) {
            displayStatus(false, "End date must be in the past and after the start date.");
            return null;
        }

        GenerateRequest request = new GenerateRequest();
        request.setStart(start);
        request.setEnd(end);
        request.setCount(io.readInt("Generate how many random forages [1-500]?: ", 1, 500));
        return request;
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayForages(List<Forage> forages) {
        if (forages == null || forages.isEmpty()) {
            io.println("No forages found.");
            return;
        }

        for (Forage forage : forages) {
            io.printf("%s %s - %s:%s - Value: $%.2f%n",
                    forage.getForager().getFirstName(),
                    forage.getForager().getLastName(),
                    forage.getItem().getName(),
                    forage.getItem().getCategory(),
                    forage.getValue()
            );
        }
    }

    public void displayForagers(List<Forager> foragers) {
        if (foragers == null || foragers.size() == 0) {
            io.println("No foragers found");
            return;
        }

        for (Forager forager : foragers.stream().sorted(Comparator.comparing(Forager::getLastName))
                .limit(25).collect(Collectors.toList())) {
            io.printf("%s %s - %s%n",
                    forager.getFirstName(),
                    forager.getLastName(),
                    forager.getState().getAbbreviation()
            );
        }

        if (foragers.size() > 25) {
            io.println("More than 25 foragers found. Showing first 25. Please refine your search.");
        }
    }

    public void displayItems(List<Item> items) {

        if (items.size() == 0) {
            io.println("No items found");
        }

        for (Item item : items) {
            io.printf("%s: %s, %s, %.2f $/kg%n", item.getId(), item.getName(), item.getCategory(), item.getDollarPerKilogram());
        }
    }

    public void displayReport(List<String> reportLines, LocalDate date, String reportTitle) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        displayHeader(String.format("%s Collected on %s", reportTitle, date.format(dateFormatter)));

        reportLines.forEach(io::println);
    }

    public Result<List<String>> getReportLinesForKgEachItem(Result<Map<Item, Double>> itemsKgCollectedResult) {
        ConsoleFormatReport formatReport = new ConsoleFormatReport();

        return formatReport.reportKilogramsOfEachItemCollected(itemsKgCollectedResult);
    }

    public Result<List<String>> getReportLinesForTotalValueEachCategory(Result<Map<String, BigDecimal>> valueOfCategoriesCollectedResult) {
        ConsoleFormatReport formatReport = new ConsoleFormatReport();

        return formatReport.reportTotalValueOfEachCategoryCollected(valueOfCategoriesCollectedResult);
    }
}
