package lessons;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalLesson {

    public static void main(String[] args) {
        final BigDecimal dollar = BigDecimal.ONE;
        final BigDecimal quarter = new BigDecimal("0.25");
        final BigDecimal dime = new BigDecimal("0.1");
        final BigDecimal nickel = new BigDecimal("0.05");

        BigDecimal change = new BigDecimal("13.93");
        BigDecimal[] result = change.divideAndRemainder(dollar);
        System.out.println("Dollars: " + result[0].setScale(0, RoundingMode.UNNECESSARY));

        result = result[1].divideAndRemainder(quarter);
        System.out.println("Quarters: " + result[0].setScale(0, RoundingMode.UNNECESSARY));

        result = result[1].divideAndRemainder(dime);
        System.out.println("Dimes: " + result[0].setScale(0, RoundingMode.UNNECESSARY));

        result = result[1].divideAndRemainder(nickel);
        System.out.println("Nickels: " + result[0].setScale(0, RoundingMode.UNNECESSARY));

        // multiply by 100 to get whole pennies
        // rounding may be necessary if change has fractions of a penny
        BigDecimal pennies = result[1].multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_EVEN);
        System.out.println("Pennies: " + pennies);
    }
}
