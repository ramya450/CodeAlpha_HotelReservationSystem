import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String type;
    double price;
    boolean available;

    Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = true;
    }

    void displayRoom() {
        System.out.println("----------------------------------------");
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Room Type   : " + type);
        System.out.printf("Price/Night : %.2f%n", price);
        System.out.println("Status      : " + (available ? "Available" : "Reserved"));
    }
}

class Reservation {
    int reservationId;
    String guestName;
    int roomNumber;
    int nights;
    double totalCost;

    Reservation(int reservationId, String guestName, int roomNumber,
                int nights, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    void displayReservation() {
        System.out.println("----------------------------------------");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Nights         : " + nights);
        System.out.printf("Total Cost     : %.2f%n", totalCost);
    }
}

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static int nextReservationId = 1001;

    public static void main(String[] args) {

        addDefaultRooms();

        int choice;

        do {
            displayMenu();

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayRooms();
                    break;

                case 2:
                    makeReservation();
                    break;

                case 3:
                    viewReservations();
                    break;

                case 4:
                    cancelReservation();
                    break;

                case 5:
                    searchAvailableRooms();
                    break;

                case 6:
                    System.out.println("\nThank you for using Hotel Reservation System!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 6);

        scanner.close();
    }

    static void addDefaultRooms() {

        rooms.add(new Room(101, "Single", 1500));
        rooms.add(new Room(102, "Single", 1500));
        rooms.add(new Room(201, "Double", 2500));
        rooms.add(new Room(202, "Double", 2500));
        rooms.add(new Room(301, "Deluxe", 4000));
        rooms.add(new Room(302, "Deluxe", 4000));
    }

    static void displayMenu() {

        System.out.println("\n========================================");
        System.out.println("       HOTEL RESERVATION SYSTEM");
        System.out.println("========================================");
        System.out.println("1. View All Rooms");
        System.out.println("2. Make Reservation");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. Search Available Rooms");
        System.out.println("6. Exit");
        System.out.println("========================================");
    }

    static void displayRooms() {

        System.out.println("\n========== ALL ROOMS ==========");

        for (Room room : rooms) {
            room.displayRoom();
        }

        System.out.println("================================");
    }

    static void makeReservation() {

        System.out.println("\n========== MAKE RESERVATION ==========");

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();

        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Sorry, this room is already reserved.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine();

        if (nights <= 0) {
            System.out.println("Number of nights must be greater than zero.");
            return;
        }

        double totalCost = selectedRoom.price * nights;

        Reservation reservation = new Reservation(
                nextReservationId,
                guestName,
                roomNumber,
                nights,
                totalCost
        );

        reservations.add(reservation);

        selectedRoom.available = false;

        System.out.println("\nReservation successful!");
        System.out.println("Reservation ID : " + nextReservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Number of Nights: " + nights);
        System.out.printf("Total Cost     : %.2f%n", totalCost);

        nextReservationId++;
    }

    static void viewReservations() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations found.");
            return;
        }

        System.out.println("\n========== RESERVATIONS ==========");

        for (Reservation reservation : reservations) {
            reservation.displayReservation();
        }

        System.out.println("==================================");
    }

    static void cancelReservation() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations available to cancel.");
            return;
        }

        System.out.println("\n========== CANCEL RESERVATION ==========");

        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        Reservation reservationToRemove = null;

        for (Reservation reservation : reservations) {

            if (reservation.reservationId == reservationId) {
                reservationToRemove = reservation;
                break;
            }
        }

        if (reservationToRemove == null) {
            System.out.println("Reservation not found.");
            return;
        }

        Room room = findRoom(reservationToRemove.roomNumber);

        if (room != null) {
            room.available = true;
        }

        reservations.remove(reservationToRemove);

        System.out.println("Reservation cancelled successfully.");
    }

    static void searchAvailableRooms() {

        System.out.println("\n====== AVAILABLE ROOMS ======");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }

        System.out.println("==============================");
    }

    static Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                return room;
            }
        }

        return null;
    }
}