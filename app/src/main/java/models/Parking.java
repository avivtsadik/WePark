package models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wepark.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

import application.WeParkApplication;

@Entity
public class Parking {
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String city;
    private String size;
    private String avatarUrl;
    private Long lastUpdated;

    public Parking(String id, String userId, String city, String size, String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.city = city;
        this.size = size;
        this.avatarUrl = avatarUrl;
    }
    static final String ID = "id";
    static final String CITY = "city";
    static final String SIZE = "size";
    static final String USER_ID = "userId";
    static final String AVATAR_URL = "avatarUrl";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "parkings_local_last_update";

    public static Parking fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String city = (String) json.get(CITY);
        String size = (String) json.get(SIZE);
        String userId = (String) json.get(USER_ID);
        String avatarUrl = (String) json.get(AVATAR_URL);
        Parking pr = new Parking(id, userId, city, size, avatarUrl);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            pr.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return pr;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = WeParkApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = WeParkApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(CITY, getCity());
        json.put(SIZE, getSize());
        json.put(USER_ID, getUserId());
        json.put(AVATAR_URL, getAvatarUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long time) {
        this.lastUpdated =  time;
    }
}
