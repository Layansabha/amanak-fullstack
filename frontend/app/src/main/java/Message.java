public class Message {
    private String messageText;
    private boolean isSender;

    public Message(String messageText, boolean isSender) {
        this.messageText = messageText;
        this.isSender = isSender;
    }

    public String getMessageText() {
        return messageText;
    }

    public boolean isSender() {
        return isSender;
    }
}
