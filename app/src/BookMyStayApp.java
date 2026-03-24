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
}