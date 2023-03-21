package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("alternate_names")
    @Expose
    String asciiName;

    public String getName() {
        return asciiName;
    }
}
