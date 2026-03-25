public class BookMyStayAPP {
import java.util.*;

    public class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    public class AddOnServiceManager {
        private Map<String, List<AddOnService>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, AddOnService service) {
            servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
            servicesByReservation.get(reservationId).add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            List<AddOnService> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
            double total = 0;
            for (AddOnService service : services) {
                total += service.getCost();
            }
            return total;
        }
    }

    public class UseCase7AddOnServiceSelection {
        public static void main(String[] args) {
            System.out.println("Add-On Service Selection");


