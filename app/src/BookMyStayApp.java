import java.util.*;

/**
 * Custom Exception for Invalid Booking
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
 * Inventory Service with validation
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 1);
        availabilityMap.put("Double Room", 1);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available < 0) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (available == 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        availabilityMap.put(roomType, available - 1);
    }
}

/**
 * Booking Service with validation
 */
class BookingService {

    public void processBooking(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        String roomType = reservation.getRoomType();

        // Validate room type
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        // Try allocation
        inventory.reduceAvailability(roomType);

        // If success
        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
    }
}

/**
 * Main Class
 * @version 9.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking System with Validation\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Test cases (valid + invalid)
        List<Reservation> requests = Arrays.asList(
                new Reservation("Alice", "Single Room"),
                new Reservation("Bob", "Double Room"),
                new Reservation("Charlie", "Suite Room"), // invalid type
                new Reservation("David", ""),             // empty type
                new Reservation("Eve", "Single Room")     // no availability
        );

        for (Reservation r : requests) {
            try {
                System.out.println("\nProcessing request for: " + r.getGuestName());

                service.processBooking(r, inventory);

            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
        double total = manager.calculateTotalCost(reservationId);

        System.out.println("\nSystem continues running safely...");
    }
}import java.util.*;

/**
 * Inventory Service
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap = new HashMap<>();

    public RoomInventory() {
        availabilityMap.put("Single Room", 1);
        availabilityMap.put("Double Room", 1);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void reduce(String roomType) {
        availabilityMap.put(roomType, availabilityMap.get(roomType) - 1);
    }

    public void increase(String roomType) {
        availabilityMap.put(roomType, availabilityMap.get(roomType) + 1);
    }
}

/**
 * Booking Service
 */
class BookingService {

    private Map<String, String> bookings = new HashMap<>(); // reservationId → roomType

    public String bookRoom(String guestName, String roomType, RoomInventory inventory) {

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking Failed for " + guestName);
            return null;
        }

        String reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 4);

        inventory.reduce(roomType);
        bookings.put(reservationId, roomType);

        System.out.println("Booking Confirmed: " + guestName + " → " + reservationId);

        return reservationId;
    }

    public Map<String, String> getBookings() {
        return bookings;
    }
}

/**
 * Cancellation Service
 */
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingService bookingService,
                              RoomInventory inventory) {

        Map<String, String> bookings = bookingService.getBookings();

        // Validate
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        String roomType = bookings.get(reservationId);

        // Rollback steps
        rollbackStack.push(reservationId); // track rollback
        inventory.increase(roomType);      // restore inventory
        bookings.remove(reservationId);    // remove booking

        System.out.println("Cancellation Successful for " + reservationId);
        System.out.println("Room restored to inventory: " + roomType);
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (LIFO):");
        while (!rollbackStack.isEmpty()) {
            System.out.println(rollbackStack.pop());
        }
    }
}

/**
 * Main Class
 * @version 10.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation & Rollback System\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();
        CancellationService cancellationService = new CancellationService();

        // Step 1: Book rooms
        String r1 = bookingService.bookRoom("Alice", "Single Room", inventory);
        String r2 = bookingService.bookRoom("Bob", "Double Room", inventory);

        // Step 2: Cancel booking
        System.out.println("\n--- Cancellation Process ---");
        cancellationService.cancelBooking(r1, bookingService, inventory);

        // Step 3: Invalid cancellation
        cancellationService.cancelBooking("INVALID", bookingService, inventory);

        // Step 4: Show rollback history
        cancellationService.showRollbackHistory();
    }
}