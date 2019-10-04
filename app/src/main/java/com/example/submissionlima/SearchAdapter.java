package com.example.submissionlima;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public SearchAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SearchAdapter.MovieViewHolder(inflater.inflate(R.layout.itemrow_search, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MovieViewHolder categoryViewHolder, final int position) {
        Movie movie = movies.get(position);
        if(movie !=null){
            categoryViewHolder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie>movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public ArrayList<Movie> getMovies() {
        return movies;
    }
    public void addItem(Movie movie) {
        this.movies.add(movie);
        notifyItemInserted(movies.size() - 1);
    }
    public void updateItem(int position, Movie movie) {
        this.movies.set(position, movie);
        notifyItemChanged(position, movie);
    }
    public void removeItem(int position) {
        this.movies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,movies.size());
    }
    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvName2, tvRemarks2, tvRemarks, tvDescription;
        ImageView imgPhoto;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvName2 = itemView.findViewById(R.id.tv_item_name2);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
            tvRemarks2 = itemView.findViewById(R.id.tv_item_remarks2);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);

        }

        void bind(Movie movie) {
            int pos = getAdapterPosition();
            Log.d(SearchAdapter.class.getSimpleName(), "bind: " + movie.getOriginalTitle());
            tvName.setText(getMovies().get(pos).getOriginalTitle());
            tvName2.setText(getMovies().get(pos).getOriginalName());
            tvRemarks.setText(getMovies().get(pos).getReleaseDate());
            tvRemarks2.setText(getMovies().get(pos).getFirstAirDate());
            tvDescription.setText(getMovies().get(pos).getOverview());
            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Movie clickDataItem = movies.get(pos);
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra("KEY_EXTRA", clickDataItem);
                    context.startActivity(detailIntent);
                }
            });

        }
    }
}
