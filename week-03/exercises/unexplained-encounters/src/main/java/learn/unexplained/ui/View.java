package learn.unexplained.ui;

import learn.unexplained.domain.EncounterResult;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import java.util.List;
import java.util.Scanner;

public class View {

    private Scanner console = new Scanner(System.in);

    public MenuOption displayMenuGetOption() {
        printHeader("Main Menu");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getMessage());
        }

        String msg = String.format("Select [%s-%s]: ", 1, options.length);
        int value = readInt(msg, 1, options.length);
        return options[value - 1];
    }

    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void printAllEncounters(List<Encounter> encounters) {
        printHeader(MenuOption.DISPLAY_ALL.getMessage());
        printEncounters(encounters);
    }

    public void printEncountersOfType(List<Encounter> encounters) {
        printHeader(MenuOption.DISPLAY_BY_TYPE.getMessage());
        printEncounters(encounters);
    }

    private void printEncounters(List<Encounter> encounters) {
        if (encounters == null || encounters.size() == 0) {
            System.out.println();
            System.out.println("No encounters found.");
        } else {
            for (Encounter e : encounters) {
                printEncounter(e);
            }
        }
    }

    public void printEncounter(Encounter encounter) {
        if (encounter != null) {
            System.out.printf("%s. Type: %s, Occurrences: %s, When: %s, Desc: %s%n",
                    encounter.getEncounterId(),
                    encounter.getType(),
                    encounter.getOccurrences(),
                    encounter.getWhen(),
                    encounter.getDescription());
        } else {
            System.out.println("No encounter found.");
        }
    }

    public void printResult(EncounterResult result) {
        if (result.isSuccess()) {
            if (result.getPayload() != null) {
                System.out.printf("Encounter Id %s added.%n", result.getPayload().getEncounterId());
            } else {
                System.out.println("Success!");
            }
        } else {
            printHeader("Errors");
            for (String msg : result.getMessages()) {
                System.out.printf("- %s%n", msg);
            }
        }
    }

    public int getEncounterId() {
        System.out.println();
        return readInt("Encounter Id: ");
    }

    public EncounterType getEncounterType() {
        System.out.println();
        System.out.println("Encounter Types:");
        System.out.println("1. UFO");
        System.out.println("2. Creature");
        System.out.println("3. Voice");
        System.out.println("4. Sound");
        System.out.println("5. Vision");
        int selection = readInt("Select [1-5]: ", 1, 5);
        switch (selection) {
            case 1:
                return EncounterType.UFO;
            case 2:
                return EncounterType.CREATURE;
            case 3:
                return EncounterType.VOICE;
            case 4:
                return EncounterType.SOUND;
            case 5:
                return EncounterType.VISION;
        }
        return null;
    }

    public Encounter makeEncounter() {
        printHeader(MenuOption.ADD.getMessage());
        Encounter encounter = new Encounter();
        encounter.setType(readType());
        encounter.setOccurrences(readInt("Number of occurrences: "));
        encounter.setWhen(readRequiredString("When: "));
        encounter.setDescription(readRequiredString("Description: "));
        return encounter;
    }

    public int getEncounterIdToDelete() {
        printHeader(MenuOption.DELETE.getMessage());
        int encounterId = getEncounterId();

        return encounterId;
    }

    public boolean confirmDeleteEncounter() {
        boolean delete = readBoolean("Are you sure you want to delete this item? [y/n]: ");
        if (delete) {
            return true;
        }
        System.out.println("Item not deleted.");
        return false;
    }

    public Encounter getUpdatedEncounter(Encounter encounter) {
        printHeader(MenuOption.UPDATE.getMessage());
        Encounter updatedEncounter = encounter;

        EncounterType type = readType();
        int numOfOccurrences = readInt("Number of occurrences: ");
        String when = readString("When: ");
        String desc = readString("Description: ");

        if (type != null) { updatedEncounter.setType(type); };
        if (numOfOccurrences != 0) { updatedEncounter.setOccurrences(numOfOccurrences); }
        if (!when.isBlank()) { updatedEncounter.setWhen(when); }
        if (!desc.isBlank()) { updatedEncounter.setDescription(desc); }

        return updatedEncounter;
    }

    private boolean readBoolean(String message) {
        String input = null;
        boolean result = false;
        boolean isValid = false;
        do {
            try {
                input = readRequiredString(message);
                if (input.equalsIgnoreCase("y")) {
                    result = true;
                    isValid = true;
                } else if (input.equalsIgnoreCase("n")) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid entry.%n", input);
            }
        } while (!isValid);

        return result;
    }

    private String readString(String message) {
        System.out.print(message);
        return console.nextLine();
    }

    private String readRequiredString(String message) {
        String result;
        do {
            result = readString(message);
            if (result.trim().length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.trim().length() == 0);
        return result;
    }

    private int readInt(String message) {
        String input = null;
        int result = 0;
        boolean isValid = false;
        do {
            try {
                input = readRequiredString(message);
                result = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isValid);

        return result;
    }

    private int readInt(String message, int min, int max) {
        int result;
        do {
            result = readInt(message);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);
        return result;
    }

    private EncounterType readType() {
        int index = 1;
        for (EncounterType type : EncounterType.values()) {
            System.out.printf("%s. %s%n", index++, type);
        }
        index--;
        String msg = String.format("Select Encounter Type [1-%s]: ", index);
        return EncounterType.values()[readInt(msg, 1, index) - 1];
    }
}
