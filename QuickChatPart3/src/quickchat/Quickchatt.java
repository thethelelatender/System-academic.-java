
package quickchat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Quickchatt {

    private static final Scanner INPUT = new Scanner(System.in);

    private static final MessageManager manager =
            new MessageManager(Path.of("storedMessages.json"));

    private static int sentCount = 0;
    private static int processedCount = 0;

    public static void main(String[] args) {

        System.out.println("Welcome to QuickChat.");

        if (!loginGateForPractice()) {
            System.out.println("Login failed. You cannot access QuickChat.");
            return;
        }

        try {
            manager.loadStoredMessages();
            System.out.println("Stored messages loaded successfully.");
        } catch (Exception ex) {
            System.out.println("Could not load stored messages: "
                    + ex.getMessage());
        }

        System.out.print("How many messages would you like to enter? ");

        int messageLimit = readPositiveInteger();

        boolean running = true;

        while (running) {

            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");

            System.out.print("Choose an option (1-4): ");

            String option = INPUT.nextLine().trim();

            switch (option) {

                case "1":
                    sendMessages(messageLimit);
                    break;

                case "2":
                    displaySentMessages();
                    break;

                case "3":
                    storedMessagesMenu();
                    break;

                case "4":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }

        System.out.println("Total messages successfully sent: " + sentCount);
    }

    private static boolean loginGateForPractice() {

        System.out.print(
                "For this Part 2 practice run, enter Y to simulate a successful login: ");

        return INPUT.nextLine().trim().equalsIgnoreCase("Y");
    }

    private static int readPositiveInteger() {

        while (true) {

            String value = INPUT.nextLine().trim();

            try {
                int number = Integer.parseInt(value);

                if (number > 0) {
                    return number;
                }

                System.out.print("Enter a number greater than zero: ");

            } catch (NumberFormatException ex) {

                System.out.print("Enter a valid whole number: ");
            }
        }
    }

    // Send, disregard or store messages
    private static void sendMessages(int messageLimit) {

        while (processedCount < messageLimit) {

            System.out.println("\n--- Message "
                    + (processedCount + 1) + " of " + messageLimit + " ---");

            System.out.print("Enter recipient (+27 followed by 9 digits): ");

            String recipient = INPUT.nextLine().trim();

            Message validation = new Message(
                    processedCount + 1, recipient, "placeholder");

            String recipientStatus = validation.checkRecipientCell();

            if (!recipientStatus.startsWith(
                    "Cell phone number successfully")) {

                System.out.println(recipientStatus);
                continue;
            }

            System.out.print("Enter your message (maximum 250 characters): ");

            String text = INPUT.nextLine();

            Message message = new Message(
                    processedCount + 1,
                    "Developer",
                    recipient,
                    text,
                    "Sent"
            );

            String lengthStatus = message.checkMessageLength();

            System.out.println(lengthStatus);

            if (!lengthStatus.equals("Message ready to send.")) {
                continue;
            }

            System.out.println("\nChoose what to do:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");

            System.out.print("Your choice: ");

            String choice = INPUT.nextLine().trim();

            try {

                switch (choice) {

                    case "1":

                        manager.addMessage(message, "Sent");

                        sentCount++;
                        processedCount++;

                        System.out.println("Message successfully sent.");
                        System.out.println(message.printMessages());

                        break;

                    case "2":

                        System.out.print(
                                "Enter 0 to confirm disregard: ");

                        String confirm = INPUT.nextLine().trim();

                        if (confirm.equals("0")) {

                            manager.addMessage(message, "Disregard");

                            processedCount++;

                            System.out.println("Message disregarded.");

                        } else {
                            System.out.println("Disregard cancelled.");
                        }

                        break;

                    case "3":

                        manager.addMessage(message, "Stored");

                        processedCount++;

                        System.out.println("Message successfully stored.");
                        System.out.println(message.printMessages());

                        break;

                    default:
                        System.out.println("Invalid message option.");
                }

            } catch (IOException ex) {

                System.out.println("Could not save message: "
                        + ex.getMessage());
            }
        }

        if (processedCount >= messageLimit) {
            System.out.println("You have reached your message limit.");
        }
    }

    // Display recently sent messages
    private static void displaySentMessages() {

        ArrayList<Message> messages = manager.getSentMessages();

        if (messages.isEmpty()) {
            System.out.println("No sent messages available.");
            return;
        }

        for (Message message : messages) {
            System.out.println("\n" + message.printMessages());
        }
    }

    // Fourth main menu option
    private static void storedMessagesMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== STORED MESSAGES MENU =====");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display longest stored message");
            System.out.println("3) Search message by ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete message using hash");
            System.out.println("6) Display complete message report");
            System.out.println("7) Return to main menu");

            System.out.print("Choose an option (1-7): ");

            String option = INPUT.nextLine().trim();

            switch (option) {

                case "1":

                    System.out.println(
                            manager.displaySendersAndRecipients());

                    break;

                case "2":

                    System.out.println("Longest stored message:");

                    System.out.println(manager.getLongestMessage());

                    break;

                case "3":

                    System.out.print("Enter message ID: ");

                    String id = INPUT.nextLine().trim();

                    Message found = manager.searchByMessageID(id);

                    if (found != null) {
                        System.out.println(found.printMessages());
                    } else {
                        System.out.println("Message ID not found.");
                    }

                    break;

                case "4":

                    System.out.print("Enter recipient number: ");

                    String recipient = INPUT.nextLine().trim();

                    ArrayList<Message> results =
                            manager.searchByRecipient(recipient);

                    if (results.isEmpty()) {
                        System.out.println("No messages found for this recipient.");
                    } else {
                        for (Message message : results) {
                            System.out.println(message.printMessages());
                        }
                    }

                    break;

                case "5":

                    System.out.print("Enter message hash to delete: ");

                    String hash = INPUT.nextLine().trim();

                    try {

                        boolean deleted = manager.deleteByHash(hash);

                        if (deleted) {
                            System.out.println(
                                    "Message successfully deleted.");
                        } else {
                            System.out.println(
                                    "No stored message found with that hash.");
                        }

                    } catch (IOException ex) {

                        System.out.println("Could not update JSON file: "
                                + ex.getMessage());
                    }

                    break;

                case "6":

                    System.out.println(manager.displayReport());

                    break;

                case "7":

                    running = false;

                    break;

                default:

                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
