package com.example.submissionlima;

import android.renderscript.Sampler;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("discover/movie")
    Call<MovieResponse> getMovie(@Query("page") int page);
    @GET("search/movie")
    Call<MovieResponse> getSearchMovie(@Query("query") String query);
    @GET("discover/tv")
    Call<TvMovieResponse> getTvShow(@Query("page") int page);
    @GET("search/tv")
    Call<TvMovieResponse> getSearchTvMovie(@Query("query") String query);
    @GET("search/multi")
    Call<MovieResponse> getSearch(@Query("query") String query);
    @GET("discover/movie")
    Call<MovieResponse> getUpdate(@Query("primary_release_date.lte") String primary_release_date_lte  );
}
