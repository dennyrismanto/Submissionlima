package com.example.submissionlima.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseMovieContract {
    public static final String AUTHORITY = "com.example.submissionlima.db";
    private static final String SCHEME = "content";
//    public static final String AUTHORITY2 = "com.example.tv.submissionlima";
//    private static final String SCHEME2 = "content";

    public static String TABLE_NOTE_MOVIE = "note1";


    private DatabaseMovieContract(){}

    public static final class NoteColumns implements BaseColumns {
        public static String ORIGINAL_TITLE = "original_title";
//        public static String ORIGINAL_NAME = "original_name";
        public static String OVERVIEW = "overview";
        public static String RELEASEDATE = "release_date";
        public static String POSTERPATH = "poster_path";
//        public static String FIRSTAIRDATE = "first_air_date";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NOTE_MOVIE)
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
