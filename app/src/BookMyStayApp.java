import java.io.*;
import java.util.*;

/**
 * Serializable Inventory
 */
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> availabilityMap = new HashMap<>();

    public RoomInventory() {
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
    }

    public void reduce(String roomType) {
        availabilityMap.put(roomType, availabilityMap.get(roomType) - 1);
    }

    public void display() {
        System.out.println("Inventory: " + availabilityMap);
    }
}

/**
 * Serializable Booking Data
 */
class BookingData implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, String> bookings = new HashMap<>();

    public void addBooking(String id, String roomType) {
        bookings.put(id, roomType);
    }

    public void display() {
        System.out.println("Bookings: " + bookings);
    }
}

/**
 * Persistence Service
 */
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save data
    public static void save(RoomInventory inventory, BookingData bookingData) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(bookingData);

            System.out.println("✅ Data saved successfully");

        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load data
    public static Object[] load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingData bookingData = (BookingData) ois.readObject();

            System.out.println("✅ Data loaded successfully");

            return new Object[]{inventory, bookingData};

        } catch (Exception e) {
            System.out.println("⚠ No previous data found. Starting fresh...");
            return null;
        }
    }
}

/**
 * Main Class
 * @version 12.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Data Persistence & Recovery System\n");

        RoomInventory inventory;
        BookingData bookingData;

        // Step 1: Load existing data
        Object[] data = PersistenceService.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            bookingData = (BookingData) data[1];
        } else {
            inventory = new RoomInventory();
            bookingData = new BookingData();
        }

        // Step 2: Simulate booking
        String reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 4);

        inventory.reduce("Single Room");
        bookingData.addBooking(reservationId, "Single Room");

        System.out.println("\nAfter Booking:");
        inventory.display();
        bookingData.display();

        // Step 3: Save state
        PersistenceService.save(inventory, bookingData);

        System.out.println("\nRestart app to see recovery...");
    }
}
