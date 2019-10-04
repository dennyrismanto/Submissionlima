package com.example.submissionlima;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.FIRSTAIRDATE;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.ORIGINAL_NAME;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.OVERVIEW;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.POSTERPATH;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TABLE_NOTE_TVMOVIE;

public class TvMovieHelper  {
    private static final String DATABASE_TABLE = TABLE_NOTE_TVMOVIE;
    private static TvMovieHelper INSTANCE;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;

    private TvMovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvMovie> getAllNotes() {
        ArrayList<TvMovie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvMovie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new TvMovie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setOriginalName(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_NAME)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(FIRSTAIRDATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor cekData2(String title){
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                ORIGINAL_NAME + "=?",
                new String[]{title},
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public long insertNote(TvMovie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_NAME, movie.getOriginalName());
        args.put(OVERVIEW, movie.getOverview());
        args.put(FIRSTAIRDATE, movie.getFirstAirDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(TvMovie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_NAME, movie.getOriginalName());
        args.put(OVERVIEW, movie.getOverview());
        args.put(FIRSTAIRDATE, movie.getFirstAirDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(TABLE_NOTE_TVMOVIE, _ID + " = '" + id + "'", null);
    }

// Content provider
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
