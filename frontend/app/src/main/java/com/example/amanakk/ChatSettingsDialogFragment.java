package com.example.amanakk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ChatSettingsDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this dialog
        return inflater.inflate(R.layout.chat_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the TextViews for the options
        TextView turnOffNotification = view.findViewById(R.id.textView25);
        TextView markAsSolved = view.findViewById(R.id.textView26);
        TextView deleteChat = view.findViewById(R.id.textView27);

        // Set listeners for each option

        // Turn off notifications click handler
        turnOffNotification.setOnClickListener(v -> {
            // Here you can add logic to handle turning off notifications
            // For example, call a method in the parent activity to handle this
            // After the action is done, you can dismiss the dialog if needed
            dismiss();
        });

        // Mark as solved click handler
        markAsSolved.setOnClickListener(v -> {
            // Logic to mark the chat as solved
            // You can trigger a method in the parent activity or fragment
            dismiss();
        });

        // Delete chat click handler
        deleteChat.setOnClickListener(v -> {
            // Handle delete chat action
            // You can trigger a confirmation dialog or directly delete the chat
            dismiss();  // Dismiss the dialog after delete action
        });
    }
}
