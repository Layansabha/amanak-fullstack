package com.example.amanakk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class first extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);
        OnboardingUi.bind(
                this,
                R.drawable.secpic,
                "Chat with Experts",
                "Connect with knowledgeable professionals. Get advice and insights through our chat feature for personalized support.",
                1,
                sec.class,
                false
        );
    }
}
