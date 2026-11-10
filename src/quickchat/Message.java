
package quickchat;

import java.util.UUID;

public class Message {

    private String messageId;
    private int messageNumber;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    public Message() {
    }

    public Message(int messageNumber, String sender,
                   String recipient, String messageText,
                   String status) {

        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.status = status;

        this.messageId = createTenDigitId();
        this.messageHash = createMessageHash();
    }

    // Original Part 2 constructor retained
    public Message(int messageNumber, String recipient,
                   String messageText) {

        this(messageNumber, "Developer", recipient,
                messageText, "Sent");
    }

    private String createTenDigitId() {

        StringBuilder digits = new StringBuilder();

        while (digits.length() < 10) {
            digits.append(UUID.randomUUID()
                    .toString()
                    .replaceAll("[^0-9]", ""));
        }

        return digits.substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageId != null && messageId.length() == 10;
    }

    public String checkRecipientCell() {

        if (recipient != null &&
                recipient.matches("\\+27[0-9]{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted "
                + "or does not contain an international code. "
                + "Please correct the number and try again.";
    }

    public String checkMessageLength() {

        if (messageText == null ||
                messageText.trim().isEmpty()) {

            return "Please enter a message.";
        }

        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (messageText.length() - 250)
                + "; please reduce the size.";
    }

    public String createMessageHash() {

        if (messageText == null ||
                messageText.trim().isEmpty() ||
                messageId == null) {

            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = cleanWord(words[0]);
        String lastWord = cleanWord(words[words.length - 1]);

        return messageId.substring(0, 2)
                + ":" + messageNumber + ":"
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
                + "\nSender: " + sender
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText
                + "\nStatus: " + status;
    }

    public int returnTotalMessagess() {
        return 1;
    }

    // Getters

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getStatus() {
        return status;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    // Setters

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}
