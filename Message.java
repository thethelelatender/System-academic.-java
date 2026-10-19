package quickchat;

import java.util.UUID;

public class Message {
    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = createTenDigitId();
        this.messageHash = createMessageHash();
    }

    private String createTenDigitId() {
        // UUID provides random characters; digits are taken until 10 are collected.
        StringBuilder digits = new StringBuilder();
        while (digits.length() < 10) {
            digits.append(UUID.randomUUID().toString().replaceAll("[^0-9]", ""));
        }
        return digits.substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    public String checkRecipientCell() {
        // +27 followed by 9 digits
        if (recipient != null && recipient.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText == null) {
            return "Please enter a message.";
        }
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by "
                + (messageText.length() - 250) + "; please reduce the size.";
    }

    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = cleanWord(words[0]);
        String lastWord = cleanWord(words[words.length - 1]);
        return messageId.substring(0, 2) + ":" + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }

    private String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    public String SentMessage() {
        return "Choose Send Message, Disregard Message, or Store Message.";
    }

    public String printMessages() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public int returnTotalMessagess() {
        return 1;
    }

    public String toJson() {
        return "{\"messageId\":\"" + jsonEscape(messageId)
                + "\",\"messageHash\":\"" + jsonEscape(messageHash)
                + "\",\"recipient\":\"" + jsonEscape(recipient)
                + "\",\"message\":\"" + jsonEscape(messageText) + "\"}";
    }

    private String jsonEscape(String value) {
        return value.replace("\\", "\\\\")   // backslash must be escaped first
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}