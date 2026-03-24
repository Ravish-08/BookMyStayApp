import java.util.*;

/**
 * Reservation class
 */
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * Thread-safe Inventory
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap = new HashMap<>();

    public RoomInventory() {
        availabilityMap.put("Single Room", 1); // Only 1 to test concurrency
    }

    // synchronized → critical section
    public synchronized boolean bookRoom(String roomType) {

        int available = availabilityMap.getOrDefault(roomType, 0);

        if (available > 0) {
            availabilityMap.put(roomType, available - 1);
            return true;
        }
        return false;
    }
}

/**
 * Booking Processor (Thread)
 */
class BookingTask implements Runnable {

    private Reservation reservation;
    private RoomInventory inventory;

    public BookingTask(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() +
                " processing " + reservation.guestName);

        boolean success = inventory.bookRoom(reservation.roomType);

        if (success) {
            System.out.println("✅ Booking confirmed for " + reservation.guestName);
        } else {
            System.out.println("❌ Booking failed for " + reservation.guestName);
        }
    }
}

/**
 * Main Class
 * @version 11.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        RoomInventory inventory = new RoomInventory();

        // Multiple users trying to book same room
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Single Room");
        Reservation r3 = new Reservation("Charlie", "Single Room");

        // Create threads
        Thread t1 = new Thread(new BookingTask(r1, inventory));
        Thread t2 = new Thread(new BookingTask(r2, inventory));
        Thread t3 = new Thread(new BookingTask(r3, inventory));

        // Start threads simultaneously
        t1.start();
        t2.start();
        t3.start();
    }
}