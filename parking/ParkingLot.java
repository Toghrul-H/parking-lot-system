package parking;

import parking.facility.Space;
import vehicle.Size;

public class ParkingLot {
    private final Space[][] floorPlan;

    public ParkingLot(int floorNumber, int spaceNumber) {
        if (floorNumber < 1 || spaceNumber < 1) {
            throw new IllegalArgumentException("floorNumber and spaceNumber must be >= 1");
        }
        floorPlan = new Space[floorNumber][spaceNumber];
        for (int i = 0; i < floorNumber; i++) {
            for (int j = 0; j < spaceNumber; j++) {
                floorPlan[i][j] = new Space(i, j);
            }
        }
    }

    public Space[][] getFloorPlan() {
        return floorPlan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < floorPlan.length; i++) {
            for (int j = 0; j < floorPlan[i].length; j++) {
                if (j > 0) sb.append(' ');
                Space spot = floorPlan[i][j];
                if (!spot.isTaken()) {
                    sb.append('X');
                } else if (spot.getOccupyingCarSize() == Size.SMALL) {
                    sb.append('S');
                } else if (spot.getOccupyingCarSize() == Size.LARGE) {
                    sb.append('L');
                }
            }
            if (i < floorPlan.length - 1) sb.append('\n');
        }
        return sb.toString();
    }
}