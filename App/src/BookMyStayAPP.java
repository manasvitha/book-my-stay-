public class BookMyStayAPP {
    public class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(
                BookingRequestQueue bookingQueue,
                RoomInventory inventory,
                RoomAllocationService allocationService) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation = null;

                synchronized (bookingQueue) {
                    if (bookingQueue.hasPendingRequests()) {
                        reservation = bookingQueue.getNextRequest();
                    }
                }

                if (reservation != null) {
                    synchronized (inventory) {
                        allocationService.allocateRoom(reservation, inventory);
                    }
                }
            }
        }
    }

    public class UseCase11ConcurrentBookingSimulation {
        public static void main(String[] args) {
            RoomInventory inventory = new RoomInventory();
            BookingRequestQueue bookingQueue = new BookingRequestQueue();
            RoomAllocationService allocationService = new RoomAllocationService();

            bookingQueue.addRequest(new Reservation("Abhi", "Single"));
            bookingQueue.addRequest(new Reservation("Subha", "Double"));
            bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));
            bookingQueue.addRequest(new Reservation("Karthik", "Single"));
            bookingQueue.addRequest(new Reservation("Priya", "Suite"));

            ConcurrentBookingProcessor processor1 = new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService);
            ConcurrentBookingProcessor processor2 = new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService);

            Thread t1 = new Thread(processor1);
            Thread t2 = new Thread(processor2);

            t1.start();
            t2.start();
        }
    }


