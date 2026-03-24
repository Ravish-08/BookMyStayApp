import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation class represents a booking request
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

/**
 * Main Application Class
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue (First-Come-First-Served)\n");

        // Create Queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Add booking requests (FIFO order)
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));

        // Display queue
        System.out.println("Current Booking Requests:\n");

        for (Reservation r : bookingQueue) {
            r.displayReservation();
        }

        System.out.println("\nAll requests are queued and waiting for processing...");
    }
}