package learn.foraging.ui;

import learn.foraging.models.Category;
import learn.foraging.models.Forager;
import learn.foraging.models.State;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleIO {

    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a valid date no later than %s.";
    private static final String INVALID_STATE
            = "[INVALID] Enter a valid state name or abbreviation.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String readString(String prompt) {
        System.out.println();
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public double readDouble(String prompt, double min, double max) {
        while (true) {
            double result = readDouble(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    // Overloaded for Update Item
    public int readInt(String prompt, int currentValue) {
        while (true) {
            String input = readString(prompt);
            if (input.trim().length() == 0) {
                return currentValue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    // Overloaded for Update Item
    public int readInt(String prompt, int min, int max, int currentValue) {
        while (true) {
            int result = readInt(prompt, currentValue);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public LocalDate readLocalDate(String prompt) {
        LocalDate today = LocalDate.now();

        LocalDate result;

        while (true) {
            String input = readRequiredString(prompt);
            try {
                result = LocalDate.parse(input, formatter);
                if (!result.isAfter(today)) return result;
                println(String.format(INVALID_DATE, today.format(formatter)));
            } catch (DateTimeParseException ex) {
                println(String.format(INVALID_DATE, today.format(formatter)));
            }
        }
    }

    public State readState(String prompt) {
        State state;
        while (true) {
            String input = readRequiredString(prompt);
            try {
                if (input.trim().length() == 2) {
                    state = State.getStateFromAbbreviation(input);
                } else {
                    state = State.getStateFromName(input.toUpperCase());
                }
                if (state != null) return state;
                println(INVALID_STATE);
            } catch (IllegalArgumentException ex) {
                println(INVALID_STATE);
            }
        }
    }

    // Overloaded for Update Forager
    public State readState(String prompt, Forager forager) {
        State state;
        while (true) {
            String input = readString(prompt);
            try {
                if (input.trim().length() == 0) {
                    return forager.getState();
                }
                if (input.trim().length() == 2) {
                    state = State.getStateFromAbbreviation(input);
                } else {
                    state = State.getStateFromName(input.toUpperCase());
                }
                if (state != null) return state;
                println(INVALID_STATE);
            } catch (IllegalArgumentException ex) {
                println(INVALID_STATE);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    // Overloaded for Update Item
    public BigDecimal readBigDecimal(String prompt, BigDecimal currentValue) {
        while (true) {
            String input = readString(prompt);
            if (input.trim().length() == 0) {
                return currentValue;
            }
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            BigDecimal result = readBigDecimal(prompt);
            if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    // Overloaded for Update Item
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max, BigDecimal currentValue) {
        while (true) {
            BigDecimal result = readBigDecimal(prompt, currentValue);
            if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }
}
