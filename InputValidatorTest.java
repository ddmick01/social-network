
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
public class InputValidatorTest {
    // Test validatePersonInput
    @Test
    public void testValidPersonInput() {
        String input = "P 1 John";
        Person person = InputValidator.validatePersonInput(input);
        assertNotNull(person);
        assertEquals(1, person.getId());
        assertEquals("John", person.getName());
    }
    @Test
    public void testInvalidPersonInputFormat() {
        String input1 = "P 1";
        String input2 = "X 1 John";

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validatePersonInput(input1));
        assertEquals("Invalid person input format: P 1", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validatePersonInput(input2));
        assertEquals("Invalid person input format: X 1 John", exception2.getMessage());
    }
    @Test
    public void testInvalidPersonIdFormat() {
        String input = "P abc John";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> InputValidator.validatePersonInput(input));
        assertEquals("Invalid ID format: abc", exception.getMessage());
    }
    @Test
    public void testEmptyPersonName() {
        String input = "P 1 ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> InputValidator.validatePersonInput(input));
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    // Test validateConnectionInput
    @Test
    public void testValidConnectionInput() {
        String input = "C 1 2";
        int[] connection = InputValidator.validateConnectionInput(input);
        assertNotNull(connection);
        assertEquals(2, connection.length);
        assertEquals(1, connection[0]);
        assertEquals(2, connection[1]);
    }
    @Test
    public void testInvalidConnectionInputFormat() {
        String input1 = "C 1";
        String input2 = "X 1 2";

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validateConnectionInput(input1));
        assertEquals("Invalid connection input format: C 1", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validateConnectionInput(input2));
        assertEquals("Invalid connection input format: X 1 2", exception2.getMessage());
    }
    @Test
    public void testInvalidConnectionIdFormat() {
        String input1 = "C abc 2";
        String input2 = "C 1 abc";

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validateConnectionInput(input1));
        assertEquals("Invalid ID format in connection: C abc 2", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> InputValidator.validateConnectionInput(input2));
        assertEquals("Invalid ID format in connection: C 1 abc", exception2.getMessage());
    }
}

