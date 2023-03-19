package models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ParkingListFragmentViewModel extends ViewModel {
    private LiveData<List<Parking>> parkingList = ParkingMock.instance().getAllParkingLots();

    public LiveData<List<Parking>> getParkingList() {
        return parkingList;
    }
}
