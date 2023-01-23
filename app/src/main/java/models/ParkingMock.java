package models;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import models.Interfaces.OnActionDoneListener;
import room.AppLocalDb;
import room.AppLocalDbRepository;

public class ParkingMock {
    public static final ParkingMock _instance = new ParkingMock();

    public static ParkingMock instance() {
        return _instance;
    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb;
    private FirebaseModel firebaseModel = new FirebaseModel();

    private ParkingMock() {

    }

    public void getAllParkingLots(OnActionDoneListener<List<Parking>> listener) {
////        executor.execute(() -> {
////            List<Parking> data = localDb.parkingDao().getAll();
////            mainHandler.post(() -> {
//                listener.onComplete(data);
//            });
//        });

        // get local last update
        Long localLastUpdate = Parking.getLocalLastUpdate();
        // get all updated records from firebase since local update
        firebaseModel.getAllParkingLotsSince(localLastUpdate,list->{
            executor.execute(() -> {
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Parking pr:list){
                    //insert new records into ROOM
                    localDb.parkingDao().insertAll(pr);
                    if(time < pr.getLastUpdated()){
                        time = pr.getLastUpdated();
                    }
                }
                //update local last update
                Parking.setLocalLastUpdate(time);
                // return complete list from ROOM
                List<Parking> complete = localDb.parkingDao().getAll();
                mainHandler.post(() -> {
                    listener.onComplete(complete);
                });
            });
        });
    }

    public void addParkingLot(Parking newParking, OnActionDoneListener<Void> listener) {
        firebaseModel.addParkingLot(newParking, listener);
//        executor.execute(() -> {
//            localDb.parkingDao().insertAll(newParking);
//            mainHandler.post(() -> {
//                listener.onComplete();
//            });
//        });
    }

    public void uploadParkingLotImage(Bitmap bitmap, String parkingId, OnActionDoneListener<String> listener) {
        firebaseModel.uploadImage(bitmap, parkingId, listener);
    }

    public void getParkingLotsByUserId(String userId, OnActionDoneListener<List> listener) {
        firebaseModel.getParkingLotOfUser(userId, listener);
//        executor.execute(() -> {
//            List<Parking> data = localDb.parkingDao().getAll().stream().filter((parking) -> parking.getUserId().equals(userId)).collect(Collectors.toList());
//            mainHandler.post(() -> {
//                listener.onComplete(data);
//            });
//        });
    }

    public void deleteParkingLot(Parking parking, OnActionDoneListener listener) {
        executor.execute(() -> {
            localDb.parkingDao().delete(parking);
            mainHandler.post(() -> {
                listener.onComplete(null);
            });
        });
    }

}
