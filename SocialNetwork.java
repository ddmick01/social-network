import java.util.*;
import java.util.stream.*;
public class SocialNetwork {
    private final Map<Integer, Person> people;
    //changed for faster lookup
    public SocialNetwork() {
        this.people = new HashMap<>();
    }

    public void addPerson(int id, String name) {
        //containsKey is a HashMap command that checks if existing key is being entered into map
        if (people.containsKey(id)) {
            throw new IllegalArgumentException("Person with ID: " + id + " already exists");
        }
        Person newPerson = new Person(id, name);
        people.put(id, newPerson);
    }

    public void addConnection(int id1, int id2) {
        Person person1 = getPerson(id1);
        Person person2 = getPerson(id2);
        person1.addConnection(person2);
    }

    public void removeConnection(int id1, int id2) {
        Person person1 = getPerson(id1);
        Person person2 = getPerson(id2);
        person1.removeConnection(person2);
    }

    public List<Person> findInfluencers(int k) {
        if (k <= 0 || k > people.size()) {
            throw new IllegalArgumentException("Invalid number of influencers requested");
        }
        //changed to use streams and to work with hashmap
        return people.values().stream()
                .sorted(Comparator.comparingInt(Person::getInfluence).reversed())
                .limit(k)
                .collect(Collectors.toList());
    }

    public List<Person> shortestPath(int id1, int id2) {
        //kept mostly the same, faster now since using hashmap
        Person start = getPerson(id1);
        Person end = getPerson(id2);
        Queue<Map.Entry<Person, List<Person>>> queue = new LinkedList<>();
        Set<Person> visited = new HashSet<>();

        queue.add(new AbstractMap.SimpleEntry<>(start, new ArrayList<>(Collections.singletonList(start))));

        while (!queue.isEmpty()) {
            Map.Entry<Person, List<Person>> currentEntry = queue.poll();
            Person current = currentEntry.getKey();
            List<Person> path = currentEntry.getValue();
            if (current.equals(end)) {
                return path;
            }
            visited.add(current);
            for (Person neighbor : current.getConnections()) {
                if (!visited.contains(neighbor)) {
                    List<Person> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(new AbstractMap.SimpleEntry<>(neighbor, newPath));
                }
            }
        }
        return null; //if no path exists
    }

    private Person getPerson(int id) {
        //getter method
        Person person = people.get(id);
        if (person == null) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist");
        }
        return person;
    }
}
