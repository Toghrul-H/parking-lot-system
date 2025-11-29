package parking.facility;

import parking.ParkingLot;
import vehicle.Car;
import vehicle.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Gate {
    private final List<Car> cars;
    private final ParkingLot parkingLot;

    public Gate(ParkingLot parkingLot) {
        this.cars = new ArrayList<>();
        this.parkingLot = parkingLot;
    }

    private Space findAvailableSpaceOnFloor(int floor, Car c) {
        Space[] spaces = parkingLot.getFloorPlan()[floor];
        if (c.getSpotOccupation() == Size.SMALL) {
            for (Space s : spaces) {
                if (!s.isTaken()) {
                    return s;
                }
            }
        } else { // LARGE
            // Need two adjacent free spaces: [i] and [i+1]
            for (int i = 0; i < spaces.length - 1; i++) {
                if (!spaces[i].isTaken() && !spaces[i + 1].isTaken()) {
                    // We return the RIGHT slot (i+1). Left slot is i.
                    return spaces[i + 1];
                }
            }
        }
        return null;
    }

    public Space findAnyAvailableSpaceForCar(Car c) {
        for (int i = 0; i < parkingLot.getFloorPlan().length; i++) {
            Space s = findAvailableSpaceOnFloor(i, c);
            if (s != null) return s;
        }
        return null;
    }

    public Space findPreferredAvailableSpaceForCar(Car c) {
        int pref = c.getPreferredFloor();
        int floors = parkingLot.getFloorPlan().length;
        for (int offset = 0; offset < floors; offset++) {
            int left = pref - offset;
            int right = pref + offset;
            if (left >= 0) {
                Space s = findAvailableSpaceOnFloor(left, c);
                if (s != null) return s;
            }
            if (right < floors && right != left) {
                Space s = findAvailableSpaceOnFloor(right, c);
                if (s != null) return s;
            }
        }
        return null;
    }

    public boolean registerCar(Car c) {
        Space s = findPreferredAvailableSpaceForCar(c);
        if (s == null) return false;

        // Assign unique ticket ID
        c.setTicketId(UUID.randomUUID().toString());

        Space[] floor = parkingLot.getFloorPlan()[s.getFloorNumber()];
        int idx = s.getSpaceNumber();
        if (c.getSpotOccupation() == Size.SMALL) {
            floor[idx].addOccupyingCar(c);
        } else { // LARGE
            // We stored the right slot as s. The left is idx-1.
            if (idx - 1 < 0) return false; // safety guard (shouldn't happen due to search logic)
            floor[idx].addOccupyingCar(c);
            floor[idx - 1].addOccupyingCar(c);
        }
        cars.add(c);
        return true;
    }

    public void registerCars(Car... carsArr) {
        for (Car c : carsArr) {
            if (!registerCar(c)) {
                System.err.println("Cannot register car: " + c.getLicensePlate());
            }
        }
    }

    public void deRegisterCar(String ticketId) {
        if (ticketId == null) return;
        for (Car c : new ArrayList<>(cars)) {
            if (ticketId.equals(c.getTicketId())) {
                for (Space[] floor : parkingLot.getFloorPlan()) {
                    for (Space s : floor) {
                        if (s.getOccupyingCar() == c) {
                            s.removeOccupyingCar();
                        }
                    }
                }
                cars.remove(c);
                return;
            }
        }
    }
}