package com.example.submissionlima;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.ORIGINAL_TITLE;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.OVERVIEW;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.POSTERPATH;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.RELEASEDATE;


public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID));
//            String originalname = notesCursor.getString(notesCursor.getColumnIndex(ORIGINAL_NAME));
            String originaltitle = notesCursor.getString(notesCursor.getColumnIndexOrThrow(ORIGINAL_TITLE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String releasedate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASEDATE));
            String posterpath = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTERPATH));
//            String firstairdate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(FIRSTAIRDATE));
            movies.add(new Movie(id, originaltitle, overview,  releasedate, posterpath ));
        }
        return movies;
    }


}
