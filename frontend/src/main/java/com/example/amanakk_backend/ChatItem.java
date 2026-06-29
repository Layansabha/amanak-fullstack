package com.example.amanakk_backend;

public class ChatItem {
    private String name;
    private String messagePreview;
    private String time;
    private boolean isSolved;

    public ChatItem(String name, String messagePreview, String time, boolean isSolved) {
        this.name = name;
        this.messagePreview = messagePreview;
        this.time = time;
        this.isSolved = isSolved;
    }

    public String getName() {
        return name;
    }

    public String getMessagePreview() {
        return messagePreview;
    }

    public String getTime() {
        return time;
    }

    public boolean isSolved() {
        return isSolved;
    }
}
