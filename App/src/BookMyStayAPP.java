public class BookMyStayAPP {
import java.util.*;

    public class CancellationService {
        private Stack<String> releasedRoomIds;
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (reservationRoomTypeMap.containsKey(reservationId)) {
                String roomType = reservationRoomTypeMap.get(reservationId);
                releasedRoomIds.push(reservationId);

                Map<String, Integer> availability = inventory.getRoomAvailability();
                inventory.updateAvailability(roomType + "Room", availability.get(roomType + "Room") + 1);

                System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
            } else {
                System.out.println("Invalid cancellation request. Reservation ID not found.");
            }
        }

        public void showRollbackHistory() {
            System.out.println("\nRollback History (Most Recent First):");
            for (String reservationId : releasedRoomIds) {
                System.out.println("Released Reservation ID: " + reservationId);
            }
        }
    }


        public static void main(String[] args) {
            System.out.println("Booking Cancellation");

            RoomInventory inventory = new RoomInventory();
            CancellationService cancellationService = new CancellationService();

            String reservationId = "Single-1";
            cancellationService.registerBooking(reservationId, "Single");

            cancellationService.cancelBooking(reservationId, inventory);
            cancellationService.showRollbackHistory();

            System.out.println("\nUpdated Single Room Availability: " + inventory.getRoomAvailability().get("SingleRoom"));
        }
    }

