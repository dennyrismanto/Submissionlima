package com.example.submissionlima;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this.getApplicationContext());
    }
}
