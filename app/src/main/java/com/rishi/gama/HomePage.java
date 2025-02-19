package com.rishi.gama;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
public class HomePage extends AppCompatActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 123; // You can choose any value for this code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Find the ImageButton views
        ImageButton autoImageButton = findViewById(R.id.imageButton3);
        ImageButton bikeImageButton = findViewById(R.id.imageButton4);
        ImageButton ProfileButton = findViewById(R.id.imageButton6);

        Intent intent1 = getIntent();
        String displayName = intent1.getStringExtra("DISPLAY_NAME");
        String email = intent1.getStringExtra("EMAIL");
        String photoUrl = intent1.getStringExtra("PHOTO_URL");

        // Set click listeners for the ImageButtons
        autoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotificationPermissionGranted()) {
                    Intent intenttoterms = new Intent(HomePage.this, TermsActivity.class);
                    intenttoterms.putExtra("DISPLAY_NAME", displayName);
                    intenttoterms.putExtra("EMAIL", email);
                    intenttoterms.putExtra("PHOTO_URL", photoUrl);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    startActivity(intenttoterms);
                } else {
                    requestNotificationPermission();
                }
            }
        });

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotificationPermissionGranted()) {
                    Intent intentToProfile = new Intent(HomePage.this, ProfileActivity.class);
                    intentToProfile.putExtra("DISPLAY_NAME", displayName);
                    intentToProfile.putExtra("EMAIL", email);
                    intentToProfile.putExtra("PHOTO_URL", photoUrl);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    startActivity(intentToProfile);
                } else {
                    requestNotificationPermission();
                }
            }
        });

        bikeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotificationPermissionGranted()) {
                    Intent intenttobikerental = new Intent(HomePage.this, BikeRental.class);

                    intenttobikerental.putExtra("DISPLAY_NAME", displayName);
                    intenttobikerental.putExtra("EMAIL", email);
                    intenttobikerental.putExtra("PHOTO_URL", photoUrl);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    startActivity(intenttobikerental);
                } else {
                    requestNotificationPermission();
                }
            }
        });
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            String phoneNumber = "tel:6302082842";
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));

            if (dialIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(dialIntent);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(HomePage.this, "Phone call permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean isNotificationPermissionGranted() {
        return NotificationManagerCompat.from(this).areNotificationsEnabled();
    }

    private void requestNotificationPermission() {
        new AlertDialog.Builder(this)
                .setTitle("Notification Permission Needed")
                .setMessage("This app needs notification permission to proceed. Please enable notifications in the device settings.")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomePage.this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}