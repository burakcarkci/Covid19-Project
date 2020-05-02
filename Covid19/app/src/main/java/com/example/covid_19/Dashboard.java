package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//###########################################################################################################################

public class Dashboard extends AppCompatActivity {

    SearchView countries;
    Button news, stats, searchButton, chartButton;
    TextView statsResults, newsResults, dataByCountryResults;
    AnyChartView chartView;

    //AnyChart Data
    static int confirmedPatients;
    static int totalDeaths;
    static int recoveredPatients;


    //Variables for News API
    public static String country = "us";
    public static String keyword = "coronavirus";
    public static String page_size = "99";
    public static String api_key = "cc0ab105fa8d477f82239da2e98fe95b";

    //Variables for Data By Country API
    public static String from = "2020-04-28";
    public static  String to = "2020-04-29";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        countries = findViewById(R.id.countries);
        news = findViewById(R.id.news);
        stats = findViewById(R.id.stats);
        statsResults = findViewById(R.id.statsResults);
        dataByCountryResults = findViewById(R.id.dataByCountryResults);
        searchButton = findViewById(R.id.searchButton);
        newsResults = findViewById(R.id.newsResults);
        chartView = findViewById(R.id.chartView);
        chartButton = findViewById(R.id.chartButton);

        //######################################################################

        //OnClicklistener for Stats
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatisticData();
                //Clear all text from other textview field
                dataByCountryResults.setText("");
            }
        });

        //######################################################################

        //OnClicklistener for News
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewsData();
                chartView.setVisibility(View.INVISIBLE);
            }
        });


        //######################################################################

        //OnClicklistener for Search By Country
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByCountry();
                //Clear all text from other textview field
                statsResults.setText("");
            }
        });

        //######################################################################

        //OnClicklistener for Search By Country
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupPieChart();
                //Clear all text from other textview field
                newsResults.setText("");
            }
        });

    }
//###########################################################################################################################

    //Function for setting up chart
    void setupPieChart(){

        chartView.setVisibility(View.VISIBLE);

        Pie pie = AnyChart.pie();


        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Confirmed Patients", confirmedPatients));
        data.add(new ValueDataEntry("T. Deaths", totalDeaths));
        data.add(new ValueDataEntry("Recovered Patients", recoveredPatients));


        pie.data(data);


        pie.labels().position("outside");
        pie.title("World Statistics Chart");
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pie.draw(true);

        chartView.setChart(pie);

    }








//###########################################################################################################################

    //Function for getting data by country
    void getDataByCountry(){

        String countryChoice = countries.getQuery().toString();

        if(countryChoice.isEmpty()){

            String warning = "Country name can't be empty!";

            dataByCountryResults.setText(warning);
        }
        else{

            Retrofit retrofit = new  Retrofit.Builder()
                    .baseUrl("https://api.covid19api.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DataByCountryAPI dataByCountryAPI = retrofit.create(DataByCountryAPI.class);

            Call<List<DataByCountryResponse>> call = dataByCountryAPI.getDataByCountryResponse(countryChoice, from, to);

            call.enqueue(new Callback<List<DataByCountryResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<DataByCountryResponse>> call, @NonNull Response<List<DataByCountryResponse>> response) {
                    if(response.code() == 200){

                        List<DataByCountryResponse> dataByResponse = response.body();

                        assert dataByResponse != null;

                        if(!dataByResponse.isEmpty()) {
                            DataByCountryResponse dataByCountryResponse = dataByResponse.get(0);
                            String dataBuilder =
                                    "Country: " + dataByCountryResponse.getCountry() + "\n" +
                                            "Confirmed Cases: " + dataByCountryResponse.getConfirmed() + "\n" +
                                            "Deaths: " + dataByCountryResponse.getDeaths() + "\n" +
                                            "Recovered: " + dataByCountryResponse.getRecovered() + "\n";

                            dataByCountryResults.setText(dataBuilder);
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<DataByCountryResponse>> call, @NonNull Throwable t) {
                    dataByCountryResults.setText(t.getMessage());
                }
            });
        }
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
                if(response.code() == 200) {

                    StatsResponse statsResponse = response.body();

                    assert statsResponse != null;

                    //Data for PIE Chart
                    confirmedPatients = statsResponse.global.getTotalConfirmed();
                    totalDeaths = statsResponse.global.getTotalDeaths();
                    recoveredPatients = statsResponse.global.getTotalRecovered();

                    //Creating a string to show data in textView
                    String stringBuilder =
                            "New Confirmed: " + statsResponse.global.getNewConfirmed() + "\n" +
                                    "Total Confirmed: " + statsResponse.global.getTotalConfirmed() + "\n" +
                                    "New Deaths: " + statsResponse.global.getNewDeaths() + "\n" +
                                    "Total Deaths: " + statsResponse.global.getTotalDeaths() + "\n" +
                                    "New Recovered: " + statsResponse.global.getNewRecovered() + "\n" +
                                    "Total Recovered: " + statsResponse.global.getTotalRecovered() + "\n\n";

                    statsResults.setText(stringBuilder);
                }

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

        Call<NewsResponse> call = newsAPI.getNewsResponse(country, keyword, page_size, api_key);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                //The HTTP 200 OK success status response code indicates that the request has succeeded.
                if(response.code() == 200){

                    NewsResponse newsResponse = response.body();

                    assert newsResponse != null;

                    for(Article article : newsResponse.articlesList){
                        String content = "";
                        content += "News Title: " + article.getTitle() + "\n\n";

                        newsResults.append(content);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull  Call<NewsResponse> call, @NonNull Throwable t) {
                newsResults.setText(t.getMessage());
            }
        });
    }


//###########################################################################################################################
}
