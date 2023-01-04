package models;

import java.util.List;
import java.util.Vector;

public class ParkingMock {
    public static final ParkingMock _instance = new ParkingMock();

    public static ParkingMock instance() {
        return _instance;
    }

    private final Vector<Parking> parkingLots = new Vector<>();

    private ParkingMock() {
        for (int i = 0; i < 20; i++) {
            this.addParkingLot(new Parking(i, "city " + i, "street " + i, ParkingSize.BIG));
        }
    }

    private void addParkingLot(Parking parking) {
        this.parkingLots.add(parking);
    }

    public List<Parking> getParkingLots() {
        return this.parkingLots;
    }
}
