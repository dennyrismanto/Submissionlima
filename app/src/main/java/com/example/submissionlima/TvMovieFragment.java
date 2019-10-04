package com.example.submissionlima;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TvMovieFragment extends Fragment {
    private TvMovieAdapter adapter = new TvMovieAdapter();
    private ArrayList<TvMovie> moviestv = new ArrayList<>();
    private ProgressBar loading;
    public RecyclerView rvCategory1;
    public static final String KEY_tv_MOVIES = "saveMovies";
    private static final String TAG = "TAG";
    private int someStateValue;

    public TvMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"Fragment onCreateView method is called.");
        return inflater.inflate(R.layout.fragment_tv_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        rvCategory1 = view.findViewById(R.id.rv_category1);
        rvCategory1.setAdapter(adapter);
        loading = view.findViewById(R.id.progressBar);
        setHasOptionsMenu(true);

        TvMovieViewModel modeltv = ViewModelProviders.of(this).get(TvMovieViewModel.class);
        modeltv.getUsers().observe(this, this.getTV);
    }

    private Observer<ArrayList<TvMovie>> getTV = new Observer<ArrayList<TvMovie>>() {

        @Override
        public void onChanged(ArrayList<TvMovie> moviestv) {
            if (moviestv != null) {
                adapter.setMovies(moviestv);
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
            private Observer<ArrayList<TvMovie>> getSearchTvMovie = new Observer<ArrayList<TvMovie>>() {
                @Override
                public void onChanged(ArrayList<TvMovie> movies) {
                    if (movies != null) {
                        adapter.setMovies(movies);
                    }
                }
            };
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    SearchTvMovieViewModel model = ViewModelProviders.of(TvMovieFragment.this).get(SearchTvMovieViewModel.class);
                    model.getUsers(query).observe(TvMovieFragment.this, this.getSearchTvMovie);
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
