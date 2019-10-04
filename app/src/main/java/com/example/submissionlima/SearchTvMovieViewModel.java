package com.example.submissionlima;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvMovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvMovie>> users;
    private MutableLiveData<String> x = new MutableLiveData<>();



    public LiveData<String> getX(String query){
        if (x ==null){
            x = new MutableLiveData<String>();
            setQuery(query);
        }
        return x;
    }

    public LiveData<ArrayList<TvMovie>> getUsers(String query) {
        if (users == null) {
            users = new MutableLiveData<ArrayList<TvMovie>>();
            loadUsers(query);
        }
        return users;
    }


    private void setQuery(final String query){
    }
    private void loadUsers(String query) {
        final RequestInterface requestInterface = MovieClient.Retrofit().create(RequestInterface.class);
        requestInterface.getSearchTvMovie(query).enqueue(new Callback<TvMovieResponse>() {

            @Override
            public void onResponse(Call<TvMovieResponse> call, Response<TvMovieResponse> response) {
                if (response.body() != null) {
                    users.getValue();
                    users.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TvMovieResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }
}
