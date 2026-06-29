package com.example.amanakk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BlogDetailActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_URL = "imageUrl";

    private static final String TAG = "BlogDetailActivity";
    private static final String BACKEND_ORIGIN = "http://10.0.2.2:8081";

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
        detailTitle.setText(getIntent().getStringExtra("title"));
        detailCategory.setText(getTextOrDefault(getIntent().getStringExtra("category"), "Blog"));
        detailDate.setText(getTextOrDefault(getIntent().getStringExtra("date"), ""));

        String subtitle = getIntent().getStringExtra("subtitle");
        if (subtitle == null || subtitle.trim().isEmpty()) {
            detailSubtitle.setVisibility(View.GONE);
        } else {
            detailSubtitle.setText(subtitle);
        }

        detailContent.setText(getTextOrDefault(getIntent().getStringExtra("content"), ""));
        setImageUrl(detailImage, getIntent().getStringExtra(EXTRA_IMAGE_URL));
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

        Log.d(TAG, "Final image URL=" + finalImageUrl);
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
                        Log.e(TAG, "Image load failed for URL=" + finalImageUrl, e);
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
