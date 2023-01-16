package models.Interfaces;

import java.util.List;

import models.Parking;

public interface GetAllParkingListener {
    void onComplete(List<Parking> data);
}
