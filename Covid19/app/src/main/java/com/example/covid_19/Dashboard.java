package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

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

public class Dashboard extends AppCompatActivity {

    SearchView countries;
    Button news, stats;
    TextView statsResults, newsResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        countries = findViewById(R.id.countries);
        news = findViewById(R.id.news);
        stats = findViewById(R.id.stats);
        statsResults = findViewById(R.id.statsResults);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentData();
            }
        });

    }


    void getCurrentData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StatsAPI statsAPI = retrofit.create(StatsAPI.class);

        Call<StatsResponse> call = statsAPI.getStatsResponse();

        call.enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                if(!response.isSuccessful()){
                    statsResults.setText("Code: " + response.code());
                    return;
                }

                StatsResponse statsResponse = response.body();

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
            public void onFailure(Call<StatsResponse> call, Throwable t) {
                statsResults.setText(t.getMessage());
            }
        });
    }





}
