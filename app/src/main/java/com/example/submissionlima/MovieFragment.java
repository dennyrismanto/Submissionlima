package com.example.submissionlima;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private MovieAdapter adapter = new MovieAdapter();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar loading;
    private RecyclerView rvCategory;
    public static final String KEY_MOVIES = "saveMovies";
    private static final String TAG = "TAG";
    private int someStateValue;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"Fragment onCreateView method is called.");
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setAdapter(adapter);
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(),2));
        loading = view.findViewById(R.id.progressBar);
        showLoading(true);
        setHasOptionsMenu(true);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getUsers().observe(this, this.getMov);

    }

    private Observer<ArrayList<Movie>> getMov = new Observer<ArrayList<Movie>>() {

        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setMovies(movies);
                showLoading(false);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Observer<ArrayList<Movie>> getSearchMovie = new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    if (movies != null) {
                        adapter.setMovies(movies);
                    }
                }
            };
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    SearchMovieViewModel modelSearch = ViewModelProviders.of(MovieFragment.this).get(SearchMovieViewModel.class);
                    modelSearch.getUsers(query).observe(MovieFragment.this, this.getSearchMovie);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void showLoading(Boolean state) {
        if (state) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

}

