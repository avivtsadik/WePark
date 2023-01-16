package models;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


import models.Interfaces.AddParkingListener;
import models.Interfaces.DeleteParkingListener;
import models.Interfaces.GetAllParkingListener;
import models.Interfaces.GetParkingLotsByUserIdListener;
import room.AppLocalDb;
import room.AppLocalDbRepository;

public class ParkingMock {
    public static final ParkingMock _instance = new ParkingMock();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb;

    public static ParkingMock instance() {
        return _instance;
    }

    private ParkingMock() {

    }

    public void getAllParkingLots(GetAllParkingListener listener) {
        executor.execute(() -> {
            List<Parking> data = localDb.parkingDao().getAll();
            mainHandler.post(() -> {
                listener.onComplete(data);
            });
        });
    }

    public void addParkingLot(Parking newParking, AddParkingListener listener) {
        executor.execute(() -> {
            localDb.parkingDao().insertAll(newParking);
            mainHandler.post(() -> {
                listener.onComplete();
            });
        });
    }

    public void getParkingLotsByUserId(String userId, GetParkingLotsByUserIdListener listener) {
        executor.execute(() -> {
            List<Parking> data = localDb.parkingDao().getAll().stream().filter((parking) -> parking.getUserId().equals(userId)).collect(Collectors.toList());
            mainHandler.post(() -> {
                listener.onComplete(data);
            });
        });
    }

    public void deleteParkingLot(Parking parking, DeleteParkingListener listener) {
        executor.execute(() -> {
            localDb.parkingDao().delete(parking);
            mainHandler.post(() -> {
                listener.onComplete();
            });
        });
    }

}
