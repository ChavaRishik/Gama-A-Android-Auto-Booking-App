package com.rishi.gama;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RECEIVE_NOTIFICATIONS extends AppCompatActivity {

    // Unique identifier for the permission request
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the notification permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted, proceed with your layout initialization
            initializeLayout();
        } else {
            // Permission is not granted, request it
            requestNotificationPermission();
        }
    }

    // Initialize your layout after permission is granted
    private void initializeLayout() {
        // Your layout initialization code here
    }

    // Request notification permission
    private void requestNotificationPermission() {
        // Check if the user has denied the permission previously
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)) {
            // Display a rationale for the permission request
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This app needs notification permission to function properly.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Request the permission
                            ActivityCompat.requestPermissions(
                                    RECEIVE_NOTIFICATIONS.this, // Change 'activity_main' to 'RECEIVE_NOTIFICATIONS'
                                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                                    NOTIFICATION_PERMISSION_REQUEST_CODE
                            );
                        }
                    })
                    .create()
                    .show();
        } else {
            // No rationale needed, request the permission directly
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    NOTIFICATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize your layout
                initializeLayout();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}