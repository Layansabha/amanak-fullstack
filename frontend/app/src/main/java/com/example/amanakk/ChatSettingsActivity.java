package com.example.amanakk;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_set); // Use the XML layout here

        // Find views
        TextView turnOffNotifications = findViewById(R.id.textView25);
        TextView markAsSolved = findViewById(R.id.textView26);
        TextView deleteChat = findViewById(R.id.textView27);
        ImageView backIcon = findViewById(R.id.imageView27);

        // Back button functionality
        backIcon.setOnClickListener(v -> finish()); // Close the settings dialog or screen

        // Handling the "Turn off notifications" click
        turnOffNotifications.setOnClickListener(v -> {
            // Call a function to turn off notifications
            handleTurnOffNotifications();
        });

        // Handling the "Mark as Solved" click
        markAsSolved.setOnClickListener(v -> {
            // Call a function to mark the chat as solved
            handleMarkAsSolved();
        });

        // Handling the "Delete chat" click
        deleteChat.setOnClickListener(v -> {
            // Show a confirmation dialog before deleting the chat
            showDeleteConfirmationDialog();
        });
    }

    // Function to handle turning off notifications
    private void handleTurnOffNotifications() {
        // Logic to turn off chat notifications (you may need to interact with a backend API or local storage)
        Toast.makeText(this, "Notifications turned off", Toast.LENGTH_SHORT).show();
    }

    // Function to mark chat as solved
    private void handleMarkAsSolved() {
        // Logic to mark the chat as solved (this could involve an API call to your backend)
        Toast.makeText(this, "Chat marked as solved", Toast.LENGTH_SHORT).show();
    }

    // Show confirmation dialog before deleting chat
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Chat")
                .setMessage("Are you sure you want to delete this chat?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Call the delete chat function if confirmed
                    handleDeleteChat();
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Function to delete the chat
    private void handleDeleteChat() {
        // Logic to delete the chat (again, likely an API call to your backend)
        Toast.makeText(this, "Chat deleted", Toast.LENGTH_SHORT).show();
        // Optionally, you might want to close the screen after deleting the chat
        finish();
    }
}
