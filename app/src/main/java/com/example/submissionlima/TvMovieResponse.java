package com.example.submissionlima;

import java.util.ArrayList;

public class TvMovieResponse {
    private ArrayList<TvMovie> results;
    private int page;
    private int total_results;
    private int total_pages;

    public TvMovieResponse(ArrayList<TvMovie> results, int page, int total_pages, int total_results){
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }
    public ArrayList<TvMovie> getResults(){
        return results;
    }
    public int getPage(){
        return page;
    }
    public int getTotal_results(){
        return total_results;
    }
    public int getTotal_pages(){
        return total_pages;
    }
}
