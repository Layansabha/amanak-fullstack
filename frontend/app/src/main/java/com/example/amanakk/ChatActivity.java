package com.example.amanakk;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amanakk.network.ApiClient;
import com.example.amanakk.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    public static final String EXTRA_DEMO_CHAT = "demoChat";
    public static final String DEMO_USER_ID = "AMANAK_DEMO_USER";
    public static final String DEMO_SPECIALIST_ID = "AMANAK_SECURITY_SPECIALIST";
    public static final String DEMO_SPECIALIST_NAME = "Mohammad";

    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private List<Message> chatMessageList = new ArrayList<>();
    private EditText messageInput;
    private ImageView sendButton;
    private String senderId;
    private String receiverNationalId;
    private boolean demoChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        demoChat = getIntent().getBooleanExtra(EXTRA_DEMO_CHAT, false);
        receiverNationalId = getIntent().getStringExtra("receiverId");

        if (demoChat) {
            senderId = DEMO_USER_ID;
            if (receiverNationalId == null || receiverNationalId.trim().isEmpty()) {
                receiverNationalId = DEMO_SPECIALIST_ID;
            }
        } else {
            senderId = SharedPreferencesUtils.getSenderId(this);
            if (senderId == null || senderId.trim().isEmpty()
                    || receiverNationalId == null || receiverNationalId.trim().isEmpty()) {
                Toast.makeText(this, "User information missing. Please log in again.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }

        senderId = senderId.trim();
        receiverNationalId = receiverNationalId.trim();

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, chatMessageList, senderId);
        recyclerViewChat.setAdapter(chatAdapter);

        TextView chatTitle = findViewById(R.id.chat_title);
        if (demoChat && chatTitle != null) {
            chatTitle.setText(DEMO_SPECIALIST_NAME);
        }

        messageInput = findViewById(R.id.message_input);
        if (demoChat) {
            messageInput.setHint("Message Mohammad");
        }

        sendButton = findViewById(R.id.send_button);
        ImageView backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        sendButton.setOnClickListener(v -> {
            String content = messageInput.getText().toString().trim();
            if (!content.isEmpty()) {
                sendMessage(content);
            }
        });

        loadChatMessages();
    }

    private void loadChatMessages() {
        if (demoChat) {
            chatMessageList.clear();
            chatMessageList.addAll(createDemoConversation());
            chatAdapter.notifyDataSetChanged();
            recyclerViewChat.scrollToPosition(chatMessageList.size() - 1);
            return;
        }

        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        Call<List<Message>> call = apiService.getChatMessages(senderId, receiverNationalId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatMessageList.clear();
                    chatMessageList.addAll(response.body());
                    chatAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error loading messages: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String content) {
        if (demoChat) {
            Message message = new Message();
            message.setSenderId(senderId);
            message.setReceiverNationalId(receiverNationalId);
            message.setSenderName("You");
            message.setMessagePreview(content);
            message.setTimestamp("Now");
            chatMessageList.add(message);
            chatAdapter.notifyDataSetChanged();
            messageInput.setText("");
            recyclerViewChat.smoothScrollToPosition(chatMessageList.size() - 1);
            return;
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverNationalId(receiverNationalId);
        message.setMessagePreview(content);

        ApiService apiService = ApiClient.getAppClient().create(ApiService.class);
        Call<Message> call = apiService.sendMessage(message);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatMessageList.add(response.body());
                    chatAdapter.notifyDataSetChanged();
                    messageInput.setText("");
                    recyclerViewChat.smoothScrollToPosition(chatMessageList.size() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error sending message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Message> createDemoConversation() {
        List<Message> messages = new ArrayList<>();
        messages.add(createDemoMessage(DEMO_SPECIALIST_ID, DEMO_USER_ID, DEMO_SPECIALIST_NAME, "مرحباً، أنا محمد من فريق أمانك للأمن السيبراني. وصلنا بلاغك رقم AMK-2026-0142 وبدأنا مراجعته بسرية تامة.", "10:15 AM"));
        messages.add(createDemoMessage(DEMO_USER_ID, DEMO_SPECIALIST_ID, "You", "شكراً. البلاغ عن حساب ينتحل اسمي ويرسل روابط مشبوهة للناس باسمي.", "10:16 AM"));
        messages.add(createDemoMessage(DEMO_SPECIALIST_ID, DEMO_USER_ID, DEMO_SPECIALIST_NAME, "تمام. لحمايتك الآن، لا تتواصل مع الحساب ولا تحذف أي رسائل. أرسل لقطات شاشة يظهر فيها اسم الحساب والرابط ووقت الرسالة.", "10:17 AM"));
        messages.add(createDemoMessage(DEMO_USER_ID, DEMO_SPECIALIST_ID, "You", "رفعت الصور ورابط الحساب داخل البلاغ.", "10:19 AM"));
        messages.add(createDemoMessage(DEMO_SPECIALIST_ID, DEMO_USER_ID, DEMO_SPECIALIST_NAME, "تم استلام الأدلة. حالة البلاغ الآن: قيد التحقيق. سنراجع البيانات ونربطها بالحساب المبلّغ عنه خلال 24 ساعة.", "10:20 AM"));
        messages.add(createDemoMessage(DEMO_SPECIALIST_ID, DEMO_USER_ID, DEMO_SPECIALIST_NAME, "إذا وصلتك تهديدات أو روابط جديدة، أرفقها هنا فوراً. بياناتك محمية، والمتابعة الرسمية ستكون عبر هذه المحادثة.", "10:21 AM"));
        return messages;
    }

    private Message createDemoMessage(String senderId, String receiverId, String senderName, String content, String timestamp) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverNationalId(receiverId);
        message.setSenderName(senderName);
        message.setMessagePreview(content);
        message.setTimestamp(timestamp);
        message.setSolved(false);
        return message;
    }
}
