package learn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubmarineTest {

    Submarine submarine = new Submarine(100.0);
    Submarine submarine2 = new Submarine(80.0);
    Submarine submarine3 = new Submarine(2.0);

    @Test
    void shouldHaveCorrectDepthAfter3Dives() {
        submarine.dive();
        submarine.dive();
        submarine.dive();
        assertEquals(9.0, submarine.getDepthInMeters(), 0.001);
    }

    @Test
    void shouldHaveCorrectPressureAfter3Dives() {
        submarine.dive();
        submarine.dive();
        submarine.dive();
        // 1.0 at sea level plus 1.0 * 0.9
        assertEquals(1.9, submarine.getPressureInAtmospheres(), 0.001);
    }

    // 1. Create one or more tests to confirm `dive` is working properly.
    // 2. Create a test to assert the submarine can't go deeper than the max depth.
    // (Be sure to use more than one max depth.)
    // 3. Create one or more tests to confirm `surface` is working properly.
    // 4. Create a test to assert the submarine can't go above sea level.
    // (Depth can never be negative.)
    // 5. Create one or more tests to confirm `getPressureInAtmospheres` is working properly.

    @Test
    void shouldHaveCorrectPressureAfterNumOfDives() {
        assertEquals(1,submarine.getPressureInAtmospheres(), 0.001);
        submarine.dive();
        assertEquals(1.3,submarine.getPressureInAtmospheres(), 0.001);
        submarine.dive();
        assertEquals(1.6,submarine.getPressureInAtmospheres(), 0.001);
        submarine.dive();
        assertEquals(1.9, submarine.getPressureInAtmospheres(), 0.001);
    }

    @Test
    void shouldStopDivingIfAtMaxDepth() {
        submarine.setDepthInMeters(100.0);
        submarine.dive();
        submarine.dive();
        assertEquals(100.0, submarine.getDepthInMeters());

        submarine2.setDepthInMeters(80.0);
        submarine2.dive();
        submarine2.dive();
        assertEquals(80.0, submarine2.getDepthInMeters());

        submarine3.setDepthInMeters(2.0);
        submarine3.dive();
        submarine3.dive();
        assertEquals(2.0, submarine3.getDepthInMeters());
    }

    @Test
    void shouldStopAtMaxDepthWhenApproaching() {
        submarine.setDepthInMeters(98.0);
        submarine.dive();
        assertEquals(100.0, submarine.getDepthInMeters());
    }

    @Test
    void shouldHaveCorrectDepthAfter3Surfaces() {
        submarine.setDepthInMeters(30.0);
        submarine.surface();
        submarine.surface();
        submarine.surface();
        assertEquals(15.0, submarine.getDepthInMeters(), 0.001);
    }

    @Test
    void shouldHaveCorrectPressureAfter3Surfaces() {
        submarine.setDepthInMeters(30.0);
        submarine.surface();
        submarine.surface();
        submarine.surface();
        assertEquals(2.5, submarine.getPressureInAtmospheres(), 0.001);
    }

    @Test
    void shouldHaveCorrectPressureAfterNumOfSurfaces() {
        submarine.setDepthInMeters(100.0);
        assertEquals(11.0,submarine.getPressureInAtmospheres(), 0.001);
        submarine.surface();
        assertEquals(10.5,submarine.getPressureInAtmospheres(), 0.001);
        submarine.surface();
        assertEquals(10.0,submarine.getPressureInAtmospheres(), 0.001);
        submarine.surface();
        assertEquals(9.5, submarine.getPressureInAtmospheres(), 0.001);
    }

    @Test
    void shouldStopSurfacingIfAtSeaLevel() {
        submarine.setDepthInMeters(0.0);
        submarine.surface();
        submarine.surface();
        assertEquals(0.0, submarine.getDepthInMeters());
    }

    @Test
    void shouldStopAtSeaLevelWhenApproaching() {
        submarine.setDepthInMeters(2.0);
        submarine.surface();
        assertEquals(0.0, submarine.getDepthInMeters());
    }

    @Test
    void shouldCorrectPressureAtSeaLevel() {
        submarine.setDepthInMeters(0.0);
        assertEquals(1.0, submarine.getPressureInAtmospheres());
    }

}