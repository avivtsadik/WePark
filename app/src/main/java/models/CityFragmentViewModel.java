package models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CityFragmentViewModel extends ViewModel {
    private LiveData<List<City>> cityList = CityMock.instance().getCities();

    public LiveData<List<City>> getCityList() {
        return cityList;
    }
}
