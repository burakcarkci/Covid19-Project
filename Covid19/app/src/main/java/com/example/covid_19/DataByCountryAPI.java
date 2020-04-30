package com.example.covid_19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataByCountryAPI {

    @GET("live/country/{country}")
    Call<List<DataByCountryResponse>> getDataByCountryResponse(@Path("country") String countryName, @Query("from") String from, @Query("to") String to);
}
