import java.util.*;
public class InputValidator {
    //class acts as a barricade to make sure all input is sanitized
    public static Person validatePersonInput(String line) throws IllegalArgumentException {
        String[] parts = line.split(" ");
        if (parts.length != 3 || !parts[0].equals("P")) {
            throw new IllegalArgumentException("Invalid person input format: " + line);
        }
        try {
            int id = Integer.parseInt(parts[1]);
            String name = parts[2];
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            return new Person(id, name);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format: " + parts[1]);
        }
    }

    public static int[] validateConnectionInput(String line) throws IllegalArgumentException {
        String[] parts = line.split(" ");
        if (parts.length != 3 || !parts[0].equals("C")) {
            throw new IllegalArgumentException("Invalid connection input format: " + line);
        }
        try {
            int id1 = Integer.parseInt(parts[1]);
            int id2 = Integer.parseInt(parts[2]);
            return new int[]{id1, id2};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format in connection: " + line);
        }
    }
}
