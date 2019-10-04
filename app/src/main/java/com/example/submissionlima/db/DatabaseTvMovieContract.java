package com.example.submissionlima.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseTvMovieContract {
    public static final String AUTHORITY = "com.example.submissionlima.db.tv";
    private static final String SCHEME = "content";

    public static String TABLE_NOTE_TVMOVIE = "note2";

    private DatabaseTvMovieContract(){}
    public static final class TvMovieColumns implements BaseColumns {
//        public static String ORIGINAL_TITLE = "original_title";
        public static String ORIGINAL_NAME = "original_name";
        public static String OVERVIEW = "overview";
//        public static String RELEASEDATE = "release_date";
        public static String POSTERPATH = "poster_path";
        public static String FIRSTAIRDATE = "first_air_date";
        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NOTE_TVMOVIE)
        .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
