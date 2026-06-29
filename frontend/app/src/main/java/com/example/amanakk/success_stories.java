package com.example.amanakk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class success_stories extends AppCompatActivity {

    private static final String TAG = "SuccessStories";
    private static final String BACKEND_ORIGIN = "http://10.0.2.2:8081";
    private static final String SUCCESS_STORIES_API =
            "http://10.0.2.2:8081/api/v1/success-stories";
    private static final int LOAD_BATCH_SIZE = 6;

    private RecyclerView successStoriesRecyclerView;
    private SuccessStoryAdapter successStoryAdapter;
    private final List<SuccessStory> successStories = new ArrayList<>();
    private final List<SuccessStory> allSuccessStories = new ArrayList<>();
    private ImageView featuredImage;
    private TextView featuredTitle;
    private TextView featuredSubtitle;
    private TextView featuredCategory;
    private Button loadMoreButton;
    private SuccessStory featuredStory;
    private String selectedFilter;
    private int visibleStoryCount = LOAD_BATCH_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successstories);

        setupSystemBars();
        setupFeaturedStory();
        setupBackButton();
        setupRecyclerView();
        setupCategoryChips();
        setupLoadMoreButton();
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

    private void setupFeaturedStory() {
        featuredImage = findViewById(R.id.blogMainImage);
        featuredTitle = findViewById(R.id.featuredTitle);
        featuredSubtitle = findViewById(R.id.featuredSubtitle);
        featuredCategory = findViewById(R.id.featuredCategory);

        findViewById(R.id.featuredStoryCard).setOnClickListener(v -> {
            if (featuredStory != null) {
                openSuccessStoryDetails(featuredStory);
            }
        });
    }

    private void setupBackButton() {
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    private void setupRecyclerView() {
        successStoriesRecyclerView = findViewById(R.id.successStoriesRecyclerView);
        successStoryAdapter = new SuccessStoryAdapter(this, successStories, true);
        successStoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        successStoriesRecyclerView.setAdapter(successStoryAdapter);
    }

    private void setupCategoryChips() {
        bindFilterChip(R.id.chipAll, null);
        bindFilterChip(R.id.chipFraud, "fraud");
        bindFilterChip(R.id.chipExtortion, "extortion");
        bindFilterChip(R.id.chipHarassment, "harassment");
        bindFilterChip(R.id.chipPiracy, "piracy");
        bindFilterChip(R.id.chipDefamation, "defamation");
        bindFilterChip(R.id.chipImpersonation, "impersonation");
    }

    private void bindFilterChip(int chipId, String filter) {
        findViewById(chipId).setOnClickListener(v -> {
            visibleStoryCount = LOAD_BATCH_SIZE;
            Log.d(TAG, "Filter selected: " + (filter == null ? "all" : filter));
            applyFilter(filter);
        });
    }

    private void setupLoadMoreButton() {
        loadMoreButton = findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(v -> {
            visibleStoryCount += LOAD_BATCH_SIZE;
            Log.d(TAG, "Load more selected; visible limit=" + visibleStoryCount);
            applyFilter(selectedFilter);
        });
    }

    private void applyFilter(String filter) {
        selectedFilter = filter;
        List<SuccessStory> filteredStories = new ArrayList<>();
        for (SuccessStory story : allSuccessStories) {
            if (matchesFilter(story, filter)) {
                filteredStories.add(story);
            }
        }

        successStories.clear();
        int visibleCount = Math.min(visibleStoryCount, filteredStories.size());
        if (visibleCount > 0) {
            successStories.addAll(filteredStories.subList(0, visibleCount));
        }
        successStoryAdapter.notifyDataSetChanged();

        loadMoreButton.setEnabled(true);
        loadMoreButton.setAlpha(1f);
        Log.d(TAG, "Filtered adapter item count=" + successStories.size());
    }

    private boolean matchesFilter(SuccessStory story, String filter) {
        if (filter == null) {
            return true;
        }

        String category = story.getCategory() == null ? "" : story.getCategory().toLowerCase(Locale.ROOT);
        String subcategory = story.getSubcategory() == null ? "" : story.getSubcategory().toLowerCase(Locale.ROOT);
        return category.contains(filter) || subcategory.contains(filter);
    }

    private String getLanguage() {
        return Locale.getDefault().getLanguage().equals("ar") ? "ar" : "en";
    }

    private void fetchSuccessStories(String language) {
        Log.d(TAG, "Success Stories API URL=" + SUCCESS_STORIES_API + "?language=" + language);
        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        apiService.getSuccessStories(language).enqueue(new Callback<List<SuccessStory>>() {
            @Override
            public void onResponse(Call<List<SuccessStory>> call, Response<List<SuccessStory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Success stories returned: " + response.body().size());
                    for (SuccessStory story : response.body()) {
                        Log.d(TAG, "Story id=" + story.getId()
                                + ", title=" + story.getTitle()
                                + ", category=" + story.getCategory()
                                + ", imageUrl=" + story.getImageUrl());
                    }
                    allSuccessStories.clear();
                    allSuccessStories.addAll(response.body());
                    visibleStoryCount = LOAD_BATCH_SIZE;
                    if (allSuccessStories.isEmpty()) {
                        findViewById(R.id.featuredStoryCard).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.featuredStoryCard).setVisibility(View.VISIBLE);
                        bindFeaturedStory(allSuccessStories.get(0));
                    }
                    applyFilter(selectedFilter);
                } else {
                    Log.e(TAG, "Failed to retrieve success stories: " + response.code() + " - " + response.message());
                    Toast.makeText(success_stories.this, "Failed to retrieve success stories. Server returned: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SuccessStory>> call, Throwable t) {
                Log.e(TAG, "Network error while fetching success stories", t);
                Toast.makeText(success_stories.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindFeaturedStory(SuccessStory story) {
        featuredStory = story;
        featuredTitle.setText(getTextOrDefault(story.getTitle(), "Success Story"));
        featuredSubtitle.setText(getTextOrDefault(story.getSubtitle(), ""));
        featuredCategory.setText(getDisplayCategory(story));

        String rawImageUrl = story.getImageUrl();
        String finalImageUrl = resolveImageUrl(rawImageUrl);
        Log.d(TAG, "Featured story raw imageUrl=" + rawImageUrl);

        if (finalImageUrl == null) {
            featuredImage.setImageResource(R.drawable.blog_image);
            return;
        }

        Log.d(TAG, "Featured story final imageUrl=" + finalImageUrl);
        Picasso.get()
                .load(finalImageUrl)
                .placeholder(R.drawable.blog_image)
                .error(R.drawable.blog_image)
                .into(featuredImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // No-op. Temporary failure logging is handled below.
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Featured story image load failed: URL=" + finalImageUrl, e);
                    }
                });
    }

    private String getDisplayCategory(SuccessStory story) {
        String category = story.getCategory();
        String subcategory = story.getSubcategory();
        String combined = ((category == null ? "" : category) + " "
                + (subcategory == null ? "" : subcategory)).toLowerCase(Locale.ROOT);
        String[] labels = {"Fraud", "Extortion", "Harassment", "Piracy", "Defamation", "Impersonation"};
        for (String label : labels) {
            if (combined.contains(label.toLowerCase(Locale.ROOT))) {
                return label;
            }
        }
        if (category != null && !category.trim().isEmpty()) {
            return category;
        }
        return getTextOrDefault(subcategory, "Success Story");
    }

    private String resolveImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return null;
        }
        String trimmedUrl = imageUrl.trim();
        if (trimmedUrl.startsWith("/")) {
            return BACKEND_ORIGIN + trimmedUrl;
        }
        if (trimmedUrl.startsWith("http://localhost")) {
            return BACKEND_ORIGIN + trimmedUrl.substring("http://localhost".length());
        }
        if (trimmedUrl.startsWith("http://127.0.0.1")) {
            return BACKEND_ORIGIN + trimmedUrl.substring("http://127.0.0.1".length());
        }
        return trimmedUrl;
    }

    private void openSuccessStoryDetails(SuccessStory story) {
        Intent intent = new Intent(this, SuccessStoryDetailActivity.class);
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_TITLE, story.getTitle());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_CATEGORY, story.getCategory());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_SUBCATEGORY, story.getSubcategory());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_SUBTITLE, story.getSubtitle());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_CONTENT, story.getContent());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_DATE, story.getCreatedAt());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_IMAGE_URL, story.getImageUrl());
        startActivity(intent);
    }

    private String getTextOrDefault(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
