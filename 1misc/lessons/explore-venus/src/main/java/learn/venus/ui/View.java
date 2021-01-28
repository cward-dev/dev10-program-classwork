package learn.venus.ui;

import learn.venus.domain.OrbiterResult;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner console = new Scanner(System.in);

    public MenuOption displayMenuAndSelect() {
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for (int i = 0; i < values.length; i++) {
            System.out.printf("%s. %s%n", i, values[i].getTitle());
        }
        int index = readInt("Select [0-4]: ", 0, 4);
        return values[index];
    }

    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void displayOrbiters(List<Orbiter> orbiters) {
        printHeader("Orbiters:");
        if (orbiters.size() == 0) {
            System.out.println("No orbiters found.");
        } else {
            for (Orbiter o : orbiters) {
                System.out.printf("%s. %s: %s, %s%n",
                        o.getOrbiterId(),
                        o.getName(),
                        o.getType(),
                        o.getSponsor());
            }
        }
    }

    public void displayResult(OrbiterResult result) {
        if (result.isSuccess()) {
            printHeader("Success");
        } else {
            printHeader("Err:");
            for (String err : result.getMessages()) {
                System.out.println(err);
            }
        }
    }

    public Orbiter makeOrbiter() {
        Orbiter orbiter = new Orbiter();
            orbiter.setName(readRequiredString("Name: "));
            orbiter.setType(readOrbiterType());
            orbiter.setSponsor(readString("Sponsor: "));
        return orbiter;
    }

    public Orbiter update(List<Orbiter> orbiters) {
        Orbiter orbiter = findOrbiter(orbiters);
        if (orbiter != null) {
            update(orbiter);
        }
        return orbiter;
    }

    public Orbiter findOrbiter(List<Orbiter> orbiters) {
        displayOrbiters(orbiters);

        if (orbiters.size() == 0) {
            return null;
        }

        int orbiterId = readInt("Orbiter Id: ");
        for (Orbiter o : orbiters) {
            if (o.getOrbiterId() == orbiterId) {
                return o;
            }
        }

        System.out.println("Orbiter Id " + orbiterId + " not found.");
        return null;
    }

    private Orbiter update(Orbiter orbiter) {
        String name = readString("Name (" + orbiter.getName() + "): ");
        if (name.trim().length() > 0) {
            orbiter.setName(name);
        }
        String sponsor = readString("Sponsor (" + orbiter.getSponsor() + "): ");
        if (sponsor.trim().length() > 0) {
            orbiter.setSponsor(sponsor);
        }
        return orbiter;
    }

    public OrbiterType readOrbiterType() {
        System.out.println("Types:");
        OrbiterType[] values = OrbiterType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("%s. %s%n", i, values[i]);
        }
        int index = readInt("Select [0-4]: ", 0, 4);
        return values[index];
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        String result = null;
        do {
            result = readString(prompt).trim();
            if (result.length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.length() == 0);
        return result;
    }

    private int readInt(String prompt) {
        int result = 0;
        boolean isValid = false;
        do {
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number.");
            }
        } while (!isValid);

        return  result;
    }

    private int readInt(String prompt, int min, int max) {
        int result = 0;
        do {
            result = readInt(prompt);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.", min, max);
            }
        } while(result < min || result > max);

        return result;
    }
}
