package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CitiesResult {
    @SerializedName("records")
    @Expose
    ArrayList<City> records;

    public ArrayList<City> getCities() {
        return records;
    }

    public void setCities(ArrayList<City> cities) {
        this.records = cities;
    }
}
