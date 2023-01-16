package models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wepark.R;

import java.util.HashMap;
import java.util.Map;

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

    public static  Parking fromJson(Map<String,Object> json){
        String id = (String)json.get("id");
        String city = (String)json.get("city");
        String size = (String)json.get("size");
        String userId = (String)json.get("userId");
        Parking pr = new Parking(id,userId,city,size);
        return pr;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("city", getCity());
        json.put("size", getSize());
        json.put("userId", getUserId());
        return json;
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
