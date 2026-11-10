package quickchat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageManagerTest {

    private MessageManager manager;
    private Path testDirectory;
    private Path testFile;

    private Message m1;
    private Message m2;
    private Message m3;
    private Message m4;
    private Message m5;

    @Before
    public void setUp() throws Exception {

        testDirectory = Files.createTempDirectory("quickchat-test");

        testFile = testDirectory.resolve("storedMessages.json");

        manager = new MessageManager(testFile);

        // Assignment test data

        m1 = new Message(1, "Developer",
                "+27834557896",
                "Did you get the cake?",
                "Sent");

        m2 = new Message(2, "Developer",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "Stored");

        m3 = new Message(3, "Developer",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                "Disregard");

        m4 = new Message(4, "Developer",
                "0838884567",
                "It is dinner time!",
                "Sent");

        m5 = new Message(5, "Developer",
                "+27838884567",
                "Ok, I am leaving without you.",
                "Stored");

        manager.addMessage(m1, "Sent");
        manager.addMessage(m2, "Stored");
        manager.addMessage(m3, "Disregard");
        manager.addMessage(m4, "Sent");
        manager.addMessage(m5, "Stored");
    }

    @After
    public void tearDown() throws Exception {

        Files.deleteIfExists(testFile);
        Files.deleteIfExists(testDirectory);
    }

    // TEST 1: Sent Messages array correctly populated

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {

        assertEquals(2, manager.getSentMessages().size());

        assertEquals(
                "Did you get the cake?",
                manager.getSentMessages().get(0).getMessageText());

        assertEquals(
                "It is dinner time!",
                manager.getSentMessages().get(1).getMessageText());
    }

    // TEST 2: All five arrays correctly populated

    @Test
    public void testAllArraysCorrectlyPopulated() {

        assertEquals(2, manager.getSentMessages().size());

        assertEquals(1, manager.getDisregardedMessages().size());

        assertEquals(2, manager.getStoredMessages().size());

        assertEquals(5, manager.getMessageHashes().size());

        assertEquals(5, manager.getMessageIDs().size());
    }

    // TEST 3: Display the longest stored message

    @Test
    public void testDisplayLongestMessage() {

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                manager.getLongestMessage());
    }

    // TEST 4: Search for a message using its ID

    @Test
    public void testSearchForMessageID() {

        Message result =
                manager.searchByMessageID(m4.getMessageId());

        assertNotNull(result);

        assertEquals("0838884567", result.getRecipient());

        assertEquals(
                "It is dinner time!",
                result.getMessageText());
    }

    // TEST 5: Search messages for a particular recipient

    @Test
    public void testSearchMessagesByRecipient() {

        ArrayList<Message> results =
                manager.searchByRecipient("+27838884567");

        assertEquals(2, results.size());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                results.get(0).getMessageText());

        assertEquals(
                "Ok, I am leaving without you.",
                results.get(1).getMessageText());
    }

    // TEST 6: Delete a message using its hash

    @Test
    public void testDeleteMessageUsingHash() throws Exception {

        String hash = m2.getMessageHash();

        boolean deleted = manager.deleteByHash(hash);

        assertTrue(deleted);

        assertEquals(1, manager.getStoredMessages().size());

        assertNull(manager.searchByMessageID(m2.getMessageId()));

        assertFalse(manager.getMessageHashes().contains(hash));

        assertFalse(manager.getMessageIDs().contains(m2.getMessageId()));
    }

    // TEST 7: Display complete message report

    @Test
    public void testDisplayReport() {

        String report = manager.displayReport();

        assertTrue(report.contains("SENT MESSAGES"));

        assertTrue(report.contains("DISREGARDED MESSAGES"));

        assertTrue(report.contains("STORED MESSAGES"));

        assertTrue(report.contains(m1.getMessageHash()));

        assertTrue(report.contains("+27834557896"));

        assertTrue(report.contains("Did you get the cake?"));

        assertTrue(report.contains("It is dinner time!"));
    }

    // TEST 8: Read stored messages from JSON

    @Test
    public void testReadStoredMessagesFromJSON() throws Exception {

        MessageManager newManager =
                new MessageManager(testFile);

        newManager.loadStoredMessages();

        assertEquals(2, newManager.getStoredMessages().size());

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                newManager.getStoredMessages().get(0).getMessageText());

        assertEquals(
                "Ok, I am leaving without you.",
                newManager.getStoredMessages().get(1).getMessageText());
    }
}
