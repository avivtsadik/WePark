package models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import application.WeParkApplication;
import services.LoginService;

@Entity
@TypeConverters({UserConverter.class})
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String displayName;
    private Long lastUpdated;
    private List<String> favorites;
    //    @ColumnInfo(name = "favorites")

    public User(String id, String displayName, List<String> favorites) {
        this.id = id;
        this.displayName = displayName;
        this.favorites = favorites;
    }
    public User() {
        this.id = LoginService.instance().getLoginService().getUserId();
        this.displayName = LoginService.instance().getLoginService().getUserName();
        this.favorites = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    static final String ID = "id";
    static final String DISPLAY_NAME = "displayName";
    static final String FAVORITES = "favorites";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "favorites_local_last_update";

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(DISPLAY_NAME, getDisplayName());
        json.put(FAVORITES, getFavorites());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
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
    public static User fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String display = (String) json.get(DISPLAY_NAME);
        List<String> favorites = (List<String>) json.get(FAVORITES);
        User pr = new User(id, display, favorites);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            pr.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return pr;
    }
    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long time) {
        this.lastUpdated =  time;
    }
}

