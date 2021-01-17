package learn.house.ui;

import learn.house.models.State;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            = "[INVALID] Enter a valid date no earlier than %s.";
    private static final String INVALID_STATE
            = "[INVALID] Enter a valid state name or abbreviation.";
    private static final String INVALID_DOLLAR_AMOUNT
            = "[INVALID] Enter a valid dollar amount no less than $0.00.";

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
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

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public LocalDate readLocalDateStart(String prompt) {
        LocalDate today = LocalDate.now();
        LocalDate result;

        while (true) {
            String input = readRequiredString(prompt);
            try {
                result = LocalDate.parse(input, formatter);
                if (!result.isBefore(today)) return result;
                println(String.format(INVALID_DATE, formatter.format(today)));
            } catch (DateTimeParseException ex) {
                println(String.format(INVALID_DATE, formatter.format(today)));
            }
        }
    }

    // Overloaded for editing existing
    public LocalDate readLocalDateStart(String prompt, LocalDate existingDate) {
        LocalDate today = LocalDate.now();
        LocalDate result;

        while (true) {
            String input = readString(prompt);
            if (input == null || input.trim().length() == 0) {
                return existingDate;
            }
            try {
                result = LocalDate.parse(input, formatter);
                if (!result.isBefore(today)) return result;
                println(String.format(INVALID_DATE, formatter.format(today)));
            } catch (DateTimeParseException ex) {
                println(String.format(INVALID_DATE, formatter.format(today)));
            }
        }
    }

    public LocalDate readLocalDateEnd(String prompt, LocalDate startDate) {
        LocalDate result;

        while (true) {
            String input = readRequiredString(prompt);
            try {
                result = LocalDate.parse(input, formatter);
                if (result.isAfter(startDate)) return result;
                println(String.format(INVALID_DATE, formatter.format(startDate.plusDays(1))));
            } catch (DateTimeParseException ex) {
                println(String.format(INVALID_DATE, formatter.format(startDate.plusDays(1))));
            }
        }
    }

    // Overloaded for editing existing
    public LocalDate readLocalDateEnd(String prompt, LocalDate startDate, LocalDate existingDate) {
        LocalDate result;

        while (true) {
            String input = readString(prompt);
            if (input == null || input.trim().length() == 0) {
                if (existingDate.isAfter(startDate)) {
                    return existingDate;
                }
                println(String.format(INVALID_DATE, formatter.format(startDate.plusDays(1))));
                continue;
            }
            try {
                result = LocalDate.parse(input, formatter);
                if (result.isAfter(startDate)) return result;
                println(String.format(INVALID_DATE, formatter.format(startDate.plusDays(1))));
            } catch (DateTimeParseException ex) {
                println(String.format(INVALID_DATE, formatter.format(startDate.plusDays(1))));
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

    // Overloaded for editing existing
    public State readState(String prompt, State existingState) {
        State state;
        while (true) {
            String input = readString(prompt);
            if (input == null || input.trim().length() == 0) {
                return existingState;
            }
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

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) >= 0) {
                    return amount;
                }
                println(INVALID_DOLLAR_AMOUNT);
            } catch (NumberFormatException ex) {
                println(INVALID_DOLLAR_AMOUNT);
            }
        }
    }

    // Overloaded for editing existing
    public BigDecimal readBigDecimal(String prompt, BigDecimal currentValue) {

        while (true) {
            String input = readString(prompt);
            if (input.trim().length() == 0) {
                return currentValue;
            }
            try {
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) >= 0) {
                    return amount;
                }
                println(INVALID_DOLLAR_AMOUNT);
            } catch (NumberFormatException ex) {
                println(INVALID_DOLLAR_AMOUNT);
            }
        }
    }
}
