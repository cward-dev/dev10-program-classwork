import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise04Test {

    @Test
    void calculateTotalCost() {
        Exercise04 instance = new Exercise04();

        // doubles are notoriously hard to test because they use floating-point rounding.
        // The third argument in `assertEquals` is a delta. It represents the margin of error for equality.
        // As long as the expected and actual differ by less than the delta, the test passes.
        assertEquals(0, instance.calculateTotalCost(1.27, -7), 0.001);
        assertEquals(0, instance.calculateTotalCost(1.27, 0), 0.001);
        assertEquals(8.89, instance.calculateTotalCost(1.27, 7), 0.001);
        assertEquals(20.51, instance.calculateTotalCost(1.27, 17), 0.001);
        assertEquals(30.861, instance.calculateTotalCost(1.27, 27), 0.001);
        assertEquals(72.327, instance.calculateTotalCost(1.27, 67), 0.001);
        assertEquals(99.06, instance.calculateTotalCost(1.27, 100), 0.001);
    }
}