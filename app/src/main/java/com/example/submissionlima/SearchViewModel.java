package com.example.submissionlima;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> users;
    private MutableLiveData<String> x = new MutableLiveData<>();



    public LiveData<String> getX(String query){
        if (x ==null){
            x = new MutableLiveData<String>();
            setQuery(query);
        }
        return x;
    }

    public LiveData<ArrayList<Movie>> getUsers(String query) {
        if (users == null) {
            users = new MutableLiveData<ArrayList<Movie>>();
            loadUsers(query);
        }
        return users;
    }


    private void setQuery(final String query){
    }
    private void loadUsers(String query) {
        final RequestInterface requestInterface = MovieClient.Retrofit().create(RequestInterface.class);
        requestInterface.getSearch(query).enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {
                    users.getValue();
                    users.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }
}
