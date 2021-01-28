package learn.methods;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MethodsTest {

    Methods methods = new Methods();

    @Test
    void shouldReturnCorrectSalary() {
        LocalDate startDate = LocalDate.of(2020,10,1);
        BigDecimal salary = new BigDecimal("100000.00");

        assertEquals(new BigDecimal("25000").doubleValue(), methods.calculateGrossSalary(startDate, salary).doubleValue(), .05);
    }

}