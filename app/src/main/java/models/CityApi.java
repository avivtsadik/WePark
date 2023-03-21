package models;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CityApi {
    @GET("/maps/api/place/textsearch/json?query=IL&types=locality&language=en&key=AIzaSyCM3xBws9NXYpIIxXUantYx6xKZ49NJfIs")
    Call<CitiesResult> getCities();
}
