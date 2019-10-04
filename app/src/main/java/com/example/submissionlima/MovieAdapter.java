package com.example.submissionlima;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.CONTENT_URI;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MovieViewHolder(inflater.inflate(R.layout.itemrow_movie2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder categoryViewHolder, final int position) {
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
        this.movies.clear();
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
    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvRemarks, tvDescription;
        ImageView imgPhoto;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);

        }
        void bind(Movie movie){
            int pos = getAdapterPosition();
            Log.d(MovieAdapter.class.getSimpleName(), "bind: " + movie.getOriginalTitle());
//            tvName.setText(movie.getOriginalTitle());
//            tvRemarks.setText(String.valueOf(movie.getReleaseDate()));
//            tvDescription.setText(movie.getOverview());
//            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());
            tvName.setText(getMovies().get(pos).getOriginalTitle());
            tvRemarks.setText(getMovies().get(pos).getReleaseDate());
            tvDescription.setText(getMovies().get(pos).getOverview());
            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Movie clickDataItem = movies.get(pos);
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI + "/" + getMovies().get(pos).getId());
                    detailIntent.setData(uri);
                    detailIntent.putExtra("KEY_EXTRA", clickDataItem);
                    context.startActivity(detailIntent);
                }
            });

        }
    }

}

