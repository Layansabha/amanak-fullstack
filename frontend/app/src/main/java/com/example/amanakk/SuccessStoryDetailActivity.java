package com.example.amanakk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SuccessStoryDetailActivity extends AppCompatActivity {
    private static final String TAG = "SuccessStoryDetail";
    private static final String BACKEND_ORIGIN = "http://10.0.2.2:8081";

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_SUBCATEGORY = "subcategory";
    public static final String EXTRA_SUBTITLE = "subtitle";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_IMAGE_URL = "imageUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        ImageView backButton = findViewById(R.id.backButton);
        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailCategory = findViewById(R.id.detailCategory);
        TextView detailDate = findViewById(R.id.detailDate);
        TextView detailSubtitle = findViewById(R.id.detailSubtitle);
        TextView detailContent = findViewById(R.id.detailContent);

        backButton.setOnClickListener(v -> finish());
        detailTitle.setText(getTextOrDefault(getIntent().getStringExtra(EXTRA_TITLE), "Success Story"));
        detailCategory.setText(getCategoryLabel());
        detailDate.setText(getTextOrDefault(getIntent().getStringExtra(EXTRA_DATE), ""));

        String subtitle = getIntent().getStringExtra(EXTRA_SUBTITLE);
        if (subtitle == null || subtitle.trim().isEmpty()) {
            detailSubtitle.setVisibility(View.GONE);
        } else {
            detailSubtitle.setText(subtitle);
        }

        detailContent.setText(getTextOrDefault(getIntent().getStringExtra(EXTRA_CONTENT), ""));
        setImageUrl(detailImage, getIntent().getStringExtra(EXTRA_IMAGE_URL));
    }

    private String getCategoryLabel() {
        String category = getIntent().getStringExtra(EXTRA_CATEGORY);
        String subcategory = getIntent().getStringExtra(EXTRA_SUBCATEGORY);

        if (category == null || category.trim().isEmpty()) {
            return "Success Story";
        }

        if (subcategory == null || subcategory.trim().isEmpty()) {
            return category;
        }

        return category + " | " + subcategory;
    }

    private String getTextOrDefault(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }

    private void setImageUrl(ImageView imageView, String rawImageUrl) {
        String finalImageUrl = resolveImageUrl(rawImageUrl);
        Log.d(TAG, "Raw imageUrl=" + rawImageUrl);

        if (finalImageUrl == null) {
            imageView.setImageResource(R.drawable.blog_image);
            return;
        }

        Log.d(TAG, "Final imageUrl=" + finalImageUrl);
        Picasso.get()
                .load(finalImageUrl)
                .placeholder(R.drawable.blog_image)
                .error(R.drawable.blog_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // No-op. Temporary failure logging is handled below.
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Success story detail image load failed: URL="
                                + finalImageUrl, e);
                    }
                });
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
}
