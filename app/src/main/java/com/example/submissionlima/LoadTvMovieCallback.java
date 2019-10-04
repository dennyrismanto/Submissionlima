package com.example.submissionlima;

import android.database.Cursor;

import java.util.ArrayList;

public interface LoadTvMovieCallback {
    void preExecute();
    void postExecute(Cursor movie);
}
