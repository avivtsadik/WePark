package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesResult {
    @SerializedName("records")
    List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
