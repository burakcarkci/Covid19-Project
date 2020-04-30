package com.example.covid_19;

import com.google.gson.annotations.SerializedName;

public class DataByCountryResponse {
    @SerializedName("Country")
    public String country;
    @SerializedName("Confirmed")
    public int confirmed;
    @SerializedName("Deaths")
    public int deaths;
    @SerializedName("Recovered")
    public int recovered;

    public String getCountry() {
        return country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }
}
