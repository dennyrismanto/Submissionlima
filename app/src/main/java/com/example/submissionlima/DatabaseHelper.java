package com.example.submissionlima;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submissionlima.db.DatabaseMovieContract;
import com.example.submissionlima.db.DatabaseTvMovieContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbnoteapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseMovieContract.TABLE_NOTE_MOVIE,
            DatabaseMovieContract.NoteColumns._ID,
            DatabaseMovieContract.NoteColumns.ORIGINAL_TITLE,
            DatabaseMovieContract.NoteColumns.OVERVIEW,
            DatabaseMovieContract.NoteColumns.RELEASEDATE,
            DatabaseMovieContract.NoteColumns.POSTERPATH
    );
    private static final String SQL_CREATE_TABLE_NOTE_TVMOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseTvMovieContract.TABLE_NOTE_TVMOVIE,
            DatabaseTvMovieContract.TvMovieColumns._ID,
            DatabaseTvMovieContract.TvMovieColumns.ORIGINAL_NAME,
            DatabaseTvMovieContract.TvMovieColumns.OVERVIEW,
            DatabaseTvMovieContract.TvMovieColumns.FIRSTAIRDATE,
            DatabaseTvMovieContract.TvMovieColumns.POSTERPATH
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_NOTE_TVMOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMovieContract.TABLE_NOTE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseTvMovieContract.TABLE_NOTE_TVMOVIE);
        onCreate(db);
    }
}
