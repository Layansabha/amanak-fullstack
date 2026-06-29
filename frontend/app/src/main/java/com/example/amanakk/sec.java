package com.example.amanakk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class sec extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);
        OnboardingUi.bind(
                this,
                R.drawable.aii,
                "Consult AI",
                "Leverage artificial intelligence for quick answers and solutions. Get smart recommendations tailored to your needs.",
                2,
                third.class,
                false
        );
    }
}
