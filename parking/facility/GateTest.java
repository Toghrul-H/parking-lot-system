package parking.facility;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import parking.ParkingLot;
import vehicle.Car;
import vehicle.Size;

import static org.junit.jupiter.api.Assertions.*;

public class GateTest {
    private ParkingLot lot;
    private Gate gate;

    @BeforeEach
    public void setup() {
        lot = new ParkingLot(3, 5);
        gate = new Gate(lot);
    }

    @Test
    public void testFindAnyAvailableSpaceForCar() {
        Car car = new Car("CAR1", Size.SMALL, 0);
        assertNotNull(gate.findAnyAvailableSpaceForCar(car));
    }

    @ParameterizedTest
    @CsvSource({"P1, SMALL, 1", "P2, LARGE, 1"})
    public void testFindPreferredAvailableSpaceForCar(String plate, Size size, int preferredFloor) {
        Car car = new Car(plate, size, preferredFloor);
        assertNotNull(gate.findPreferredAvailableSpaceForCar(car));
    }

    @ParameterizedTest
    @CsvSource({"P1, SMALL, 0", "P2, LARGE, 2"})
    public void testRegisterCar(String plate, Size size, int preferredFloor) {
        Car car = new Car(plate, size, preferredFloor);
        assertTrue(gate.registerCar(car));
    }

    @ParameterizedTest
    @CsvSource({"P1, SMALL, 0", "P2, LARGE, 2"})
    public void testDeRegisterCar(String plate, Size size, int preferredFloor) {
        Car car = new Car(plate, size, preferredFloor);
        assertTrue(gate.registerCar(car));
        gate.deRegisterCar(car.getTicketId());
        assertTrue(gate.registerCar(car));
    }
}