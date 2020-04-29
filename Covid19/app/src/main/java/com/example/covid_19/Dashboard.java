package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//###########################################################################################################################

public class Dashboard extends AppCompatActivity {

    SearchView countries;
    Button news, stats;
    TextView statsResults, newsResults;

    public static String country = "us";
    public static String keyword = "coronavirus";
    public static String page_size = "99";
    public static String api_key = "cc0ab105fa8d477f82239da2e98fe95b";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        countries = findViewById(R.id.countries);
        news = findViewById(R.id.news);
        stats = findViewById(R.id.stats);
        statsResults = findViewById(R.id.statsResults);


        //Event Listener for Stats
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatisticData();
            }
        });

        //Event Listener for News
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewsData();
            }
        });

    }

//###########################################################################################################################

    //Function for Getting Stats Data
    void getStatisticData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StatsAPI statsAPI = retrofit.create(StatsAPI.class);

        Call<StatsResponse> call = statsAPI.getStatsResponse();

        call.enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StatsResponse> call, @NonNull Response<StatsResponse> response) {
                if(!response.isSuccessful()){
                    //statsResults.setText("Code: " + response.code());
                    return;
                }

                StatsResponse statsResponse = response.body();

                assert statsResponse != null;

                String stringBuilder =
                        "New Confirmed: " + statsResponse.global.getNewConfirmed() + "\n" +
                                "Total Confirmed: " + statsResponse.global.getTotalConfirmed() + "\n" +
                                "New Deaths: " + statsResponse.global.getNewDeaths() + "\n" +
                                "Total Deaths: " + statsResponse.global.getTotalDeaths() + "\n" +
                                "New Recovered: " + statsResponse.global.getNewRecovered() + "\n" +
                                "Total Recovered: " + statsResponse.global.getTotalRecovered() + "\n\n";

                statsResults.setText(stringBuilder);

            }

            @Override
            public void onFailure(@NonNull Call<StatsResponse> call, @NonNull Throwable t) {
                statsResults.setText(t.getMessage());
            }
        });
    }


//###########################################################################################################################

    //Function for Getting News Data
    void getNewsData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        Call<List<NewsResponse>> call = newsAPI.getNewsResponse(country, keyword, page_size, api_key);

        call.enqueue(new Callback<List<NewsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<NewsResponse>> call, @NonNull Response<List<NewsResponse>> response) {
                //The HTTP 200 OK success status response code indicates that the request has succeeded.
                if(response.code() == 200){

                    List<NewsResponse> newsResponse = response.body();

                    assert newsResponse != null;

                    for(NewsResponse news : newsResponse){
                        String content = "";
                        content += "Title: " + news.articlesList.get(0).getTitle() + "\n\n";

                        newsResults.append(content);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull  Call<List<NewsResponse>> call, @NonNull Throwable t) {
                newsResults.setText(t.getMessage());
            }
        });
    }
//###########################################################################################################################




}
