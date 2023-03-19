package models;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CityApi {
    @GET("/")
    Call<CitiesResult> getCities();
}
