package models;

import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

public class ParkingsListFragmentViewModel extends ViewModel {
    private List<Parking> parkingList = new LinkedList<>();

    public List<Parking> getParkingList(){
        return parkingList;
    }
    public void setParkingList(List<Parking> list){
        parkingList= list;
    }
}
