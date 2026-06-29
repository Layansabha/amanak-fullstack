package com.example.amanakk;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private RecyclerView blogsRecyclerView, successStoriesRecyclerView;
    private BlogAdapter blogAdapter;
    private SuccessStoryAdapter successStoryAdapter;
    private List<Post> posts = new ArrayList<>();
    private List<SuccessStory> successStories = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ImageView imageView5, imageView55, aiBanner;
    private Button reportButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Log.d(TAG, "Home screen loaded");
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(0x66000000);
        setupSystemBars();
        setupDrawer();
        setupHeaderActions();
        setupRecyclerViews();
        setupImageView5();
        setupImageView55(); // Setup for imageView55
        setupAiBanner();
        setupReportButton();
        setupLawsSection();
        setupBottomNavigationView();

        fetchLatestPosts(getLanguage(), 2);
        fetchSuccessStories(getLanguage());
    }

    private void setupSystemBars() {
        getWindow().setStatusBarColor(Color.parseColor("#EEF0F2"));
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );
    }

    private void setupHeaderActions() {
        ImageView menuButton = findViewById(R.id.homeMenuButton);
        ImageView notificationButton = findViewById(R.id.homeNotificationButton);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        notificationButton.setOnClickListener(v -> Log.d(TAG, "Home notifications clicked"));
    }

    private void setupDrawer() {
        findViewById(R.id.drawerBackButton).setOnClickListener(v ->
                drawerLayout.closeDrawer(GravityCompat.START));
        findViewById(R.id.drawerNotificationButton).setOnClickListener(v ->
                Log.d(TAG, "Clicked menu item: Notifications; coming soon"));

        bindDrawerDestination(R.id.drawerProfile, "My Profile", profile.class);
        bindDrawerDestination(R.id.drawerReports, "My Reports", reportsss.class);
        bindDrawerDestination(R.id.drawerBlogs, "Blogs", blogs.class);
        bindDrawerDestination(R.id.drawerSuccessStories, "Success Stories", success_stories.class);
        bindDrawerDestination(R.id.drawerChat, "Chat", InboxActivity.class);
        bindDrawerDestination(R.id.drawerSettings, "Settings", SettingsActivity.class);
        bindDrawerDestination(R.id.drawerAbout, "About", AboutActivity.class);
        bindDrawerDestination(R.id.drawerPrivacy, "Privacy Policy", PrivacyActivity.class);
        bindDrawerDestination(R.id.drawerTerms, "Terms and Conditions", TermsActivity.class);

        findViewById(R.id.drawerLaws).setOnClickListener(v -> openHomeLawsFromDrawer());
        findViewById(R.id.drawerArabic).setOnClickListener(v -> {
            Log.d(TAG, "Clicked menu item: عربي; coming soon");
            Toast.makeText(this, "Language switching is coming soon.", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.drawerLogoutButton).setOnClickListener(v -> logoutFromDrawer());

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(TAG, "Drawer opened");
                getWindow().setStatusBarColor(Color.WHITE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG, "Drawer closed");
                getWindow().setStatusBarColor(Color.parseColor("#EEF0F2"));
            }
        });
    }

    private void bindDrawerDestination(int rowId, String label, Class<?> destination) {
        findViewById(rowId).setOnClickListener(v -> {
            Log.d(TAG, "Clicked menu item: " + label);
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.postDelayed(() -> {
                startActivity(new Intent(this, destination));
                overridePendingTransition(0, 0);
                Log.d(TAG, "Destination opened: " + label);
            }, 180);
        });
    }

    private void openHomeLawsFromDrawer() {
        Log.d(TAG, "Clicked menu item: Laws");
        drawerLayout.closeDrawer(GravityCompat.START);
        ScrollView homeScrollView = findViewById(R.id.homeScrollView);
        TextView lawsLabel = findViewById(R.id.lawsLabel);
        homeScrollView.postDelayed(() -> {
            Rect lawsBounds = new Rect();
            lawsLabel.getDrawingRect(lawsBounds);
            homeScrollView.offsetDescendantRectToMyCoords(lawsLabel, lawsBounds);
            int topPadding = Math.round(16 * getResources().getDisplayMetrics().density);
            homeScrollView.smoothScrollTo(0, Math.max(0, lawsBounds.top - topPadding));
            Log.d(TAG, "Destination opened: Home Laws section");
        }, 220);
    }

    private void logoutFromDrawer() {
        Log.d(TAG, "Clicked menu item: Logout");
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putBoolean("isLoggedIn", false)
                .apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Log.d(TAG, "Destination opened: Login after logout");
        finish();
    }

    private void setupRecyclerViews() {
        // Setup Blogs RecyclerView
        blogsRecyclerView = findViewById(R.id.blogsRecyclerView);
        if (blogsRecyclerView != null) {
            blogAdapter = new BlogAdapter(this, posts);
            blogsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            blogsRecyclerView.setAdapter(blogAdapter);
            blogsRecyclerView.setHasFixedSize(true);
        }

        // Setup Success Stories RecyclerView (Horizontal Layout)
        successStoriesRecyclerView = findViewById(R.id.successStoriesRecyclerView);
        if (successStoriesRecyclerView != null) {
            successStoryAdapter = new SuccessStoryAdapter(this, successStories);
            successStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            successStoriesRecyclerView.setAdapter(successStoryAdapter);
            successStoriesRecyclerView.setHasFixedSize(true);
        }
    }

    private void setupImageView5() {
        imageView5 = findViewById(R.id.imageView5);
        if (imageView5 != null) {
            imageView5.setOnClickListener(v -> startActivityWithAnimation(blogs.class));
        }
    }

    private void setupImageView55() {
        imageView55 = findViewById(R.id.imageView55);
        if (imageView55 != null) {
            imageView55.setOnClickListener(v -> startActivityWithAnimation(success_stories.class));
        }
    }

    private void setupReportButton() {
        reportButton = findViewById(R.id.reportButton);
        if (reportButton != null) {
            reportButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            reportButton.setTextColor(Color.parseColor("#385D94"));
            reportButton.setOnClickListener(v -> BottomNavUtils.navigateToTopLevel(this, reports.class));
        }
    }

    private void setupAiBanner() {
        aiBanner = findViewById(R.id.imageView3);
        if (aiBanner != null) {
            aiBanner.setOnClickListener(v -> startActivityWithAnimation(ai.class));
        }
    }

    private void setupLawsSection() {
        setupLawAccordion(
                R.id.lawCardOne,
                R.id.lawAnswerOne,
                R.id.lawIconOne,
                "Who are the people or establishments who commit insurance fraud?"
        );
        setupLawAccordion(
                R.id.lawCardTwo,
                R.id.lawAnswerTwo,
                R.id.lawIconTwo,
                "What are we doing to prevent fraud?"
        );
        setupLawAccordion(
                R.id.lawCardThree,
                R.id.lawAnswerThree,
                R.id.lawIconThree,
                "How can i prevent/avoid fraud?"
        );

        View.OnClickListener discoverLawsListener = v -> {
            Log.d(TAG, "Discover All Laws clicked");
            Toast.makeText(this, "More laws will be added soon.", Toast.LENGTH_SHORT).show();
        };
        Button discoverLawsButton = findViewById(R.id.discoverLawsButton);
        discoverLawsButton.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#3F6DA8"))
        );
        discoverLawsButton.setOnClickListener(discoverLawsListener);
        findViewById(R.id.lawsArrow).setOnClickListener(discoverLawsListener);
    }

    private void setupLawAccordion(int cardId, int answerId, int iconId, String title) {
        View card = findViewById(cardId);
        TextView answer = findViewById(answerId);
        ImageView icon = findViewById(iconId);

        card.setOnClickListener(v -> {
            boolean expand = answer.getVisibility() != View.VISIBLE;
            answer.setVisibility(expand ? View.VISIBLE : View.GONE);
            icon.setImageResource(expand ? R.drawable.minus1 : R.drawable.plus1);
            icon.setContentDescription(expand ? "Collapse law" : "Expand law");
            Log.d(TAG, "Laws accordion clicked: " + title + ", expanded=" + expand);
        });
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavUtils.setup(bottomNavigationView, this, R.id.nav_home);
    }

    private void startActivityWithAnimation(Class<?> activityClass) {
        BottomNavUtils.navigateTo(this, activityClass);
    }

    private String getLanguage() {
        return Locale.getDefault().getLanguage().equals("ar") ? "ar" : "en";
    }

    private void fetchLatestPosts(String language, int limit) {
        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        apiService.getPosts(language, limit).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Blogs count: " + response.body().size());
                    for (Post post : response.body()) {
                        Log.d(TAG, "Post id=" + post.getId()
                                + " raw imageUrl=" + post.getImageUrl());
                    }
                    posts.clear();
                    posts.addAll(response.body());
                    blogAdapter.notifyDataSetChanged();
                } else {
                    Log.e("HomeActivity", "Failed to retrieve posts: " + response.code() + " - " + response.message());
                    Toast.makeText(HomeActivity.this, "Failed to retrieve posts. Server returned: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("HomeActivity", "Network error while fetching posts", t);
                Toast.makeText(HomeActivity.this, "Network error while loading posts: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchSuccessStories(String language) {
        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        apiService.getSuccessStories(language).enqueue(new Callback<List<SuccessStory>>() {
            @Override
            public void onResponse(Call<List<SuccessStory>> call, Response<List<SuccessStory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Success stories count: " + response.body().size());
                    for (SuccessStory story : response.body()) {
                        Log.d(TAG, "Success story id=" + story.getId()
                                + ", title=" + story.getTitle()
                                + ", category=" + story.getCategory()
                                + ", raw imageUrl=" + story.getImageUrl());
                    }
                    successStories.clear();
                    successStories.addAll(response.body());
                    successStoryAdapter.notifyDataSetChanged();
                } else {
                    Log.e("HomeActivity", "Failed to retrieve success stories: HTTP " + response.code() + " - " + response.message());
                    Toast.makeText(HomeActivity.this, "Error: Failed to retrieve success stories. Server returned: " + response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SuccessStory>> call, Throwable t) {
                Log.e("HomeActivity", "Network error while fetching success stories", t);
                Toast.makeText(HomeActivity.this, "Network error while loading success stories: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}
