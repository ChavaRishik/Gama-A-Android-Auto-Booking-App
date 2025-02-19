package com.rishi.gama;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AutoType extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_type);

        Button button499 = findViewById(R.id.button499);
        Intent intent = getIntent();
        String displayName = intent.getStringExtra("DISPLAY_NAME");
        String email = intent.getStringExtra("EMAIL");
        String photoUrl = intent.getStringExtra("PHOTO_URL");

        button499.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCompleteAuto= new Intent(AutoType.this, CompleteAuto.class);

                // Include name, email, and photoUrl as extras
                intentToCompleteAuto.putExtra("DISPLAY_NAME", displayName);
                intentToCompleteAuto.putExtra("EMAIL", email);
                intentToCompleteAuto.putExtra("PHOTO_URL", photoUrl);
                intentToCompleteAuto.putExtra("price", "399");
                startActivity(intentToCompleteAuto);
            }
        });

        // Find the "649" button
        Button button649 = findViewById(R.id.button649);

        // Set a click listener for the "649" button
        button649.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCompleteAuto= new Intent(AutoType.this, CompleteAuto.class);
                intentToCompleteAuto.putExtra("DISPLAY_NAME", displayName);
                intentToCompleteAuto.putExtra("EMAIL", email);
                intentToCompleteAuto.putExtra("PHOTO_URL", photoUrl);
                intentToCompleteAuto.putExtra("price", "549");
                startActivity(intentToCompleteAuto);
            }
        });
    }
}
