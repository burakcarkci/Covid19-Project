package com.example.covid_19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatsAPI {

    @GET("summary?")
    Call<StatsResponse> getStatsResponse();

}
