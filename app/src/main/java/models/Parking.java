package models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wepark.R;

@Entity
public class Parking {
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String city;
    private String size;

    public Parking(String id, String userId, String city, String size) {
        this.id = id;
        this.userId = userId;
        this.city = city;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
