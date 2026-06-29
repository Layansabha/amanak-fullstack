package com.example.amanakk;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public final class BottomNavUtils {
    private BottomNavUtils() {
    }

    public static void setup(BottomNavigationView bottomNavigationView, AppCompatActivity activity, int currentItemId) {
        if (bottomNavigationView == null) {
            return;
        }

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        bottomNavigationView.setItemRippleColor(ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
        bottomNavigationView.setItemBackgroundResource(android.R.color.transparent);
        if (bottomNavigationView.getMenu().findItem(currentItemId) != null) {
            bottomNavigationView.getMenu().findItem(currentItemId).setChecked(true);
        }
        bottomNavigationView.post(() -> resizeNavigationIcons(bottomNavigationView, currentItemId));
        bottomNavigationView.setOnItemReselectedListener(item -> { });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == currentItemId) {
                return true;
            }

            if (itemId == R.id.nav_home) {
                bottomNavigationView.getMenu().setGroupEnabled(0, false);
                navigateToTopLevel(activity, HomeActivity.class);
                return true;
            } else if (itemId == R.id.message) {
                bottomNavigationView.getMenu().setGroupEnabled(0, false);
                navigateToTopLevel(activity, InboxActivity.class);
                return true;
            } else if (itemId == R.id.reporttt) {
                bottomNavigationView.getMenu().setGroupEnabled(0, false);
                navigateToTopLevel(activity, reports.class);
                return true;
            } else if (itemId == R.id.reportss) {
                bottomNavigationView.getMenu().setGroupEnabled(0, false);
                navigateToTopLevel(activity, reportsss.class);
                return true;
            } else if (itemId == R.id.profile) {
                bottomNavigationView.getMenu().setGroupEnabled(0, false);
                navigateToTopLevel(activity, profile.class);
                return true;
            }
            return false;
        });
    }

    private static void resizeNavigationIcons(BottomNavigationView navigationView, int currentItemId) {
        int[] itemIds = {R.id.nav_home, R.id.message, R.id.reporttt, R.id.reportss, R.id.profile};
        int iconViewId = navigationView.getResources().getIdentifier(
                "navigation_bar_item_icon_view", "id", navigationView.getContext().getPackageName());
        int iconContainerId = navigationView.getResources().getIdentifier(
                "navigation_bar_item_icon_container", "id", navigationView.getContext().getPackageName());
        int activeIndicatorId = navigationView.getResources().getIdentifier(
                "navigation_bar_item_active_indicator_view", "id", navigationView.getContext().getPackageName());
        for (int itemId : itemIds) {
            View itemView = navigationView.findViewById(itemId);
            if (itemView == null) continue;
            itemView.setBackground(null);
            ImageView icon = iconViewId == 0 ? null : itemView.findViewById(iconViewId);
            View iconContainer = iconContainerId == 0 ? null : itemView.findViewById(iconContainerId);
            View activeIndicator = activeIndicatorId == 0 ? null : itemView.findViewById(activeIndicatorId);
            if (activeIndicator != null) activeIndicator.setVisibility(View.GONE);
            boolean largeSubmitButton = itemId == R.id.reporttt && currentItemId == R.id.reporttt;
            int iconSize = dp(navigationView, largeSubmitButton ? 52 : 26);
            int containerSize = dp(navigationView, largeSubmitButton ? 58 : 34);
            if (icon != null) {
                ViewGroup.LayoutParams params = icon.getLayoutParams();
                params.width = iconSize;
                params.height = iconSize;
                icon.setLayoutParams(params);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            if (iconContainer != null) {
                iconContainer.setBackground(null);
                ViewGroup.LayoutParams params = iconContainer.getLayoutParams();
                params.width = containerSize;
                params.height = containerSize;
                iconContainer.setLayoutParams(params);
            }
        }
    }

    private static int dp(View view, int value) {
        return Math.round(value * view.getResources().getDisplayMetrics().density);
    }

    public static void navigateToTopLevel(AppCompatActivity activity, Class<?> targetActivity) {
        if (activity.getClass().equals(targetActivity)) {
            return;
        }

        Intent intent = new Intent(activity, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }

    public static void navigateTo(AppCompatActivity activity, Class<?> targetActivity) {
        if (activity.getClass().equals(targetActivity)) {
            return;
        }

        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
