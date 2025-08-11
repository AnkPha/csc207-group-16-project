package entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class FriendRequestTest {

    @Test
    @DisplayName("Should create friend request with valid sender and receiver")
    public void testConstructorWithValidParameters() {
        String sender = "alice@example.com";
        String receiver = "bob@example.com";

        FriendRequest friendRequest = new FriendRequest(sender, receiver);

        assertEquals(sender, friendRequest.getSender());
        assertEquals(receiver, friendRequest.getReceiver());
        assertTrue(friendRequest.isPending());
    }
}
