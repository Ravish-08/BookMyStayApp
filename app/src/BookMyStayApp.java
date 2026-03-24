import java.util.*;

/**
 * Reservation class
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Inventory Service
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        availabilityMap.put(roomType, availabilityMap.get(roomType) - 1);
    }
}

/**
 * Booking Service
 */
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public void processBookings(Queue<Reservation> queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation req = queue.poll(); // FIFO
            String roomType = req.getRoomType();

            System.out.println("\nProcessing request for: " + req.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allocatedRoomIds.add(roomId);

                // Store allocation
                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                // Update inventory
                inventory.reduceAvailability(roomType);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + req.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed for " + req.getGuestName() + " (No rooms available)");
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}

/**
 * Main Class
 * @version 6.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation System\n");

        // Create Queue
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Double Room"));
        queue.add(new Reservation("Charlie", "Single Room"));
        queue.add(new Reservation("David", "Suite Room"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking Service
        BookingService service = new BookingService();

        // Process bookings
        service.processBookings(queue, inventory);
    }
}