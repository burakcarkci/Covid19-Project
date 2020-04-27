package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

public class Dashboard extends AppCompatActivity {

    SearchView countries;
    Button news, stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        countries = findViewById(R.id.countries);
        news = findViewById(R.id.news);
        stats = findViewById(R.id.stats);

    }
}
