package com.example.submissionlima;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReminderReceiver extends BroadcastReceiver {
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public final static int NOTIFICATION_ID_ = 101;
    private int index;


    public ArrayList<Movie> listMovie = new ArrayList<>();

    public ReminderReceiver() {
    }
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onReceive(final Context context, final Intent intent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = dateFormat.format(new Date());
        final RequestInterface requestInterface = ReminderClient.Retrofit().create(RequestInterface.class);
        requestInterface.getUpdate(date).enqueue(new Callback<MovieResponse>(){
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                listMovie = response.body().getResults();
                final ArrayList<Movie> items = response.body().getResults();

                int index = new Random().nextInt(items.size());
                Movie item = items.get(index);
                int notifId = 503;
                String title = items.get(index).getTitle();
                String message = items.get(index).getOverview();
                sendNotification(context, title, message, notifId);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("getUpCommingMovie", "onFailure: " + t.toString());
            }
        });
    }
    public void setMovies(ArrayList<Movie>listMovie){
        this.listMovie.addAll(listMovie);
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        @SuppressWarnings("deprecation")
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(EXTRA_TYPE_RECIEVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

    }
}
