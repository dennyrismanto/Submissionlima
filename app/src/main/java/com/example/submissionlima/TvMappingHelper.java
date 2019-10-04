package com.example.submissionlima;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.FIRSTAIRDATE;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.ORIGINAL_NAME;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.OVERVIEW;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.POSTERPATH;

public class TvMappingHelper {
    public static ArrayList<TvMovie> mapCursorToArrayTvList(Cursor notesTvCursor) {
        ArrayList<TvMovie> movies = new ArrayList<>();
        while (notesTvCursor.moveToNext()) {
            int id = notesTvCursor.getInt(notesTvCursor.getColumnIndexOrThrow(_ID));
            String originalname = notesTvCursor.getString(notesTvCursor.getColumnIndex(ORIGINAL_NAME));
//            String originaltitle = notesCursor.getString(notesCursor.getColumnIndexOrThrow(ORIGINAL_TITLE));
            String overview = notesTvCursor.getString(notesTvCursor.getColumnIndexOrThrow(OVERVIEW));
//            String releasedate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASEDATE));
            String firstairdate = notesTvCursor.getString(notesTvCursor.getColumnIndexOrThrow(FIRSTAIRDATE));
            String posterpath = notesTvCursor.getString(notesTvCursor.getColumnIndexOrThrow(POSTERPATH));
            movies.add(new TvMovie(id, originalname, overview,  firstairdate, posterpath ));
        }
        return movies;
    }
}
