package com.example.covid_19;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsResponse {
    @SerializedName("articles")
    public ArrayList<Article> articlesList = new ArrayList<Article>();
}

class Article{
    public String title;

    public String getTitle() {
        return title;
    }
}