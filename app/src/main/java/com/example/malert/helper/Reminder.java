package com.example.malert.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class Reminder extends BroadcastReceiver {

    private static final String TAG = Reminder.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        Log.d(TAG, "Received intent with name: " + name);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(name);
        notificationHelper.getManager().notify(1, nb.build());
    }
}