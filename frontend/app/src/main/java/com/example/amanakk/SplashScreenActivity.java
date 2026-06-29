package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000L;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable openNextScreen = this::navigateAfterSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        getWindow().setStatusBarColor(0xFFFFFFFF);
        getWindow().setNavigationBarColor(0xFFFFFFFF);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        View splashRoot = findViewById(R.id.splashRoot);
        splashRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                splashRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                handler.postDelayed(openNextScreen, SPLASH_TIME_OUT);
                return true;
            }
        });
    }

    private void navigateAfterSplash() {
        boolean isLoggedIn = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);

        boolean onboardingCompleted = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getBoolean("onboarding_completed", false);
        Class<?> destination;
        if (isLoggedIn) {
            destination = HomeActivity.class;
        } else if (onboardingCompleted) {
            destination = MainActivity.class;
        } else {
            destination = first.class;
        }
        startActivity(new Intent(this, destination));
        finish();
    }

    @Override protected void onDestroy() {
        handler.removeCallbacks(openNextScreen);
        super.onDestroy();
    }
}
