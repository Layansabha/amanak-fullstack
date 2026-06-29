package com.example.amanakk;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);  // Assuming your terms layout is named terms.xml
        ImageView backButton = findViewById(R.id.imageView20);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }
}
