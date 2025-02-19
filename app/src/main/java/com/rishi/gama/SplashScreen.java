package com.rishi.gama;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {

    private ImageView gView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();
        gView = findViewById(R.id.gView);

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(gView, "rotation", 0, 360);
        rotateAnimator.setDuration(200); // Faster rotation duration (adjust as needed)
        rotateAnimator.setRepeatCount(4);
        rotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                new android.os.Handler().postDelayed(() -> {
                    decideNextActivity();
                }, 250); // Display the rotated image for 1 second
            }
        });

        rotateAnimator.start();

        // Initialize Firebase
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    private void decideNextActivity() {
        if (isUserSignedIn()) {
            navigateToHomePage();
        } else {
            navigateToMainActivity();
        }
    }

    private boolean isUserSignedIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }

    private void navigateToHomePage() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Get user information
            String email = currentUser.getEmail();
            String welcomeMessage = "Welcome back, " + email + "!";
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(this, HomePage.class);
            intent1.putExtra("EMAIL", email);
            startActivity(intent1);
        } else {
            navigateToMainActivity();
        }

        // Finish the SplashScreen activity
        finish();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
