public class BookMyStayAPP {
import java.util.Scanner;

    public class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    public class ReservationValidator {
        public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            if (!roomType.equals("Single") && !roomType.equals("Double") && !roomType.equals("Suite")) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            Map<String, Integer> availability = inventory.getRoomAvailability();
            if (availability.get(roomType + "Room") == null || availability.get(roomType + "Room") <= 0) {
                throw new InvalidBookingException("No rooms available for type: " + roomType);
            }
        }
    }


        public static void main(String[] args) {
            System.out.println("Booking Validation");

            Scanner scanner = new Scanner(System.in);

            RoomInventory inventory = new RoomInventory();
            ReservationValidator validator = new ReservationValidator();
            BookingRequestQueue bookingQueue = new BookingRequestQueue();

            try {
                System.out.print("Enter guest name: ");
                String guestName = scanner.nextLine();

                System.out.print("Enter room type (Single/Double/Suite): ");
                String roomType = scanner.nextLine();

                validator.validate(guestName, roomType, inventory);

                Reservation reservation = new Reservation(guestName, roomType);
                bookingQueue.addRequest(reservation);

                System.out.println("Booking request accepted for Guest: " + guestName + ", Room Type: " + roomType);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            } finally {
                scanner.close();
            }
        }
    }
