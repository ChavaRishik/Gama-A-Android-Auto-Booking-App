package com.rishi.gama;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AutoBooking extends AppCompatActivity {

    private Button shareAutoButton;
    private Button completeAutoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_booking);

        shareAutoButton = findViewById(R.id.shareAutoBtn);
        completeAutoButton = findViewById(R.id.completeAutoBtn);

        // Set click listeners for the buttons
        shareAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteActivity();
            }
        });

        completeAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompleteAutoActivity();
            }
        });
    }

    private void openNoteActivity() {
        // Create an intent to open the Note.java activity
        Intent intent = new Intent(AutoBooking.this, ShareAuto.class);
        startActivity(intent);
    }

    private void openCompleteAutoActivity() {
        // Create an intent to open the CompleteAuto.java activity
        Intent intent = new Intent(AutoBooking.this, AutoType.class);
        startActivity(intent);
    }
}
