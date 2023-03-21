package models;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wepark.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import models.Interfaces.OnActionDoneListener;
import room.AppLocalDb;
import room.AppLocalDbRepository;
import services.LoginService;

public class ParkingMock {
    public static final ParkingMock _instance = new ParkingMock();

    public static ParkingMock instance() {
        return _instance;
    }

    private LiveData<List<Parking>> parkingList;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb;
    private FirebaseModel firebaseModel = new FirebaseModel();

    final public MutableLiveData<LoadingState> EventStudentsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING_PARKING);

    private ParkingMock() {

    }

    public LiveData<List<Parking>> getAllParkingLots() {
        if (parkingList == null) {
            parkingList = localDb.parkingDao().getAll();
            refreshAllParkingLots();
        }

        return parkingList;
    }

    public void refreshAllParkingLots() {
        // get local last update
        Long localLastUpdate = Parking.getLocalLastUpdate();

        firebaseModel.getUser(LoginService.instance().getLoginService().getUserId(), user -> {
            // get all updated records from firebase since local update
            if (user.getFavorites().size() != 0) {
                EventStudentsListLoadingState.setValue(LoadingState.LOADING_PARKING);
                firebaseModel.getFavoriteParkingLotsSince(user.getFavorites(), localLastUpdate, list -> {
                    executor.execute(() -> {
                        Log.d("TAG", " firebase return : " + list.size());
                        Long time = localLastUpdate;

                        LiveData<List<Parking>> av = getAllParkingLots();

                        for (Parking pr : av.getValue()) {
                            if (!user.getFavorites().contains(pr.getCity())) {
                                localDb.parkingDao().delete(pr);
                            }
                        }

                        for (Parking pr : list) {
                            if (pr.getMark().equals("D")) {
                                localDb.parkingDao().delete(pr);
                            } else {
                                localDb.parkingDao().insertAll(pr);
                            }

                            if (time < pr.getLastUpdated()) {
                                time = pr.getLastUpdated();
                            }
                        }
                        //update local last update
                        Parking.setLocalLastUpdate(time);
                        EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING_PARKING);
                    });
                });
            }else{
                executor.execute(() -> {
//                    Long time = localLastUpdate;

                    localDb.parkingDao().deleteAll();

                    //update local last update
//                    Parking.setLocalLastUpdate(time);
                    EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING_PARKING);
                });
            }
        });
    }

    public void addParkingLot(Parking newParking, OnActionDoneListener<Void> listener) {
        firebaseModel.addParkingLot(newParking, (Void) -> {
            refreshAllParkingLots();
            listener.onComplete(null);
        });
    }

    public void deleteParkingLot(Parking parking, OnActionDoneListener listener) {
        firebaseModel.removeParkingLot(parking.getId(), unused -> {
            executor.execute(() -> {
            localDb.parkingDao().delete(parking);
            mainHandler.post(() -> {
                listener.onComplete(null);
            });
        });
        });
    }

    public void updateParkingLot(Parking parking, OnActionDoneListener listener) {
        firebaseModel.addParkingLot(parking, unused -> {
            executor.execute(() -> {
            localDb.parkingDao().getParking(parking.getId());
            mainHandler.post(() -> {
                listener.onComplete(null);
            });
        });
        });
    }

    public void uploadParkingLotImage(Bitmap bitmap, String parkingId, OnActionDoneListener<String> listener) {
        firebaseModel.uploadImage(bitmap, parkingId, listener);
    }

    public void getParkingLotsByUserId(String userId, OnActionDoneListener<List> listener) {
        firebaseModel.getParkingLotOfUser(userId, listener);
    }

    public void getParkingLot(String parkingId, OnActionDoneListener<Parking> listener) {
        firebaseModel.getParkingLot(parkingId, listener);
    }


}
