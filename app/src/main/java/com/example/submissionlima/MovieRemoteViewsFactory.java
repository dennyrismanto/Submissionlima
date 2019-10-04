package com.example.submissionlima;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submissionlima.db.DatabaseMovieContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.POSTERPATH;

//import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.CONTENT_URI;
//import static com.example.submissionlima.db.DatabaseMovieContract.NoteColumns.POSTERPATH;

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> widgetItems = new ArrayList<>();
    private Context mContext;
    int mAppWidgetId;
    private Cursor cursor;
    private String movieFavorite;

//    MovieRemoteViewsFactory(Context context) {
//        mContext = context;
//    }
    public MovieRemoteViewsFactory(Context applicationContext){
        mContext = applicationContext;
//        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }



    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
//        if (cursor != null){
//            cursor.close();
//        }
        widgetItems.clear();
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query( DatabaseMovieContract.NoteColumns.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        if (cursor.moveToPosition(position)){
            movieFavorite = cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH));
            String urlImage = "https://image.tmdb.org/t/p/w500/";
            Bitmap bmp;
            try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(urlImage+movieFavorite)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageWidgetView, bmp);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
            }
        }
        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageWidgetView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
//        return cursor.moveToNext() ? cursor.getLong(0):position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
