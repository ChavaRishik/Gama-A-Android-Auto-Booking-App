package com.rishi.gama;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleLoginActivity";
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        // Configure Google Sign-In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up the UI
        setupUI();
    }

    private void setupUI() {
        SignInButton signInButton = findViewById(R.id.googleSignInBtn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, retrieve user information
            String displayName = account.getDisplayName();
            String email = account.getEmail();
            String photoUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : null;

            // Log the user information
            Log.d(TAG, "User Display Name: " + displayName);
            Log.d(TAG, "User Email: " + email);
            Log.d(TAG, "User Photo URL: " + photoUrl);

            updateUI(account);
        } catch (ApiException e) {
            // Handle sign-in failure
            showToast("signInResult:failed code=" + e.getStatusCode());
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            showToast("Welcome, " + account.getDisplayName() + "!");
            navigateToHomePage(account.getEmail()); // Pass the user email to the method
        } else {
            // Customize the behavior for a signed-out or failed sign-in user
        }
    }

    private void navigateToHomePage(String userEmail) {

        // Customize the navigation behavior to your Home Page
        Intent intent = new Intent(this, RegisterAcc.class);
        intent.putExtra("user_email", userEmail); // Pass the user email as an intent extra
        startActivity(intent);
        finish(); // Optional: Close the current activity to prevent the user from going back to the sign-in screen
    }

    private void signOutFromGoogle() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    // After signing out, you can perform any additional actions if needed
                    Log.d(TAG, "Google Sign Out: success");
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

