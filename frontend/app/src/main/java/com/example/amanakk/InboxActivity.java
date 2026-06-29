package com.example.amanakk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity {
    private static final long DEMO_MESSAGE_ID = -20260625L;
    private RecyclerView recyclerViewInbox;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        recyclerViewInbox = findViewById(R.id.recyclerViewInbox);
        ImageView aiChat = findViewById(R.id.ai);
        ImageView expertChat = findViewById(R.id.exp);

        recyclerViewInbox.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageList);
        recyclerViewInbox.setAdapter(messageAdapter);

        messageAdapter.setOnItemClickListener(position -> {
            Message message = messageList.get(position);
            openChatForMessage(message);
        });

        if (aiChat != null) {
            aiChat.setOnClickListener(v -> startActivity(new Intent(this, ai.class)));
        }

        if (expertChat != null) {
            expertChat.setOnClickListener(v -> {
                if (messageList.isEmpty()) {
                    openDemoChat();
                } else {
                    openChatForMessage(messageList.get(0));
                }
            });
        }

        loadInboxMessages();
        setupBottomNavigationView();
    }

    private void openChatForMessage(Message message) {
        if (message.getId() != null && message.getId() == DEMO_MESSAGE_ID) {
            openDemoChat();
            return;
        }

        String receiverId = message.getSenderId();
        if (receiverId == null || receiverId.trim().isEmpty()) {
            receiverId = message.getReceiverNationalId();
        }

        if (receiverId == null || receiverId.trim().isEmpty()) {
            Toast.makeText(this, "Unable to open this chat. Missing receiver information.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(InboxActivity.this, ChatActivity.class);
        intent.putExtra("receiverId", receiverId.trim());
        startActivity(intent);
    }

    private void openDemoChat() {
        Intent intent = new Intent(InboxActivity.this, ChatActivity.class);
        intent.putExtra(ChatActivity.EXTRA_DEMO_CHAT, true);
        intent.putExtra("receiverId", ChatActivity.DEMO_SPECIALIST_ID);
        startActivity(intent);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavUtils.setup(bottomNavigationView, this, R.id.message);
    }

    private void loadInboxMessages() {
        String receiverId = SharedPreferencesUtils.getReceiverNationalIdSilently(this);
        if (receiverId == null || receiverId.trim().isEmpty()) {
            showDemoInboxMessage();
            return;
        }

        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        Call<List<Message>> call = apiService.getUserMessages(receiverId);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear();
                    if (response.body().isEmpty()) {
                        messageList.add(createDemoInboxMessage());
                    } else {
                        messageList.addAll(response.body());
                    }
                    messageAdapter.notifyDataSetChanged();
                } else {
                    showDemoInboxMessage();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                showDemoInboxMessage();
            }
        });
    }

    private void showDemoInboxMessage() {
        messageList.clear();
        messageList.add(createDemoInboxMessage());
        messageAdapter.notifyDataSetChanged();
    }

    private Message createDemoInboxMessage() {
        Message message = new Message();
        message.setId(DEMO_MESSAGE_ID);
        message.setSenderName(ChatActivity.DEMO_SPECIALIST_NAME);
        message.setSenderId(ChatActivity.DEMO_SPECIALIST_ID);
        message.setReceiverNationalId(ChatActivity.DEMO_USER_ID);
        message.setMessagePreview("Security Specialist - Your report is under review");
        message.setTimestamp("Now");
        message.setSolved(false);
        return message;
    }
}
