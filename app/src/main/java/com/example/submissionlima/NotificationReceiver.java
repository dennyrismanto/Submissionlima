package com.example.submissionlima;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ncontext, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) ncontext.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent = new Intent(ncontext, DetailNotificationActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(ncontext,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        @SuppressWarnings("deprecation")
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ncontext)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle("Notification Title")
                .setContentText("Notification Text")
                .setSound(alarmSound)
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());

    }

    public void setAlarm(Context context, String time) {
        Calendar calendar = Calendar.getInstance();
        String timeArray[] = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        Intent calIntent = new Intent(context, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,calIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent calIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,calIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }
}
