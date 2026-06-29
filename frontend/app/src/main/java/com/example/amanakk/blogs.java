package com.example.amanakk;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class blogs extends AppCompatActivity {

    private RecyclerView blogsRecyclerView;
    private BlogItemAdapter blogAdapter;
    private List<Post> posts = new ArrayList<>();
    private List<Post> allPosts = new ArrayList<>();
    private String selectedFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blogs); // Ensure the blogs.xml layout file exists

        setupBackButton();
        setupRecyclerView();
        setupCategoryChips();
        fetchLatestPosts(getLanguage(), 4);
    }

    private void setupBackButton() {
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    private void setupRecyclerView() {
        blogsRecyclerView = findViewById(R.id.blogsRecyclerView);
        blogAdapter = new BlogItemAdapter(this, posts);
        blogsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        blogsRecyclerView.setAdapter(blogAdapter);
    }

    private void setupCategoryChips() {
        findViewById(R.id.chipAll).setOnClickListener(v -> applyFilter(null));
        findViewById(R.id.news).setOnClickListener(v -> applyFilter("news"));
        findViewById(R.id.courses).setOnClickListener(v -> applyFilter("courses"));
        findViewById(R.id.Updates).setOnClickListener(v -> applyFilter("updates"));
    }

    private void applyFilter(String filter) {
        selectedFilter = filter;
        posts.clear();
        for (Post post : allPosts) {
            if (matchesFilter(post, filter)) {
                posts.add(post);
            }
        }
        blogAdapter.notifyDataSetChanged();
    }

    private boolean matchesFilter(Post post, String filter) {
        if (filter == null) {
            return true;
        }

        String category = post.getCategory() == null ? "" : post.getCategory().toLowerCase(Locale.ROOT);
        String subcategory = post.getSubcategory() == null ? "" : post.getSubcategory().toLowerCase(Locale.ROOT);
        return category.contains(filter) || subcategory.contains(filter);
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
                    Log.d("blogs", "Posts returned: " + response.body().size());
                    for (Post post : response.body()) {
                        Log.d("blogs", "Post id=" + post.getId()
                                + " raw imageUrl=" + post.getImageUrl());
                    }
                    allPosts.clear();
                    allPosts.addAll(response.body());
                    applyFilter(selectedFilter);
                } else {
                    Log.e("blogs", "Failed to retrieve posts: " + response.code() + " - " + response.message());
                    Toast.makeText(blogs.this, "Failed to retrieve posts. Server returned: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("blogs", "Network error while fetching posts", t);
                Toast.makeText(blogs.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
