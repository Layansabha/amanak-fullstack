package com.example.amanakk;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

final class OnboardingUi {
    private OnboardingUi() { }

    static void bind(AppCompatActivity activity, int imageResource, String title, String description,
                     int activeDot, Class<?> nextActivity, boolean completesOnboarding) {
        activity.getWindow().setStatusBarColor(0xFFFFFFFF);
        activity.getWindow().setNavigationBarColor(0xFFFFFFFF);
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        ((ImageView) activity.findViewById(R.id.onboardingImage)).setImageResource(imageResource);
        ((TextView) activity.findViewById(R.id.onboardingTitle)).setText(title);
        ((TextView) activity.findViewById(R.id.onboardingDescription)).setText(description);

        View[] dots = {
                activity.findViewById(R.id.onboardingDotOne),
                activity.findViewById(R.id.onboardingDotTwo),
                activity.findViewById(R.id.onboardingDotThree)
        };
        for (int i = 0; i < dots.length; i++) {
            boolean active = i == activeDot;
            dots[i].setBackgroundResource(active
                    ? R.drawable.onboarding_dot_active
                    : R.drawable.onboarding_dot_inactive);
            android.view.ViewGroup.LayoutParams params = dots[i].getLayoutParams();
            params.height = dp(activity, active ? 16 : 8);
            dots[i].setLayoutParams(params);
        }

        activity.findViewById(R.id.onboardingNextButton).setOnClickListener(v -> {
            if (completesOnboarding) {
                activity.getSharedPreferences("app_prefs", AppCompatActivity.MODE_PRIVATE)
                        .edit().putBoolean("onboarding_completed", true).apply();
            }
            activity.startActivity(new Intent(activity, nextActivity));
            activity.finish();
        });
    }

    private static int dp(AppCompatActivity activity, int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }
}
