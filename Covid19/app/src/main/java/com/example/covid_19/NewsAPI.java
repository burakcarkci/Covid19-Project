package com.example.covid_19;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("v2/top-headlines") //country=us&q=coronavirus&pageSize=99&apiKey=cc0ab105fa8d477f82239da2e98fe95b")
    Call<List<NewsResponse>> getNewsResponse(@Query("country") String country, @Query("q") String keyword, @Query("pageSize") String page_size, @Query("apiKey") String api_key);
}
