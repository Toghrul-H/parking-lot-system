package parking;

import vehicle.Size;
import vehicle.Car;
import parking.facility.Gate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ParkingLotTest {

    @Test
    public void testConstructorWithInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(1, 0));
    }

    @Test
    public void testTextualRepresentation() {
        ParkingLot lot = new ParkingLot(5, 5);
        Gate gate = new Gate(lot);

        Car s1 = new Car("S1", Size.SMALL, 0);
        Car l1 = new Car("L1", Size.LARGE, 1);
        gate.registerCar(s1);
        gate.registerCar(l1);

        StringBuilder expected = new StringBuilder();
        expected.append("S X X X X\n")
                .append("L L X X X\n")
                .append("X X X X X\n")
                .append("X X X X X\n")
                .append("X X X X X");

        assertEquals(expected.toString(), lot.toString());
    }
}