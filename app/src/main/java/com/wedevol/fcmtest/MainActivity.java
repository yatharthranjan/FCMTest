package com.wedevol.fcmtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;


/**
 * Main Activity for FCM Token
 */
public class MainActivity extends AppCompatActivity implements IRequestListener {

    private static final String TAG = "MainActivity";
    public static final String FCM_PROJECT_SENDER_ID = "1043784930865";
    public static final String FCM_SERVER_CONNECTION = "@gcm.googleapis.com";
    public static final String BACKEND_ACTION_MESSAGE = "MESSAGE";
    public static final String BACKEND_ACTION_ECHO = "ECHO";
    public static final String BACKEND_ACTION_SCHEDULE = "SCHEDULE";
    public static final String BACKEND_ACTION_CANCEL = "CANCEL";

    public static final Random RANDOM = new Random();

    private EditText editTextEcho;
    private TextView deviceText;
    private Button buttonUpstreamEcho;
    private TokenService tokenService;

    private EditText scheduleMessage;
    private EditText scheduleTimes;
    private EditText scheduleInterval;
    private Button buttonUpstreamSchedule;
    private  Button buttonUpstreamCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "FCM Token creation logic");

        Intent intent = getIntent();
        String scheduledTime = intent.getStringExtra("scheduledTime");
        String title = intent.getStringExtra("title");

        if(title != null && scheduledTime != null) {
            Toast.makeText(this, "Notification with title = " + title + "Was scheduled at : " + scheduledTime, Toast.LENGTH_LONG).show();
        }


        // Get variables reference
        deviceText = (TextView) findViewById(R.id.deviceText);
        editTextEcho = (EditText) findViewById(R.id.editTextEcho);
        buttonUpstreamEcho = (Button) findViewById(R.id.buttonUpstreamEcho);

        scheduleMessage = (EditText) findViewById(R.id.editTextSchedule);
        scheduleTimes = (EditText) findViewById(R.id.editTextScheduleTimes);
        scheduleInterval = (EditText) findViewById(R.id.editTextScheduleInterval);
        buttonUpstreamSchedule = (Button) findViewById(R.id.buttonUpstreamSchedule);
        buttonUpstreamCancel = (Button) findViewById(R.id.buttonUpstreamCancel);

        //Get token from Firebase
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
        deviceText.setText(token);

        //Call the token service to save the token in the database
        tokenService = new TokenService(this, this);
        if( token != null)
            tokenService.registerTokenInDB(token);
        else
            Log.e(TAG, "Token is null");

        buttonUpstreamEcho.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Echo Upstream message logic");
                String message = editTextEcho.getText().toString();
                Log.d(TAG, "Message: " + message + ", recipient: " + token);
                FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(FCM_PROJECT_SENDER_ID + FCM_SERVER_CONNECTION)
                        .setMessageId(Integer.toString(RANDOM.nextInt()))
                        .addData("message", message)
                        .addData("action", BACKEND_ACTION_ECHO)
                        .build());
                // To send a message to other device through the XMPP Server, you should add the
                // receiverId and change the action name to BACKEND_ACTION_MESSAGE in the data
            }
        });

        buttonUpstreamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Schedule Upstream message logic");
                Log.d(TAG, "Message: " + scheduleMessage.getText() + ", times: " + scheduleTimes.getText() + ", interval: " + scheduleInterval.getText() + ", recipient: " + token);

                String[] params = {
                        scheduleMessage.getText().toString(),
                        scheduleTimes.getText().toString(),
                        scheduleInterval.getText().toString()
                };

                new FcmScheduleTask().execute(params);
            }
        });

        buttonUpstreamCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Cancel Upstream message logic");
                String message = editTextEcho.getText().toString();
                Log.d(TAG, "Cancel for token: " + token);
                FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(FCM_PROJECT_SENDER_ID + FCM_SERVER_CONNECTION)
                        .setMessageId(Integer.toString(RANDOM.nextInt()))
                        .addData("cancelType", "all")
                        .addData("action", BACKEND_ACTION_CANCEL)
                        .build());
            }
        });
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "Token registered successfully in the DB");

    }

    @Override
    public void onError(String message) {
        Log.d(TAG, "Error trying to register the token in the DB: " + message);
    }
}
