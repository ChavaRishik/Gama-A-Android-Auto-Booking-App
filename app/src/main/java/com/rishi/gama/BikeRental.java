package com.rishi.gama;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.appcompat.app.AppCompatActivity;

public class BikeRental extends AppCompatActivity {

    private Spinner fromLocationSpinner;
    private Spinner toLocationSpinner;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_rental);

        fromLocationSpinner = findViewById(R.id.fromLocationSpinner);
        toLocationSpinner = findViewById(R.id.toLocationSpinner);
        submitButton = findViewById(R.id.submitButton);


        Intent intentToBikeRental = getIntent();
        String displayName = intentToBikeRental.getStringExtra("DISPLAY_NAME");
        String email = intentToBikeRental.getStringExtra("EMAIL");
        String photoUrl = intentToBikeRental.getStringExtra("PHOTO_URL");

        // Set up the spinners with location options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.location_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLocationSpinner.setAdapter(adapter);
        toLocationSpinner.setAdapter(adapter);

        // Set up item selected listeners to enforce the constraint
        fromLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                checkLocations();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        toLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                checkLocations();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected "From" and "To" locations
                String fromLocation = fromLocationSpinner.getSelectedItem().toString();
                String toLocation = toLocationSpinner.getSelectedItem().toString();

                // Check if the selected locations are the same
                if (fromLocation.equals(toLocation)) {
                    // Handle the case where the locations are the same (e.g., display an error message)
                } else {
                    // Compose the WhatsApp message
                    String message = "Booking Details:\nFrom: " + fromLocation + "\nTo: " + toLocation + "\nLooking for confirmation";

                    // Send the WhatsApp message
                    sendWhatsAppMessage(message);
                }
            }
        });
    }

    private void checkLocations() {
        // Get the selected "From" and "To" locations
        String fromLocation = fromLocationSpinner.getSelectedItem().toString();
        String toLocation = toLocationSpinner.getSelectedItem().toString();

        // If the selected locations are the same, prevent submission
        if (fromLocation.equals(toLocation)) {
            submitButton.setEnabled(false);
            // You can also display an error message here if needed
        } else {
            submitButton.setEnabled(true);
        }
    }

    private void sendWhatsAppMessage(String message) {
        String phoneNumber = "+916302082842"; // Phone number in international format

        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
