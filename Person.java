import java.util.*;
public class Person {
    private final int id;
    private final String name;
    private final Set<Person> connections;
    //changed to set to prevent dupes
    public Person(int id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.connections = new HashSet<>();
    }

    public void addConnection(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot add null connection");
        }
        if (this.equals(person)) {
            throw new IllegalArgumentException("Self-connection is not allowed");
        }
        connections.add(person);
        person.connections.add(this);
    }

    public void removeConnection(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot remove null connection");
        }
        connections.remove(person);
        person.connections.remove(this);
    }

    public int getNumConnections() {
        return connections.size();
    }

    public Set<Person> getConnections() {
        //unmodifiableSet so that the set will not be changed when calculations are being done on elements
        return Collections.unmodifiableSet(connections);
    }

    public int getInfluence() {
        //changed to streams and maps
        return connections.stream()
                .mapToInt(Person::getNumConnections)
                .sum() + connections.size();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    //helper methods for use
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "'}";
    }
}
class PersonComparator implements java.util.Comparator<Person> {
    public int compare(Person a, Person b) {
        return a.getInfluence() - b.getInfluence();
    }
}
