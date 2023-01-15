package models.Interfaces;

import java.util.List;

import models.Parking;

public interface GetParkingLotsByUserIdListener {
    void onComplete(List<Parking> parkingList);
}
