package project;

import java.io.File;
import java.util.Scanner;

public class OrderData {

    // Linked list to store all orders loaded from file
    public static LinkedList<Order> orders = new LinkedList<Order>();
    public static Scanner input = new Scanner (System.in);

    
    // reads orders from CSV file and stores them into the list
    public OrderData(String fileName) {
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            // Skip header line in CSV file
            if (reader.hasNext()) {
                reader.nextLine();
            }

            // Read each line and parse order information
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] data = line.split(",");

                int orderId = Integer.parseInt(data[0].trim());
                int customerId = Integer.parseInt(data[1].trim());

                // Convert product list string into array of integers
                String[] productText = data[2].replace("\"", "").split(";");
                Integer[] productIds = new Integer[productText.length];
                for (int i = 0; i < productText.length; i++) {
                    productIds[i] = Integer.parseInt(productText[i].trim());
                }

                double totalPrice = Double.parseDouble(data[3].trim());
                String date = data[4].trim();
                String status = data[5].trim();

                // Create an Order object and store it in the list
                Order o = new Order(orderId, customerId, productIds, totalPrice, date, status);
                orders.insert(o);
            }

            reader.close();

        } catch (Exception ex) {
            System.out.println("Error loading orders: " + ex.getMessage());
        }
    }
    
    
    // Cancel Order
    // Removes the order if found in the list
    public int cancelOrder(int id) {
    if (orders.empty()) {
        System.out.println("No orders available.");
        return 0; // not found
    }

    orders.findFirst();

    while (!orders.last()) {
        if (orders.retrieve().getOrderId() == id) {

            if (orders.retrieve().getStatus().equalsIgnoreCase("cancelled")) {
                System.out.println("Order " + id + " was already cancelled before.");
                return 2; // cancelled before
            }

            orders.retrieve().setStatus("cancelled");
            System.out.println("Order " + id + " has been cancelled successfully.");
            return 1; // cancelled successfully
        }

        orders.findNext();
    }

    if (orders.retrieve().getOrderId() == id) {
        if (orders.retrieve().getStatus().equalsIgnoreCase("cancelled")) {
            System.out.println("Order " + id + " was already cancelled before.");
            return 2;
        }

        orders.retrieve().setStatus("cancelled");
        System.out.println("Order " + id + " has been cancelled successfully.");
        return 1;
    }

    System.out.println("Order " + id + " not found.");
    return 0; // not found
    }
    
    // Update Order Status
    // Finds the order by ID and updates its status
    public boolean UpdateOrder(int orderID) {
    if (orders.empty()) {
        System.out.println("No orders available.");
        return false;
    }

    boolean found = false;

    orders.findFirst();
    while (!orders.last()) {
        if (orders.retrieve().getOrderId() == orderID) {
            found = true;
            break;
        }
        orders.findNext();
    }

    if (orders.retrieve().getOrderId() == orderID)
        found = true;

    if (found) {
        Order current = orders.retrieve();

        if (current.getStatus().equalsIgnoreCase("cancelled")) {
            System.out.println("Could not change status for cancelled order.");
            return false;
        }

        System.out.println("Current status: " + current.getStatus());
        System.out.print("Enter new status (pending / shipped / delivered / cancelled): ");
        String newStatus = input.next();

        current.setStatus(newStatus);
        System.out.println("Order " + orderID + " status updated to " + newStatus);
        return true;
    }

    System.out.println("No such order found.");
    return false;
}
      
    // Search Order by ID
    // Returns the order if found, otherwise returns null
    public static Order searchById(int id) {
        if (orders.empty()) 
            return null;

        orders.findFirst();
        while (!orders.last()) {
            if (orders.retrieve().getOrderId() == id) {
                return orders.retrieve();
            }
            orders.findNext();
        }

        if (orders.retrieve().getOrderId() == id) {
            return orders.retrieve();
        }
        System.out.println("There is no Order with this ID");
        return null;
    }
    

    // Returns orders that fall between two given dates 
    public LinkedList<Order> getOrdersBetween(String start, String end) {
        LinkedList<Order> result = new LinkedList<>();

        if (orders.empty()) return result;

        orders.findFirst();
        while (true) {
            Order current = orders.retrieve();

            // Add order if its date is within the range
            if (current.getOrderDate().compareTo(start) >= 0 &&
                current.getOrderDate().compareTo(end) <= 0) 
            {
                result.insert(current);
            }

            if (orders.last()) break;
            orders.findNext();
        }

        return result;
    }
    
    public boolean checkOrderID(int oid)
    {
        if (!orders.empty())
        {
            orders.findFirst();
            while (!orders.last())
            {
                if (orders.retrieve().getOrderId()== oid)
                    return true;
                orders.findNext();
            }
            if (orders.retrieve().getOrderId()== oid)
                return true;
        }
        return false;
    }
}
    

