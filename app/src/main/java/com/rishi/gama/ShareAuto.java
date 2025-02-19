package com.rishi.gama;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class ShareAuto extends AppCompatActivity {

    private DatePicker datePicker;
    private Spinner timeSlotSpinner;
    private Button bookButton;
    private Button selectDateButton; // Added for date picker
    private String selectedDate = "";
    private String selectedTimeSlot = "";
    private String name = "";
    private String mobileNumber = "";
    private SharedPreferences sharedPreferences;
    private String[] timeSlots;

    private String price="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_auto);

        EditText mobileNumberEditText = findViewById(R.id.editTextPhone);
        datePicker = findViewById(R.id.datePicker);
        timeSlotSpinner = findViewById(R.id.timeSlotSpinner);
        bookButton = findViewById(R.id.button2);
        selectDateButton = findViewById(R.id.selectDateButton); // Initialize the new button

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve name and mobile number from SharedPreferences
        name = sharedPreferences.getString("name", "");
        mobileNumber = sharedPreferences.getString("mobileNumber", "");
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


            // Get user information
            String name = account.getDisplayName();
            String email = account.getEmail();
            String photoUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : null;

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // Set the minimum date for the DatePicker to today
        datePicker.setMinDate(calendar.getTimeInMillis());

        // Initialize time slots based on the current time
        timeSlots = getTimeSlots(currentHour);
        ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlotSpinner.setAdapter(timeSlotAdapter);

        // Set a listener for the Spinner to capture the selected time slot
        timeSlotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedTimeSlot = timeSlots[position];
                Toast.makeText(ShareAuto.this, "Selected Time Slot: " + selectedTimeSlot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Set a listener for the "Book" button to navigate to the checkout page with selected data
        // Set a listener for the "Book" button to navigate to the checkout page with selected data
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNumber = mobileNumberEditText.getText().toString();

                // Check if any of the required fields are missing
                if (name.isEmpty() || mobileNumber.isEmpty() || selectedDate.isEmpty() || selectedTimeSlot.isEmpty()) {
                    Toast.makeText(ShareAuto.this, "Please fill in all the required fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // All required fields are filled, proceed to the checkout page
                    // Create an Intent to start the CheckoutActivity
                    Intent intentToCheckout = new Intent(ShareAuto.this, CheckoutActivity.class);
                    intentToCheckout.putExtra("name", name);
                    intentToCheckout.putExtra("mobileNumber", mobileNumber);
                    intentToCheckout.putExtra("email", email);
                    intentToCheckout.putExtra("selectedDate", selectedDate);
                    intentToCheckout.putExtra("selectedTimeSlot", selectedTimeSlot);
                    intentToCheckout.putExtra("price", price);

                    // Start the CheckoutActivity
                    startActivity(intentToCheckout);
                }
            }
        });


        // Set a listener for the "Select Date" button to show the date picker
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });
    }

    private String[] getTimeSlots(int currentHour) {
        // Define time slots based on the current time
        String[] timeSlots;
        if (currentHour < 9) {
            timeSlots = new String[]{"9:30AM", "10:30AM", "11:30AM", "12:30AM", "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 10) {
            timeSlots = new String[]{  "10:30AM", "11:30AM", "12:30AM", "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 11) {
            timeSlots = new String[]{ "11:30AM", "12:30AM", "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 12) {
            timeSlots = new String[]{  "12:30AM", "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 13) {
            timeSlots = new String[]{ "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        }else if (currentHour < 14) {
            timeSlots = new String[]{ "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 15) {
            timeSlots = new String[]{"3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 16) {
            timeSlots = new String[]{ "4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 17) {
            timeSlots = new String[]{"5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
        } else if (currentHour < 18) {
            timeSlots = new String[]{ "6PM","6:30PM","7PM","7:30PM"};
        }else if (currentHour < 19) {
            timeSlots = new String[]{"7PM","7:30PM"};
        } else {
            // No available time slots
            timeSlots = new String[]{"No available time slots"};
        }
        return timeSlots;
    }
    private void updateAvailableTimeSlots(int year, int month, int day) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, day);

        if (selectedCalendar.equals(currentCalendar)) {
            ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
            timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSlotSpinner.setAdapter(timeSlotAdapter);
        } else {
            timeSlots = new String[]{"9:30AM", "10:30AM", "11:30AM", "12:30AM", "1:15pm", "2PM", "2:30PM","3PM", "3:30PM","4PM","4:30PM","5PM","5:30PM","6PM","6:30PM","7PM","7:30PM"};
            ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
            timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSlotSpinner.setAdapter(timeSlotAdapter);
        }
    }
    private void updatePrice(int year, int month, int day) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, day);

        if (selectedCalendar.equals(currentCalendar)) {
            price="99";
        } else {
        price="79";
        }
    }



    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                selectDateButton.setText( selectedDate);
                updateAvailableTimeSlots(year, month, dayOfMonth);
                updatePrice(year, month, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }
}
