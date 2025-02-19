package com.rishi.gama;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Handle the incoming FCM message here
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // You can process the message data here and take appropriate actions
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message notification: " + remoteMessage.getNotification().getBody());

            // You can display a notification to the user with the notification data here
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // If you want to handle the FCM token when it's refreshed, you can do it here
        Log.d(TAG, "Refreshed token: " + token);

        // You can send the new token to your server or update it in your app as needed.
    }
}