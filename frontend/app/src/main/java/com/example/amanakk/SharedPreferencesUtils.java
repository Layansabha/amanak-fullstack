package com.example.amanakk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class SharedPreferencesUtils {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String SENDER_ID_KEY = "Sender_Id";
    private static final String RECEIVER_NATIONAL_ID_KEY = "Receiver_National_Id";
    private static final String FULL_NAME_KEY = "Full_Name";
    private static final String ID_NUMBER_KEY = "Id_Number";
    private static final String PHONE_NUMBER_KEY = "Phone_Number";
    private static final String LOGIN_IDENTIFIER_KEY = "Login_Identifier";

    public static void saveUserProfile(Context context, String fullName, String nationalId, String idNumber, String phoneNumber, String loginIdentifier) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        putIfPresent(editor, FULL_NAME_KEY, fullName);
        putIfPresent(editor, RECEIVER_NATIONAL_ID_KEY, nationalId);
        putIfPresent(editor, SENDER_ID_KEY, nationalId);
        putIfPresent(editor, ID_NUMBER_KEY, idNumber);
        putIfPresent(editor, PHONE_NUMBER_KEY, phoneNumber);
        putIfPresent(editor, LOGIN_IDENTIFIER_KEY, loginIdentifier);
        editor.apply();
    }

    private static void putIfPresent(SharedPreferences.Editor editor, String key, String value) {
        if (value != null && !value.trim().isEmpty()) {
            editor.putString(key, value.trim());
        }
    }

    // Method to save Sender ID to SharedPreferences
    public static void saveSenderId(Context context, String senderId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SENDER_ID_KEY, senderId);
        editor.apply();
    }

    // Method to get Sender ID from SharedPreferences
    public static String getSenderId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String senderId = prefs.getString(SENDER_ID_KEY, "");
        if (senderId.isEmpty()) {
            Log.e("SharedPreferencesUtils", "Sender ID not found in SharedPreferences");
            Toast.makeText(context, "Sender ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
        }
        return senderId;
    }

    // Method to save Receiver National ID to SharedPreferences
    public static void saveReceiverNationalId(Context context, String receiverNationalId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(RECEIVER_NATIONAL_ID_KEY, receiverNationalId);
        editor.apply();
    }

    // Method to get Receiver National ID from SharedPreferences
    public static String getReceiverNationalId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String receiverNationalId = prefs.getString(RECEIVER_NATIONAL_ID_KEY, "");
        if (receiverNationalId.isEmpty()) {
            Log.e("SharedPreferencesUtils", "Receiver National ID not found in SharedPreferences");
            Toast.makeText(context, "Receiver National ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
        }
        return receiverNationalId;
    }

    public static String getReceiverNationalIdSilently(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(RECEIVER_NATIONAL_ID_KEY, "");
    }

    public static String getUserFullName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(FULL_NAME_KEY, "");
    }

    public static String getProfileNationalId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(RECEIVER_NATIONAL_ID_KEY, "");
    }

    public static String getUserIdNumber(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(ID_NUMBER_KEY, "");
    }

    public static String getUserPhoneNumber(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String phoneNumber = prefs.getString(PHONE_NUMBER_KEY, "");
        if (phoneNumber.isEmpty()) {
            String loginIdentifier = prefs.getString(LOGIN_IDENTIFIER_KEY, "");
            if (loginIdentifier.startsWith("07")) {
                phoneNumber = loginIdentifier;
            }
        }
        return phoneNumber;
    }

    public static String getLoginIdentifier(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LOGIN_IDENTIFIER_KEY, "");
    }
}
