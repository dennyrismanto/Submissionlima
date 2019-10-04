package com.example.submissionlima.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.submissionlima.TvMovieFavorite;
import com.example.submissionlima.TvMovieHelper;

import static com.example.submissionlima.db.DatabaseTvMovieContract.AUTHORITY;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.CONTENT_URI_TV;
import static com.example.submissionlima.db.DatabaseTvMovieContract.TABLE_NOTE_TVMOVIE;

public class TvMovieProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TvMovieHelper tvmovieHelper;

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NOTE_TVMOVIE, NOTE);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NOTE_TVMOVIE + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        tvmovieHelper = TvMovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                tvmovieHelper.open();
                cursor = tvmovieHelper.queryProvider();
                break;
            case NOTE_ID:
                tvmovieHelper.open();
                cursor = tvmovieHelper.queryByIdProvider(uri.getLastPathSegment());
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
                tvmovieHelper.open();
                added = tvmovieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, new TvMovieFavorite.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        tvmovieHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = tvmovieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, new TvMovieFavorite.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {

        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                tvmovieHelper.open();
                deleted = tvmovieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, new TvMovieFavorite.DataObserver(new Handler(), getContext()));
        return deleted;
    }


}
