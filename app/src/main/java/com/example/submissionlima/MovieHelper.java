package com.example.submissionlima;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.ORIGINAL_TITLE;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.OVERVIEW;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.POSTERPATH;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.RELEASEDATE;
import static com.example.submissionlima.db.DatabaseMovieContract.TABLE_NOTE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NOTE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public MovieHelper() {
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllNotes() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH)));
                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor cekData(String title){
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                ORIGINAL_TITLE + "=?",
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
    public Cursor cekImage(String image){
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                POSTERPATH + "=?",
                new String[]{image},
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public long insertNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_TITLE, movie.getOriginalTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASEDATE, movie.getReleaseDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_TITLE, movie.getOriginalTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASEDATE, movie.getReleaseDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(TABLE_NOTE_MOVIE, _ID + " = '" + id + "'", null);
    }

//Content Provider
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