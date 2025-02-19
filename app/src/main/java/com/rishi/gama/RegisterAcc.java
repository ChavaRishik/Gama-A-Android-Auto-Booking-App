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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterAcc extends AppCompatActivity {

    private EditText name, phoneNumber, password, confirmPassword;
    private String textEmail;
    private Button createAccount;
    private FirebaseAuth auth;
    private ALoadingDialog loadingDialog;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acc);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        createAccount = findViewById(R.id.signUpBtn);

        loadingDialog = new ALoadingDialog(this);

        // Retrieve the email from the Google sign-in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            textEmail = account.getEmail();
        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textPhone = phoneNumber.getText().toString();
                String textPassword = password.getText().toString();
                String textConfirmPassword = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(textName)) {
                    showToast("Please Enter Your Name");
                } else if (TextUtils.isEmpty(textPhone)) {
                    showToast("Please Enter Your Phone Number");
                } else if (TextUtils.isEmpty(textPassword)) {
                    showToast("Please Enter Your Password");
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    showToast("Please re-enter the password");
                } else if (!textPassword.equals(textConfirmPassword)) {
                    showToast("Password and Confirm Password should be the same");
                } else if (textPhone.length() != 10) {
                    showToast("Please re-enter your Phone number");
                } else {
                    // Show loading dialog
                    loadingDialog.show();

                    registerUser(textName, textPhone, textEmail, textPassword, textConfirmPassword);
                }
            }
        });
    }

    private void registerUser(String textName, String textPhone, String textEmail, String textPassword, String textConfirmPassword) {
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Hide loading dialog
                loadingDialog.dismiss();

                if (task.isSuccessful()) {
                    // Save user information to Firestore
                    saveUserInfoToFirestore(textName, textEmail, textPhone);

                    showToast("Account has been created successfully");
                    Intent intent = new Intent(RegisterAcc.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle specific authentication errors
                    handleRegistrationError(task.getException());
                }
            }
        });
    }

    private void saveUserInfoToFirestore(String textName, String textEmail, String textPhone) {
        // Create a new user document in Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("name", textName);
        user.put("email", textEmail);
        user.put("phone", textPhone);
        user.put("status", "Not Verified"); // Default status

        // Add the user document to the 'users' collection in Firestore
        firestore.collection("usersREG").document(textEmail).set(user)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User document added successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error adding user document", e));
    }

    private void handleRegistrationError(Exception exception) {
        // You can customize this method to handle specific authentication errors
        String errorMessage = "Error during registration: " + exception.getMessage();
        showToast(errorMessage);
        Log.e("RegistrationError", errorMessage);
    }

    private void showToast(String message) {
        Toast.makeText(RegisterAcc.this, message, Toast.LENGTH_LONG).show();
    }
}
