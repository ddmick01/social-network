import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class SocialNetworkTest {
    private SocialNetwork network;
    @BeforeEach
    public void setUp() {
        network = new SocialNetwork();
    }

    // Test Constructor
    @Test
    public void testConstructor() {
        assertTrue(network.findInfluencers(1).isEmpty(), "New network should be empty");
    }

    // Test for addPerson
    @Test
    public void testAddPerson() {
        network.addPerson(1, "Dom");
        assertDoesNotThrow(() -> network.getPerson(1), "Dom should be added to the network");
        assertThrows(IllegalArgumentException.class, () -> network.addPerson(1, "John"), "Adding duplicate ID should throw exception");
    }

    // Test for addConnection
    @Test
    public void testAddConnection() {
        network.addPerson(1, "Dom");
        network.addPerson(2, "John");
        network.addConnection(1, 2);
        assertTrue(network.getPerson(1).getConnections().contains(network.getPerson(2)), "Dom should be connected to John");
        assertThrows(IllegalArgumentException.class, () -> network.addConnection(1, 3), "Adding connection with non-existent person should throw exception");
    }

    // Test for removeConnection
    @Test
    public void testRemoveConnection() {
        network.addPerson(1, "Dom");
        network.addPerson(2, "John");
        network.addConnection(1, 2);
        network.removeConnection(1, 2);
        assertFalse(network.getPerson(1).getConnections().contains(network.getPerson(2)), "Connection should be removed");
        assertDoesNotThrow(() -> network.removeConnection(1, 3), "Removing non-existent connection should not throw exception");
    }

    // Test for findInfluencers
    @Test
    public void testFindInfluencers() {
        network.addPerson(1, "Dom");
        network.addPerson(2, "John");
        network.addPerson(3, "Jane");
        network.addConnection(1, 2);
        network.addConnection(2, 3);

        List<Person> influencers = network.findInfluencers(2);
        assertEquals(2, influencers.size(), "Should return 2 influencers");
        assertEquals(2, influencers.get(0).getId(), "John should be the top influencer");

        SocialNetwork emptyNetwork = new SocialNetwork();
        assertTrue(emptyNetwork.findInfluencers(1).isEmpty(), "Empty network should return empty list");

        assertThrows(IllegalArgumentException.class, () -> network.findInfluencers(0), "Invalid k should throw exception");
        assertThrows(IllegalArgumentException.class, () -> network.findInfluencers(4), "Invalid k should throw exception");
    }

    // Test for shortestPath
    @Test
    public void testShortestPath() {
        network.addPerson(1, "Dom");
        network.addPerson(2, "John");
        network.addPerson(3, "Jane");
        network.addConnection(1, 2);
        network.addConnection(2, 3);

        List<Person> path = network.shortestPath(1, 3);
        assertNotNull(path, "Path should exist");
        assertEquals(3, path.size(), "Path should have 3 people");
        assertEquals(1, path.get(0).getId(), "Path should start with Dom");
        assertEquals(3, path.get(2).getId(), "Path should end with Jane");

        network.removeConnection(2, 3);
        assertNull(network.shortestPath(1, 3), "No path should exist");

        assertThrows(IllegalArgumentException.class, () -> network.shortestPath(1, 4), "Non-existent person should throw exception");
    }
}