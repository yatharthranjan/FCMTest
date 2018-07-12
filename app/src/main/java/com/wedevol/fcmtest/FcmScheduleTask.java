package com.wedevol.fcmtest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.UUID;

import static com.wedevol.fcmtest.MainActivity.BACKEND_ACTION_SCHEDULE;
import static com.wedevol.fcmtest.MainActivity.FCM_PROJECT_SENDER_ID;
import static com.wedevol.fcmtest.MainActivity.FCM_SERVER_CONNECTION;

public class FcmScheduleTask extends AsyncTask<String, String, String>{
    @Override
    protected String doInBackground(String[] params) {
        int interval = Integer.parseInt(params[2]);
        int times = Integer.parseInt(params[1]);

        long now = System.currentTimeMillis();

        try {
            for (int i = 1; i <= times; i++) {
                Thread.sleep(6000);

                FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(FCM_PROJECT_SENDER_ID + FCM_SERVER_CONNECTION)
                        .setMessageId(UUID.randomUUID().toString())
                        .addData("notificationMessage", params[0])
                        .addData("notificationTitle", "Schedule id " + i)
                        .addData("time", String.valueOf(now + (i * interval)))
                        .addData("action", BACKEND_ACTION_SCHEDULE)
                        .build());
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return "Success";
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);

        Log.i("Schedule Task", "Scheduling completed" + ", Result:" + o);
    }
}
