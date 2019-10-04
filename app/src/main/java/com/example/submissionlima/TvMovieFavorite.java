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
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submissionlima.db.DatabaseTvMovieContract.TvMovieColumns.CONTENT_URI_TV;
import static com.example.submissionlima.TvMappingHelper.mapCursorToArrayTvList;

public class TvMovieFavorite extends AppCompatActivity implements View.OnClickListener, LoadTvMovieCallback {
    private TvMovieAdapter adapter;
    private ArrayList<TvMovie> movies = new ArrayList<>();
    private ProgressBar loading;
    private RecyclerView rvCategory1;
    private TvMovieHelper tvMovieHelper;

    private static HandlerThread handlerThread;
    private DataObserver myObserver;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_movie_favorite);

        rvCategory1 = findViewById(R.id.rv_category1);
        rvCategory1.setLayoutManager(new LinearLayoutManager(this));
        rvCategory1.setHasFixedSize(true);
        loading = findViewById(R.id.progressBar);

        tvMovieHelper = TvMovieHelper.getInstance(getApplicationContext());
        tvMovieHelper.open();

        adapter = new TvMovieAdapter();
        rvCategory1.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadTvMovieAsync(this, this).execute();
        } else {
            ArrayList<TvMovie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movies != null) {
                adapter.setMovies(movies);
            }
        }

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI_TV, true, myObserver);
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
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(Cursor movie) {
        loading.setVisibility(View.GONE);
//        adapter.setMovies(movies);
        ArrayList<TvMovie> movies = mapCursorToArrayTvList(movie);
        if (movies.size() > 0) {
            adapter.setMovies(movies);
        }
        else {
            adapter.setMovies(new ArrayList<TvMovie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvCategory1, message, Snackbar.LENGTH_SHORT).show();
    }


    static class LoadTvMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weaktvMovieHelper;
        private final WeakReference<LoadTvMovieCallback> weakCallback;

        private LoadTvMovieAsync(Context context, LoadTvMovieCallback callback) {
            weaktvMovieHelper = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
//            return weaktvMovieHelper.get().getAllNotes();
            Context context = weaktvMovieHelper.get();
            return context.getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
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
            new LoadTvMovieAsync(context, (LoadTvMovieCallback) context).execute();
        }
    }
}
