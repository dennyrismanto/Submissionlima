package com.example.submissionlima;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public final static String KEY_REMINDER_DAILY = "DailyReminder";
    public final static String PREF_NAME = "reminderPreferences";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";

    public NotificationPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setReminderReleaseTime(String time){
        editor.putString(KEY_REMINDER_DAILY,time);
        editor.commit();
    }
    public void setReminderReleaseMessage (String message){
        editor.putString(KEY_REMINDER_MESSAGE_Release,message);
    }
    public void setReminderDailyTime(String time){
        editor.putString(KEY_REMINDER_DAILY,time);
        editor.commit();
    }
    public void setReminderDailyMessage(String message){
        editor.putString(KEY_REMINDER_MESSAGE_Daily,message);
    }
}
