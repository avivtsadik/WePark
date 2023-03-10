package models;

import androidx.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class UserConverter {
    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<String> toList(String string) {
        if (string == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(string, type);
    }
}
