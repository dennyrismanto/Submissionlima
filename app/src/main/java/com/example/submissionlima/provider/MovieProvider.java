package com.example.submissionlima.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.submissionlima.MovieFavorite;
import com.example.submissionlima.MovieHelper;

import static com.example.submissionlima.db.DatabaseMovieContract.AUTHORITY;
import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.CONTENT_URI;
import static com.example.submissionlima.db.DatabaseMovieContract.TABLE_NOTE_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;


    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NOTE_MOVIE, NOTE);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NOTE_MOVIE + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                movieHelper.open();
                cursor = movieHelper.queryProvider();
                break;
            case NOTE_ID:
                movieHelper.open();
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                movieHelper.open();
                added = movieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        movieHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {

        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                movieHelper.open();
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}
