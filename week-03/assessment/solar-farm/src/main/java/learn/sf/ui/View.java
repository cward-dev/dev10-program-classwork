package learn.sf.ui;

import learn.sf.domain.PanelResult;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import learn.sf.ui.MenuModel.DisplayMenuOption;
import learn.sf.ui.MenuModel.MenuOption;

import java.time.Year;
import java.time.ZoneId;
import java.util.*;

public class View {

    Scanner console = new Scanner(System.in);

    public MenuOption printMenuGetSelection() {
        printHeader("Main Menu");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getMessage());
        }

        String message = String.format("Select [%s-%s]: ", 1, options.length);
        int selection = readInt(message,1, options.length);
        return options[selection - 1];
    }

    public DisplayMenuOption printDisplayMenuGetSelection() {
        printHeader("Display Menu");

        DisplayMenuOption[] options = DisplayMenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getMessage());
        }

        String message = String.format("Select [%s-%s]: ", 1, options.length);
        int selection = readInt(message,1, options.length);
        return options[selection - 1];
    }

    public void printBySection(String section, List<Panel> panels) {
        if (panels.size() == 0) {
            System.out.println();
            System.out.printf("No panels exist in section %s.%n", section);
            return;
        }

        printHeader(String.format("Panels in %s", section), "*");
        printPanels(panels);
    }

    public void printByMaterial(PanelMaterial material, List<Panel> panels) {
        if (panels.size() == 0) {
            System.out.println();
            System.out.printf("No panels of material %s exist.%n", material);
            return;
        }

        printHeader(String.format("%s (%s) Panels", material.getName(), material.getAbbreviation()), "*");
        printPanels(panels);
    }

    public void printPanel(Panel panel) {
        if (panel == null) {
            System.out.println();
            System.out.println("No panel exists with that Panel Id.");
            return;
        }

        ArrayList<Panel> panels = new ArrayList<>();
        panels.add(panel);

        printPanels(panels);
    }

    public void printResult(PanelResult result, String actionVerb) {
        if (result.isSuccess()) {
            if (result.getPayload() != null) {
                System.out.println();
                System.out.printf("Panel Id %s %s.%n",
                        result.getPayload().getPanelId(),
                        actionVerb);
            } else {
                System.out.println();
                System.out.println("Success!");
            }
        } else {
            printHeader("Errors");
            for (String message : result.getMessages()) {
                System.out.printf("- %s%n", message);
            }
        }
    }

    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    // Overloaded
    public void printHeader(String message, String symbol) {
        String lineSymbol = "";
        lineSymbol += symbol.charAt(0);

        System.out.println();
        System.out.println(message);
        System.out.println(lineSymbol.repeat(message.length()));
    }

    public void printPanels(List<Panel> panels) {
        if (panels == null || panels.size() == 0) {
            System.out.println("No panels exist by that criteria.");
            return;
        }

        printHeader(" Id  Section  Row  Col  Year  Material  Tracking", "-");
        for (Panel panel : panels) {
            System.out.printf("%3s  %7s  %3s  %3s  %4s  %8s  %8s%n",
                    panel.getPanelId(),
                    panel.getSection().substring(0,Math.min(panel.getSection().length(), 7)),
                    panel.getRow(),
                    panel.getColumn(),
                    panel.getYearInstalled(),
                    panel.getMaterial().getAbbreviation().
                            substring(0,Math.min(panel.getMaterial().getAbbreviation().length(), 7)),
                    panel.isTracking() ? "Yes" : "No");
        }
    }

    public Panel getNewPanelToAdd(List<String> sections) {
        int currentYear = Year.now(ZoneId.of("America/Chicago")).getValue();
        Panel panel = new Panel();

        panel.setSection(getSectionFromListOrManualInput(sections, panel, false));
        panel.setRow(readInt("Row [1-250]: ", 1, 250));
        panel.setColumn(readInt("Column [1-250]: ",1,250));
        panel.setYearInstalled(readInt("Year Installed: ", 1954, currentYear));
        panel.setMaterial(getPanelMaterial());
        panel.setTracking(readBoolean("Solar Tracking [y/n]: "));

        return panel;
    }

    public Panel getUpdatedPanel(Panel panel, List<String> sections) { // TODO Fix this in line with explore-venus project
        int currentYear = Year.now(ZoneId.of("America/Chicago")).getValue();
        Panel updatedPanel = panel;

        String section = getSectionFromListOrManualInput(sections, panel, true);
        int row = readUpdateInt(String.format("Row [%s]: ", panel.getRow()), 1, 250);
        int column = readUpdateInt(String.format("Column [%s]: ", panel.getColumn()), 1, 250);
        int yearInstalled = readUpdateInt(String.format("Year Installed [%s]: ", panel.getYearInstalled()), 1954, currentYear);
        PanelMaterial material = getUpdatePanelMaterial(panel.getMaterial());
        boolean isTracking = readUpdateBoolean(String.format("Solar Tracking [%s]: ", panel.isTracking() ? "y" : "n"), panel.isTracking());

        boolean updateMade = false;
        if (!section.equals(panel.getSection())) { updatedPanel.setSection(section); updateMade = true; }
        if (row != 0) { updatedPanel.setRow(row); updateMade = true; }
        if (column != 0) { updatedPanel.setColumn(column); updateMade = true; }
        if (yearInstalled != 0) { updatedPanel.setYearInstalled(yearInstalled); updateMade = true; }
        if (material != null) { updatedPanel.setMaterial(material); updateMade = true; }
        if (isTracking != panel.isTracking()) { updatedPanel.setTracking(isTracking); updateMade = true; }

        if (!updateMade) {
            System.out.println();
            System.out.println("No updates were made.");
            return null;
        }

        return updatedPanel;
    }

    private String getSectionFromListOrManualInput(List<String> sections, Panel panel, boolean canBeEmpty) {
        String section;
        boolean chooseFromExistingSections = readBoolean("Would you like to choose section from list? [y/n]: ");
        if (chooseFromExistingSections) {
            return getSection(sections);
        }

        if (canBeEmpty && panel != null) {
            section = readString(String.format("Section [%s]: ", panel.getSection()));
            if (section.trim().length() == 0) {
                return panel.getSection();
            }
        } else {
            section = readRequiredString("Section: ");
        }

        return section;
    }

    public boolean confirmDeletePanel() {
        boolean delete = readBoolean("Are you sure you want to delete this panel? [y/n]: ");
        if (delete) {
            return true;
        }
        System.out.println();
        System.out.println("Panel not deleted.");
        return false;
    }

    public int getPanelId() {
        String message = "Enter Panel Id: ";
        return readInt(message);
    }

    public String updateSection(String section, List<String> sections) {
        String newSection = getSectionFromListOrManualInput(sections, null, false);

        System.out.println();
        if (section.equals(newSection)) {
            System.out.printf("No change was made to %s.%n", section);
        } else {
            System.out.printf("Moving panels from %s to %s.%n", section, newSection);
        }

        return newSection;
    }

    public String getSection(List<String> sections) {
        // Get digit length to align menu options
        int digitLength = 1;
        if (sections.size() > 9) {
            digitLength++;
        }

        printHeader("Section", "-");

        for (int i = 0; i < sections.size(); i++) {
            System.out.printf("%" + digitLength + "s. %s%n", i + 1, sections.get(i));
        }

        String message = String.format("Select [%s-%s]: ", 1, sections.size());
        int selection = readInt(message, 1, sections.size());

        return sections.get(selection - 1);
    }

    public int getRow() {
        System.out.println();
        return readInt("Row [1-250]: ", 1, 250);
    }

    public int getColumn() {
        System.out.println();
        return readInt("Column [1-250]: ", 1, 250);
    }

    public PanelMaterial getPanelMaterial() {
        printHeader("Panel Material", "-");

        PanelMaterial[] options = PanelMaterial.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s (%s)%n", i + 1, options[i].getName(), options[i].getAbbreviation());
        }

        String message = String.format("Select [%s-%s]: ", 1, options.length);
        int selection = readInt(message, 1, options.length);

        return options[selection - 1];
    }

    // Overloaded - update method
    public PanelMaterial getUpdatePanelMaterial(PanelMaterial currentMaterial) {
        printHeader(String.format("Panel Material [%s]", currentMaterial.getName()), "-");

        PanelMaterial[] options = PanelMaterial.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s (%s)%n", i + 1, options[i].getName(), options[i].getAbbreviation());
        }

        String message = String.format("Select [%s-%s]: ", 1, options.length);
        int selection = readUpdateInt(message, 1, options.length);

        if (selection == 0) {
            return null;
        }

        return options[selection - 1];
    }

    private boolean readBoolean(String message) {
        String input;
        boolean result = false;
        boolean isValid = false;

        do {
            input = readRequiredString(message);
            if (input.equalsIgnoreCase("y")) {
                result = true;
                isValid = true;
            } else if (input.equalsIgnoreCase("n")) {
                isValid = true;
            } else {
                System.out.println("Value must be \"y\" or \"n\".");
            }
        } while (!isValid);

        return result;
    }

    // Overloaded - update method
    private boolean readUpdateBoolean(String message, boolean currentTracking) {
        String input;
        boolean result = false;
        boolean isValid = false;

        do {
            input = readString(message);
            if (input.trim().length() == 0) {
                return currentTracking;
            }

            if (input.equalsIgnoreCase("y")) {
                result = true;
                isValid = true;
            } else if (input.equalsIgnoreCase("n")) {
                isValid = true;
            } else {
                System.out.println("Value must be \"y\" or \"n\".");
            }
        } while (!isValid);

        return result;
    }

    private String readString(String message) {
        System.out.println();
        System.out.print(message);
        return console.nextLine();
    }

    private String readRequiredString(String message) {
        String input;

        do {
            input = readString(message);
            if (input.trim().length() == 0) {
                System.out.println("Value is required.");
            }
        } while (input.trim().length() == 0);

        return input;
    }

    private int readInt(String message) {
        int result = 0;
        boolean isValid = false;

        do {
            try {
                result = Integer.parseInt(readRequiredString(message));
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number.");
            }
        } while (!isValid);

        return result;
    }

    // Overloaded - min and max
    private int readInt(String message, int min, int max) {
        int result = 0;

        do {
            result = readInt(message);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);

        return result;
    }

    // Overloaded - update method
    private int readUpdateInt(String message, int min, int max) {
        String input;
        int result = 0;
        boolean isValid = false;

        do {
            input = readString(message);
            if (input.trim().length() == 0) {
                return 0;
            }
            try {
                result = Integer.parseInt(input);
                if (result < min || result > max) {
                    System.out.printf("Value must be between %s and %s.%n", min, max);
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number.");
            }
        } while (!isValid);

        return result;
    }

}
