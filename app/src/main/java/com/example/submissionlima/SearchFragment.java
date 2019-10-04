package com.example.submissionlima;



import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private Context context;
    private RecyclerView rvSearch;
    private SearchAdapter searchAdapter = new SearchAdapter();
    private ImageView imgPhoto;
    private Movie movie;
    TextView tvName, tvRemarks, tvRemarks2, tvDescription;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvSearch = view.findViewById(R.id.rv_category2);
        rvSearch.setAdapter(searchAdapter);
        setHasOptionsMenu(true);
        tvName = view.findViewById(R.id.tv_item_name);
        imgPhoto = view.findViewById(R.id.img_item_photo);
        tvRemarks = view.findViewById(R.id.tv_item_remarks);
        tvRemarks2 = view.findViewById(R.id.tv_item_remarks2);
        tvDescription = view.findViewById(R.id.tv_item_description);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Observer<ArrayList<Movie>> getSearch = new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    if (movies != null) {
                        searchAdapter.setMovies(movies);
                    }
                }
            };
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                SearchViewModel model = ViewModelProviders.of(SearchFragment.this).get(SearchViewModel.class);
                model.getUsers(query).observe(SearchFragment.this, this.getSearch);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }




}
