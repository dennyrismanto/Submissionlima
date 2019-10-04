package com.example.submissionlima;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.submissionlima.db.DatabaseMovieContract;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.submissionlima.db.DatabaseMovieContract.getColumnInt;
import static com.example.submissionlima.db.DatabaseMovieContract.getColumnString;

public class Movie implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("video")
    private boolean video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @SerializedName("vote_average")
    private double voteAverage;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalName);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.firstAirDate);
        dest.writeList(this.genreIds);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.voteAverage);
    }

    public Movie() {
    }
    //Menggunakan content provider
    public Movie(int id, String originaltitle, String overview, String releasedate, String posterpath) {
        this.id = id;
        this.originalTitle = originaltitle;
        this.overview = overview;
        this.releaseDate = releasedate;
        this.posterPath = posterpath;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.originalTitle = getColumnString(cursor, DatabaseMovieContract.NoteColumns.ORIGINAL_TITLE);
        this.overview = getColumnString(cursor, DatabaseMovieContract.NoteColumns.OVERVIEW);
        this.releaseDate = getColumnString(cursor, DatabaseMovieContract.NoteColumns.RELEASEDATE);
//        this.firstAirDate = getColumnString(cursor, DatabaseMovieContract.NoteColumns.FIRSTAIRDATE);
//        this.originalName = getColumnString(cursor, DatabaseMovieContract.NoteColumns.ORIGINAL_NAME);
        this.posterPath = getColumnString(cursor, DatabaseMovieContract.NoteColumns.POSTERPATH);
    }
    public Movie(long movieId, String movieTitle, String poster, String back, String date, Double rate, String ovr) {
        this.id = (int) movieId;
        this.originalTitle = movieTitle;
        this.posterPath = poster;
        this.backdropPath = back;
        this.releaseDate = date;
        this.voteAverage = rate;
        this.overview = ovr;
    }
//Batasnya
    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.originalName = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.firstAirDate = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.backdropPath = in.readString();
        this.popularity = in.readDouble();
        this.voteCount = in.readInt();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
