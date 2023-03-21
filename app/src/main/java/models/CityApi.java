package models;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CityApi {
    @GET("/api/records/1.0/search/?dataset=geonames-all-cities-with-a-population-1000%40public&q=&sort=name&facet=feature_code&facet=cou_name_en&facet=timezone&refine.cou_name_en=Israel")
    Call<CitiesResult> getCities();
}
