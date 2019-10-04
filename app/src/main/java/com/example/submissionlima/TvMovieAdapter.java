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

import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.CONTENT_URI_TV;

public class TvMovieAdapter extends RecyclerView.Adapter<TvMovieAdapter.MovieViewHolder> {
    private ArrayList<TvMovie> movies = new ArrayList<>();
    private Context context;



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MovieViewHolder(inflater.inflate(R.layout.itemrow_tvmovie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder categoryViewHolder, final int position) {
        TvMovie movie = movies.get(position);
        if(movie !=null){
            categoryViewHolder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<TvMovie>movies){
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public ArrayList<TvMovie> getMovies() {
        return movies;
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
        void bind(TvMovie movie){
            Log.d(MovieAdapter.class.getSimpleName(), "bind: " + movie.getOriginalName());
            int pos = getAdapterPosition();
//            tvName.setText(movie.getOriginalName());
//            tvRemarks.setText(String.valueOf(movie.getFirstAirDate()));
//            tvDescription.setText(movie.getOverview());
//            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());
            tvName.setText(getMovies().get(pos).getOriginalName());
            tvRemarks.setText(getMovies().get(pos).getFirstAirDate());
            tvDescription.setText(getMovies().get(pos).getOverview());
            MovieClient.LoadImage(context, imgPhoto, movie.getPosterPath());

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    TvMovie clickDataItem = movies.get(pos);
                    Intent detailIntent = new Intent(context, TvMovieDetail.class);
                    detailIntent.putExtra("KEY_EXTRA", clickDataItem);
                    Uri uri = Uri.parse(CONTENT_URI_TV + "/" + getMovies().get(pos).getId());
                    detailIntent.setData(uri);
                    context.startActivity(detailIntent);
                }
            });
        }
    }
}

