package com.rishi.gama;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView6;
    private TextView nameTextView;
    private TextView emailTextView;
    private ImageView logout;
    private Button contactSupportWhatsAppButton;
    private Button contactSupportCallButton;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView6 = findViewById(R.id.imageView6);
        nameTextView = findViewById(R.id.name);
        emailTextView = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        contactSupportWhatsAppButton = findViewById(R.id.contactSupportWhatsAppButton);
        contactSupportCallButton = findViewById(R.id.contactSupportCallButton);

        // Retrieve user information from the intent or any storage mechanism
        Intent intent = getIntent();
        String displayName = intent.getStringExtra("DISPLAY_NAME");
        String email = intent.getStringExtra("EMAIL");
        String photoUrl = intent.getStringExtra("PHOTO_URL");

        // Display user information
        nameTextView.setText(displayName);
        emailTextView.setText(email);
        Glide.with(this)
                .load(photoUrl)
                .override(188, 185)
                .into(imageView6);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        contactSupportWhatsAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send a WhatsApp message to support
                sendWhatsAppMessage("Hi, I need support!");
            }
        });

        contactSupportCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a phone call to support
                makePhoneCall("tel:6302082842");
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        // Google Sign Out
        googleSignInClient.signOut().addOnCompleteListener(this,
                task -> {
                    // After signing out, navigate back to the login or splash screen
                    Intent intent = new Intent(ProfileActivity.this, SplashScreen.class);
                    startActivity(intent);
                    finish();
                });
    }

    private void sendWhatsAppMessage(String message) {
        String phoneNumber = "+916302082842"; // Phone number in international format

        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void makePhoneCall(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));

        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        }
    }
}
