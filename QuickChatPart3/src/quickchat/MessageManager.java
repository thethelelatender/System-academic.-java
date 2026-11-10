
package quickchat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageManager {

    private final ArrayList<Message> sentMessages =
            new ArrayList<>();

    private final ArrayList<Message> disregardedMessages =
            new ArrayList<>();

    private final ArrayList<Message> storedMessages =
            new ArrayList<>();

    private final ArrayList<String> messageHashes =
            new ArrayList<>();

    private final ArrayList<String> messageIDs =
            new ArrayList<>();

    private final Path file;

    
   // Allows unit tests to use a separate temporary JSON file
    public MessageManager() {
    this(Path.of("storedMessages.json"));

        try {
            loadStoredMessages();
        } catch (IOException e) {
            System.out.println("Could not load stored messages: "
                    + e.getMessage());
        }
    }

        // Allows unit tests to use a separate temporary JSON file
    public MessageManager(Path file) {
        this.file = file;
    }

    // Populate the appropriate arrays
    public void addMessage(Message message, String status)
            throws IOException {

        String selectedStatus = status.toLowerCase();

        switch (selectedStatus) {

            case "sent":
                message.setStatus("Sent");
                sentMessages.add(message);
                break;

            case "disregard":
            case "disregarded":
                message.setStatus("Disregard");
                disregardedMessages.add(message);
                break;

            case "stored":
                message.setStatus("Stored");
                storedMessages.add(message);
                saveStoredMessages();
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid message status.");
        }

        if (!messageIDs.contains(message.getMessageId())) {
            messageIDs.add(message.getMessageId());
        }

        if (!messageHashes.contains(message.getMessageHash())) {
            messageHashes.add(message.getMessageHash());
        }
    }

    // Save stored messages to JSON
    public void saveStoredMessages() throws IOException {

        JSONArray jsonArray = new JSONArray();

        for (Message message : storedMessages) {

            JSONObject object = new JSONObject();

            object.put("messageId", message.getMessageId());
            object.put("messageHash", message.getMessageHash());
            object.put("messageNumber", message.getMessageNumber());
            object.put("sender", message.getSender());
            object.put("recipient", message.getRecipient());
            object.put("messageText", message.getMessageText());
            object.put("status", message.getStatus());

            jsonArray.put(object);
        }

        Path parent = file.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
                file,
                jsonArray.toString(4),
                StandardCharsets.UTF_8
        );
    }

    // Read stored messages from JSON
    public void loadStoredMessages() throws IOException {

        storedMessages.clear();

        if (!Files.exists(file)) {
            return;
        }

        String json = Files.readString(
                file, StandardCharsets.UTF_8);

        if (json.trim().isEmpty()) {
            return;
        }

        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject object = jsonArray.getJSONObject(i);

            Message message = new Message();

            message.setMessageId(
                    object.optString("messageId", ""));

            message.setMessageNumber(
                    object.optInt("messageNumber", 0));

            message.setSender(
                    object.optString("sender", "Developer"));

            message.setRecipient(
                    object.optString("recipient", ""));

            // Supports both your old and new JSON formats
            String messageText = object.has("messageText")
                    ? object.optString("messageText", "")
                    : object.optString("message", "");

            message.setMessageText(messageText);

            message.setStatus("Stored");

            String hash = object.optString("messageHash", "");

            if (hash.isEmpty()) {
                hash = message.createMessageHash();
            }

            message.setMessageHash(hash);

            storedMessages.add(message);

            if (!messageIDs.contains(message.getMessageId())) {
                messageIDs.add(message.getMessageId());
            }

            if (!messageHashes.contains(message.getMessageHash())) {
                messageHashes.add(message.getMessageHash());
            }
        }
    }

    // A. Display sender and recipient of stored messages
    public String displaySendersAndRecipients() {

        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {

            result.append("Sender: ")
                    .append(message.getSender());

            result.append("\nRecipient: ")
                    .append(message.getRecipient());

            result.append("\n\n");
        }

        return result.length() == 0
                ? "No stored messages found."
                : result.toString();
    }

    // B. Display longest stored message
    public String getLongestMessage() {

        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        Message longest = storedMessages.get(0);

        for (Message message : storedMessages) {

            if (message.getMessageText().length()
                    > longest.getMessageText().length()) {

                longest = message;
            }
        }

        return longest.getMessageText();
    }

    // C. Search by message ID across all message categories
    public Message searchByMessageID(String id) {

        for (Message message : sentMessages) {
            if (message.getMessageId().equals(id)) {
                return message;
            }
        }

        for (Message message : storedMessages) {
            if (message.getMessageId().equals(id)) {
                return message;
            }
        }

        for (Message message : disregardedMessages) {
            if (message.getMessageId().equals(id)) {
                return message;
            }
        }

        return null;
    }

    // D. Search sent and stored messages by recipient
    public ArrayList<Message> searchByRecipient(String recipient) {

        ArrayList<Message> results = new ArrayList<>();

        for (Message message : sentMessages) {

            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        for (Message message : storedMessages) {

            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        return results;
    }

// E. Delete a stored message using its hash
public boolean deleteByHash(String hash) throws IOException {

    if (hash == null || hash.trim().isEmpty()) {
        return false;
    }

    String searchHash = hash.trim();

    Iterator<Message> iterator = storedMessages.iterator();

    while (iterator.hasNext()) {

        Message message = iterator.next();

        if (message.getMessageHash() != null &&
            message.getMessageHash().trim().equalsIgnoreCase(searchHash)) {

            // Remove message from stored messages
            iterator.remove();

            // Remove corresponding ID and hash
            messageIDs.remove(message.getMessageId());
            messageHashes.remove(message.getMessageHash());

            // Save updated messages to JSON file
            saveStoredMessages();

            System.out.println("Message: "
                    + message.getMessageHash()
                    + " successfully deleted.");

            return true;
        }
    }

    System.out.println("Message not found. Check the message hash.");

    return false;
}
    // F. Display complete message report
    public String displayReport() {

        StringBuilder report = new StringBuilder();

        report.append("========== QUICKCHAT MESSAGE REPORT ==========\n");

        appendMessages(report, "SENT MESSAGES", sentMessages);
        appendMessages(report, "DISREGARDED MESSAGES",
                disregardedMessages);
        appendMessages(report, "STORED MESSAGES", storedMessages);

        return report.toString();
    }

    private void appendMessages(
            StringBuilder report,
            String heading,
            ArrayList<Message> messages) {

        report.append("\n").append(heading).append("\n");

        for (Message message : messages) {

            report.append("\nMessage ID: ")
                    .append(message.getMessageId());

            report.append("\nMessage Hash: ")
                    .append(message.getMessageHash());

            report.append("\nSender: ")
                    .append(message.getSender());

            report.append("\nRecipient: ")
                    .append(message.getRecipient());

            report.append("\nMessage: ")
                    .append(message.getMessageText());

            report.append("\nStatus: ")
                    .append(message.getStatus());

            report.append("\n--------------------------------------\n");
        }
    }

    // Getters for all five arrays

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public ArrayList<String> getMessageIDs() {
        return messageIDs;
    }
}
