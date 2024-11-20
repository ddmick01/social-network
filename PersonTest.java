import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class PersonTest {
    private Person person1, person2, person3;

    @BeforeEach
    public void setUp() {
        person1 = new Person(1, "Dom");
        person2 = new Person(2, "John");
        person3 = new Person(3, "Jane");
    }

    // Test Constructor
    @Test
    public void testValidConstructor() {
        Person person = new Person(0, "Test");
        assertEquals(0, person.getId());
        assertEquals("Test", person.getName());
    }

    // Test Invalid Constructor
    @Test
    public void testInvalidConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Person(-1, "Test"));
        assertThrows(IllegalArgumentException.class, () -> new Person(1, ""));
        assertThrows(IllegalArgumentException.class, () -> new Person(1, null));
    }

    // Tests for addConnection
    @Test
    public void testAddConnection() {
        person1.addConnection(person2);
        assertTrue(person1.getConnections().contains(person2), "Dom should be connected to John");
        assertTrue(person2.getConnections().contains(person1), "John should be connected to Dom");
    }
    @Test
    public void testAddNullConnection() {
        assertThrows(IllegalArgumentException.class, () -> person1.addConnection(null));
    }
    @Test
    public void testAddSelfConnection() {
        assertThrows(IllegalArgumentException.class, () -> person1.addConnection(person1));
    }
    @Test
    public void testAddDuplicateConnection() {
        person1.addConnection(person2);
        person1.addConnection(person2); // Duplicate add
        assertEquals(1, person1.getNumConnections());
    }

    // Tests for removeConnection
    @Test
    public void testRemoveConnection() {
        person1.addConnection(person2);
        person1.removeConnection(person2);
        assertFalse(person1.getConnections().contains(person2), "Dom should not be connected to John anymore");
        assertFalse(person2.getConnections().contains(person1), "John should not be connected to Dom anymore");
    }
    @Test
    public void testRemoveNonExistingConnection() {
        person1.removeConnection(person3);
        assertEquals(0, person1.getNumConnections());
    }
    @Test
    public void testRemoveNullConnection() {
        assertThrows(IllegalArgumentException.class, () -> person1.removeConnection(null));
    }

    // Test for getNumConnections
    @Test
    public void testGetNumConnections() {
        person1.addConnection(person2);
        person1.addConnection(person3);
        assertEquals(2, person1.getNumConnections(), "Dom should have 2 connections");
    }

    // Test for getConnections
    @Test
    public void testGetConnections() {
        assertTrue(person1.getConnections().isEmpty());
        person1.addConnection(person2);
        Set<Person> connections = person1.getConnections();
        assertEquals(1, connections.size());
        assertTrue(connections.contains(person2));
        assertThrows(UnsupportedOperationException.class, () -> connections.add(person3));
    }

    // Test for getInfluence
    @Test
    public void testGetInfluence() {
        assertEquals(0, person1.getInfluence());
        person1.addConnection(person2);
        assertEquals(2, person1.getInfluence());
        person2.addConnection(person3);
        assertEquals(4, person1.getInfluence());
    }
}
