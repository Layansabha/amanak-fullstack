package com.example.amanakk;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final Context context;
    private final List<Message> chatMessages;
    private final String currentSenderId;

    public ChatAdapter(Context context, List<Message> chatMessages) {
        this(context, chatMessages, SharedPreferencesUtils.getSenderId(context));
    }

    public ChatAdapter(Context context, List<Message> chatMessages, String currentSenderId) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.currentSenderId = currentSenderId == null ? "" : currentSenderId.trim();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_bubble, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = chatMessages.get(position);
        boolean sentByCurrentUser = message.getSenderId() != null && message.getSenderId().equals(currentSenderId);

        if (sentByCurrentUser) {
            holder.senderMessage.setText(message.getMessagePreview());
            holder.senderMessage.setVisibility(View.VISIBLE);
            holder.receiverMessage.setVisibility(View.GONE);
            alignTime(holder.messageTime, Gravity.END);
        } else {
            holder.receiverMessage.setText(message.getMessagePreview());
            holder.receiverMessage.setVisibility(View.VISIBLE);
            holder.senderMessage.setVisibility(View.GONE);
            alignTime(holder.messageTime, Gravity.START);
        }

        holder.messageTime.setText(message.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    private void alignTime(TextView timeView, int gravity) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) timeView.getLayoutParams();
        params.gravity = gravity;
        timeView.setLayoutParams(params);
        timeView.setGravity(gravity);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage;
        TextView receiverMessage;
        TextView messageTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message);
            receiverMessage = itemView.findViewById(R.id.receiver_message);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }
}
