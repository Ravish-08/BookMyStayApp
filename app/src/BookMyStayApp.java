import java.util.*;

/**
 * Service class (Add-On)
 */
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }
}

/**
 * Add-On Service Manager
 */
class AddOnServiceManager {

    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Services:");
        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        double total = 0;
        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }
}

/**
 * Main Class
 * @version 7.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        // Assume reservation already exists
        String reservationId = "RES123";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1000));
        manager.addService(reservationId, new Service("Spa Access", 1500));

        // Display selected services
        manager.displayServices(reservationId);

        // Show total cost
        double total = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + total);
    }
}