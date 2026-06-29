package com.example.amanakk;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);  // Make sure this matches your layout file

        // Find the TextView by ID
        TextView termsTextView = findViewById(R.id.termss);

        // Set the text with HTML bullet points from strings.xml
        termsTextView.setText(Html.fromHtml(getString(R.string.termss)));
    }
}
