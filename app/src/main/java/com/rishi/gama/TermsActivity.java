package com.rishi.gama;
// TermsActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;

public class TermsActivity extends AppCompatActivity {

    private RadioButton termsRadioBtn;
    private Button nextButton;
    private WebView termsWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        termsWebView = findViewById(R.id.termsWebView);
        termsRadioBtn = findViewById(R.id.acceptRadioButton);
        nextButton = findViewById(R.id.nextButton);

        // Enable JavaScript in WebView
        termsWebView.getSettings().setJavaScriptEnabled(true);

        // Load HTML content from the file in assets
        termsWebView.setWebViewClient(new WebViewClient());
        loadHTMLContent();

        termsRadioBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            nextButton.setEnabled(isChecked);
        });
    }

    private void loadHTMLContent() {
        try {
            // Read HTML content from the file
            InputStream inputStream = getAssets().open("terms.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();

            // Load HTML content into WebView
            termsWebView.loadDataWithBaseURL("file:///android_asset/", stringBuilder.toString(), "text/html; charset=utf-8", "UTF-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNextButtonClick(View view) {
        if (termsRadioBtn.isChecked()) {
            Intent intentToTerms = new Intent(TermsActivity.this, AutoBooking.class);
            startActivity(intentToTerms);
            Toast.makeText(this, "Navigating to booking page...", Toast.LENGTH_SHORT).show();
        } else {
            // User didn't accept the terms, show a notification
            Toast.makeText(this, "Please accept the terms to continue.", Toast.LENGTH_SHORT).show();
        }
    }
}
