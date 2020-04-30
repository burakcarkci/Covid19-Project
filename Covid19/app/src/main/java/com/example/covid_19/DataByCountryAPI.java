package com.example.covid_19;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataByCountryAPI {

    @GET("live/country/")
    Call<DataByCountryResponse> getDataByCountryResponse(@Query("") String countryName, @Query("from") String from, @Query("to") String to);
}
