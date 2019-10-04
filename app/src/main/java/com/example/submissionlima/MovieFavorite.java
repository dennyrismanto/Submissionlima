package com.example.submissionlima;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.CONTENT_URI;
import static com.example.submissionlima.MappingHelper.mapCursorToArrayList;

public class MovieFavorite extends AppCompatActivity implements View.OnClickListener, LoadMovieCallback{

    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar loading;
    private RecyclerView rvCategory;
    private MovieHelper movieHelper;

    private static HandlerThread handlerThread;
    private DataObserver myObserver;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorite);

        rvCategory = findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setHasFixedSize(true);
        loading = findViewById(R.id.progressBar);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        adapter = new MovieAdapter();
        rvCategory.setAdapter(adapter);

//        if (savedInstanceState == null) {
//            new LoadMovieAsync(movieHelper, this).execute();
//        } else {
//            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
//            if (movies != null) {
//                adapter.setMovies(movies);
//            }
//        }

        if (savedInstanceState == null) {
            new LoadMovieAsync(this, this).execute();
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movies != null) {
                adapter.setMovies(movies);

            }
        }

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovies());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(Cursor movie) {
        loading.setVisibility(View.GONE);
//        adapter.setMovies(movies);
        ArrayList<Movie> movies = mapCursorToArrayList(movie);
        if (movies.size() > 0) {
            adapter.setMovies(movies);
            Log.d("Favorite", movies.get(0).getOriginalTitle());
        }
        else {
            adapter.setMovies(new ArrayList<Movie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvCategory, message, Snackbar.LENGTH_SHORT).show();
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakMovieHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMovieCallback callback) {
            weakMovieHelper = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
//            return weakMovieHelper.get().getAllNotes();
            Context context = weakMovieHelper.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
//            Context context = weakMovieHelper.get();
//            Cursor dataCursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
//            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);
            weakCallback.get().postExecute(movie);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsync(context, (LoadMovieCallback) context).execute();
        }
    }
}
