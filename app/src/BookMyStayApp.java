import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room class
 */
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

// Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * Room Inventory (Read-only usage here)
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}

/**
 * Main Application Class
 * @version 4.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Search Results\n");

        RoomInventory inventory = new RoomInventory();

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Search (Read-only)
        searchAndDisplay("Single Room", single, inventory);
        searchAndDisplay("Double Room", doubleRoom, inventory);
        searchAndDisplay("Suite Room", suite, inventory);
    }

    public static void searchAndDisplay(String roomType, Room room, RoomInventory inventory) {

        int available = inventory.getAvailability(roomType);

        if (available > 0) {
            System.out.println(roomType + ":");
            room.displayDetails();
            System.out.println("Available Rooms: " + available + "\n");
        }
    }
}