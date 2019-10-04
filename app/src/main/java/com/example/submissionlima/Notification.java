package com.example.submissionlima;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class Notification extends AppCompatActivity {
    public final static int NOTIFICATION_ID = 1001;
    public final static String NOTIFICATION_CHANNEL_ID = "11001";
    private Switch btnNotif, btnReminder;
    public ReminderReceiver reminderReceiver;
    public NotificationReceiver notificationReceiver;
    public NotificationPreference notificationPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;
    public static final String TYPE_NOTIFICATION_PREF = "notificationAlarm";
    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnReminder = findViewById(R.id.switch_reminder);
        btnNotif = findViewById(R.id.switch_notification);
        reminderReceiver = new ReminderReceiver();
        notificationReceiver = new NotificationReceiver();
        notificationPreference = new NotificationPreference(this);
        setPreference();

        btnReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    setAlarmNotification();
                    Toast.makeText(getBaseContext(),"Set Daily Reminder On", Toast.LENGTH_SHORT).show();
                } else {
                    setCancelAlarmNotification();
                    Toast.makeText(getBaseContext(),"Set Daily Reminder Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked2) {
                if (isChecked2 == true){
                    setAlarmReminder();
                } else {
                    setCancelAlarmReminder();
                }
            }
        });

    }

    private void setAlarmNotification(){
        String time = "07:00";
        notificationPreference.setReminderDailyTime(time);
        notificationReceiver.setAlarm(Notification.this, time);
    }
    private void setCancelAlarmNotification(){
        notificationReceiver.cancelAlarm(Notification.this);
    }

    private void setAlarmReminder(){
        String time = "08:00";
        String message = getResources().getString(R.string.release_movie_message);
        notificationPreference.setReminderReleaseTime(time);
        notificationPreference.setReminderReleaseMessage(message);
        reminderReceiver.setAlarm(Notification.this, TYPE_REMINDER_PREF, time, message);
    }

    private void setCancelAlarmReminder(){
        reminderReceiver.cancelAlarm(Notification.this);
    }




    private void setPreference() {
        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
        btnNotif.setChecked(checkReminder);
        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        btnReminder.setChecked(checkDailyReminder);
    }

}
