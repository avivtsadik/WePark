package models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Parking {
    @PrimaryKey
    @NonNull
    private int id;
    private String city;
    private ParkingSize size;

    public Parking(int id, String city, ParkingSize size) {
        this.id = id;
        this.city = city;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ParkingSize getSize() {
        return size;
    }

    public void setSize(ParkingSize size) {
        this.size = size;
    }
}
