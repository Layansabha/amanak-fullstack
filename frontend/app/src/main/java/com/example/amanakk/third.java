package com.example.amanakk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class third extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);
        OnboardingUi.bind(
                this,
                R.drawable.first,
                "Privacy First",
                "Your identity remains confidential. No one will know who you are while using the app, ensuring your complete privacy.",
                0,
                MainActivity.class,
                true
        );
    }
}
