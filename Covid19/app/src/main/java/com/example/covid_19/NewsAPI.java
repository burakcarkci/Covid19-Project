package com.example.covid_19;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("v2/top-headlines")
    Call<NewsResponse> getNewsResponse(@Query("country") String country, @Query("q") String keyword, @Query("pageSize") String page_size, @Query("apiKey") String api_key);
}
