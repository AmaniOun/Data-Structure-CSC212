package project;

import java.io.File;
import java.util.Scanner;

public class OrderData {

    // Linked list to store all orders loaded from file
    private LinkedList<Order> orders = new LinkedList<>();
    
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

    // Returns the linked list containing all orders read from file
    public LinkedList<Order> getAllOrders() {
        return orders;
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
}
