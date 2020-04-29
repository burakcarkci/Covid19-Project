package com.example.covid_19;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsResponse {
    @SerializedName("status")
    public Status status;
    @SerializedName("totalResults")
    public TotalResults totalResults;
    @SerializedName("articles")
    public ArrayList<articles> articlesList = new ArrayList<articles>();
}

class Status{
    public String status;

    public String getStatus() {
        return status;
    }
}

class TotalResults{
    public int totalResults;

    public int getTotalResults() {
        return totalResults;
    }
}

class articles{
    public String title;

    public String getTitle() {
        return title;
    }
}