package com.rishi.gama;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        Button createAccountBtn = findViewById(R.id.reg);

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show loading dialog
                ALoadingDialog loadingDialog = new ALoadingDialog(MainActivity.this);
                loadingDialog.show();

                // Perform sign-in logic, check credentials
                signInWithEmailAndPassword(loadingDialog);
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoCreateAcc = new Intent(MainActivity.this, GoogleLoginActivity.class);
                startActivity(intenttoCreateAcc);
                finish();
            }
        });
    }

    private void signInWithEmailAndPassword(ALoadingDialog loadingDialog) {
        String email = ((EditText) findViewById(R.id.ed1)).getText().toString();
        String password = ((EditText) findViewById(R.id.ed2)).getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss(); // Dismiss the loading dialog if there's an error
            return;
        }

        // Sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Hide loading dialog
                    loadingDialog.dismiss();

                    if (task.isSuccessful()) {
                        // Sign-in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmailAndPassword: success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmailAndPassword: failure", task.getException());
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(@Nullable FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            // User is signed in with email/password, navigate to homepage
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("EMAIL", firebaseUser.getEmail());
            startActivity(intent);
            finish();
        } else {
            // Handle the case when sign-in is not successful
            Log.e(TAG, "updateUI: User is not signed in");
        }
    }
}
