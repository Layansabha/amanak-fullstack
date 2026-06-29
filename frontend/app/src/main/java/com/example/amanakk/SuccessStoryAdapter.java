package com.example.amanakk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SuccessStoryAdapter extends RecyclerView.Adapter<SuccessStoryAdapter.ViewHolder> {
    private static final String TAG = "SuccessStoryAdapter";
    private static final String BACKEND_ORIGIN = "http://10.0.2.2:8081";

    private Context context;
    private List<SuccessStory> successStories;
    private final boolean useGridWidth;

    public SuccessStoryAdapter(Context context, List<SuccessStory> successStories) {
        this(context, successStories, false);
    }

    public SuccessStoryAdapter(Context context, List<SuccessStory> successStories, boolean useGridWidth) {
        this.context = context;
        this.successStories = successStories;
        this.useGridWidth = useGridWidth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_success_story, parent, false);
        if (useGridWidth) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuccessStory story = successStories.get(position);
        holder.title.setText(story.getTitle());
        holder.subtitle.setText(story.getSubtitle());
        holder.category.setText(getDisplayCategory(story));

        String rawImageUrl = story.getImageUrl();
        String finalImageUrl = resolveImageUrl(rawImageUrl);
        Log.d(TAG, "Success story id=" + story.getId() + " raw imageUrl=" + rawImageUrl);

        if (finalImageUrl != null) {
            Log.d(TAG, "Success story id=" + story.getId()
                    + " final imageUrl=" + finalImageUrl);
            Picasso.get()
                    .load(finalImageUrl)
                    .placeholder(R.drawable.blog_image)
                    .error(R.drawable.blog_image)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            // No-op. Temporary failure logging is handled below.
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Success story image load failed: id=" + story.getId()
                                    + ", URL=" + finalImageUrl, e);
                        }
                    });
        } else {
            holder.imageView.setImageResource(R.drawable.blog_image);
        }

        holder.itemView.setOnClickListener(v -> openSuccessStoryDetails(story));
    }

    private void openSuccessStoryDetails(SuccessStory story) {
        Intent intent = new Intent(context, SuccessStoryDetailActivity.class);
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_TITLE, story.getTitle());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_CATEGORY, story.getCategory());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_SUBCATEGORY, story.getSubcategory());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_SUBTITLE, story.getSubtitle());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_CONTENT, story.getContent());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_DATE, story.getCreatedAt());
        intent.putExtra(SuccessStoryDetailActivity.EXTRA_IMAGE_URL, story.getImageUrl());
        context.startActivity(intent);
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

    private String getDisplayCategory(SuccessStory story) {
        String category = story.getCategory();
        String subcategory = story.getSubcategory();
        String combined = ((category == null ? "" : category) + " "
                + (subcategory == null ? "" : subcategory)).toLowerCase(java.util.Locale.ROOT);
        String[] labels = {"Fraud", "Extortion", "Harassment", "Piracy", "Defamation", "Impersonation"};
        for (String label : labels) {
            if (combined.contains(label.toLowerCase(java.util.Locale.ROOT))) {
                return label;
            }
        }
        if (category != null && !category.trim().isEmpty()) {
            return category;
        }
        return subcategory == null || subcategory.trim().isEmpty() ? "Success Story" : subcategory;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Adapter item count=" + successStories.size());
        return successStories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, category;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.userName);
            subtitle = itemView.findViewById(R.id.userDescription);
            category = itemView.findViewById(R.id.caseType);
            imageView = itemView.findViewById(R.id.userImage);
        }
    }
}
