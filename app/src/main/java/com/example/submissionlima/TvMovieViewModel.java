package com.example.submissionlima;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvMovieViewModel extends ViewModel {
    private ArrayList<TvMovie> moviestv = new ArrayList<>();
    private MutableLiveData<ArrayList<TvMovie>> users;

    public LiveData<ArrayList<TvMovie>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<ArrayList<TvMovie>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        final RequestInterface requestInterface = MovieClient.Retrofit().create(RequestInterface.class);
        requestInterface.getTvShow(1).enqueue(new Callback<TvMovieResponse>() {

            @Override
            public void onResponse(Call<TvMovieResponse> call, Response<TvMovieResponse> response) {
                Log.d("Data", response.body().getResults().get(0).getOriginalName());
                users.postValue(response.body().getResults());

            }

            @Override
            public void onFailure(Call<TvMovieResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }
}