package com.example.amanakk;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private List<Uri> fileList;
    private Context context;
    private OnFileRemoveListener onFileRemoveListener;

    public FileAdapter(List<Uri> fileList, OnFileRemoveListener onFileRemoveListener) {
        this.fileList = fileList;
        this.onFileRemoveListener = onFileRemoveListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri fileUri = fileList.get(position);
        holder.fileName.setText(fileUri.getLastPathSegment());
        String mimeType = context.getContentResolver().getType(fileUri);
        if (mimeType != null && mimeType.startsWith("image/")) {
            holder.filePreview.setImageURI(fileUri);
        } else {
            holder.filePreview.setImageResource(R.drawable.report_icon);
        }

        holder.removeButton.setOnClickListener(v -> onFileRemoveListener.onFileRemoved(position));  // Pass position for removal
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public interface OnFileRemoveListener {
        void onFileRemoved(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        ImageView removeButton, filePreview;

        ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            removeButton = itemView.findViewById(R.id.removeButton);
            filePreview = itemView.findViewById(R.id.filePreview);
        }
    }
}
