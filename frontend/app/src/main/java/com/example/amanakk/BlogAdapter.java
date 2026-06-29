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

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private static final String TAG = "BlogAdapter";
    private static final String BACKEND_ORIGIN = "http://10.0.2.2:8081";

    private Context context;
    private List<Post> posts;

    public BlogAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_blog, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.blogTitle.setText(post.getTitle());
        String category = post.getCategory();
        holder.blogCategory.setText(category == null || category.trim().isEmpty() ? "Blog" : category);

        String rawImageUrl = post.getImageUrl();
        String finalImageUrl = resolveImageUrl(rawImageUrl);
        Log.d(TAG, "Post id=" + post.getId() + " raw imageUrl=" + rawImageUrl);

        if (finalImageUrl == null) {
            holder.blogImage.setImageResource(R.drawable.blog_image);
        } else {
            Log.d(TAG, "Post id=" + post.getId() + " final image URL=" + finalImageUrl);
            Picasso.get()
                    .load(finalImageUrl)
                    .placeholder(R.drawable.blog_image)
                    .error(R.drawable.blog_image)
                    .into(holder.blogImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            // No-op. Temporary failure logging is handled below.
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Image load failed for post id=" + post.getId()
                                    + ", URL=" + finalImageUrl, e);
                        }
                    });
        }

        holder.itemView.setOnClickListener(v -> {
            int clickPosition = holder.getBindingAdapterPosition();
            if (clickPosition == RecyclerView.NO_POSITION) {
                return;
            }

            Post clickedPost = posts.get(clickPosition);
            Log.d(TAG, "Clicked position=" + clickPosition + ", post id=" + clickedPost.getId());
            openBlogDetails(clickedPost);
        });
    }

    private void openBlogDetails(Post post) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("title", post.getTitle());
        intent.putExtra("category", post.getCategory());
        intent.putExtra("subtitle", post.getSubtitle());
        intent.putExtra("content", post.getContent());
        intent.putExtra("date", post.getDate() != null ? post.getDate() : post.getCreatedAt());
        intent.putExtra(BlogDetailActivity.EXTRA_IMAGE_URL, post.getImageUrl());
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

    @Override
    public int getItemCount() {
        Log.d(TAG, "Adapter item count=" + posts.size());
        return posts.size();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        TextView blogTitle, blogCategory;
        ImageView blogImage;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            blogCategory = itemView.findViewById(R.id.blogCategory);
            blogImage = itemView.findViewById(R.id.blogImage);
        }
    }
}
