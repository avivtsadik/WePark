package models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityMock {
    public static final CityMock instance = new CityMock();

    final String BASE_URL = "https://data.gov.il/api/3/action/datastore_search/";
    Retrofit retrofit;
    CityApi cityApi;

    public CityMock() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        cityApi = retrofit.create(CityApi.class);
    }

    public LiveData<List<City>> getCities() {
        MutableLiveData<List<City>> data = new MutableLiveData<>();
        Call<CitiesResult> call = cityApi.getCities();
        call.enqueue(new Callback<CitiesResult>() {
            @Override
            public void onResponse(Call<CitiesResult> call, Response<CitiesResult> response) {
                if (response.isSuccessful()) {
                    CitiesResult res = response.body();
                    data.setValue(res.getCities());
                } else {
                    Log.d("TAG", "response error");
                }
            }

            @Override
            public void onFailure(Call<CitiesResult> call, Throwable t) {
                Log.d("TAG", "fail");
            }
        });
        return data;
    }
}
