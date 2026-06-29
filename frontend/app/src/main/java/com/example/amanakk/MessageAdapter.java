package com.example.amanakk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final Context context;
    private final List<Message> messages;
    private OnItemClickListener onItemClickListener;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.chatName.setText(message.getSenderName());
        holder.chatMessage.setText(message.getMessagePreview());
        holder.chatTime.setText(message.getTimestamp());

        // Assuming solved icon visibility is based on message status
        holder.solvedIcon.setVisibility(message.isSolved() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatName, chatMessage, chatTime;
        ImageView profileImage, solvedIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chatName);
            chatMessage = itemView.findViewById(R.id.chatMessage);
            chatTime = itemView.findViewById(R.id.chatTime);
            profileImage = itemView.findViewById(R.id.profileImage);
            solvedIcon = itemView.findViewById(R.id.solved);
        }
    }
}
