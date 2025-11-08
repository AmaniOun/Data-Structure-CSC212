package project;

import java.io.File;
import java.util.Scanner;

public class CustomerData {

    public static LinkedList<Customer> customers = new LinkedList<Customer>();
    public static Scanner input = new Scanner(System.in);

    public LinkedList<Customer> getCustomerList() {
        return customers;
    }

    // Read customers from CSV file
    public CustomerData(String fileName) {
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            String line = reader.nextLine();

            if (reader.hasNext())
                reader.nextLine();

            while (reader.hasNext()) {
                String row = reader.nextLine();
                String[] info = row.split(",");

                Customer c = new Customer(
                        Integer.parseInt(info[0]), info[1], info[2]
                );

                customers.insert(c);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading customers file: " + e.getMessage());
        }
    }

    public void registerCustomer() {
        
    System.out.print("Enter Customer ID: ");
    int id = input.nextInt();
    input.nextLine(); // clear buffer

    // Check if ID already exists
    if (customerExists(id)) {
        System.out.println("This ID already exists. Try again.");
        return;
    }

    System.out.print("Enter Customer Name: ");
    String name = input.nextLine();

    System.out.print("Enter Customer Email: ");
    String email = input.nextLine();

    // Create and add customer
    Customer c = new Customer(id, name, email);
    customers.insert(c);

    System.out.println("Customer added successfully.");
}

    public void showOrderHistory() {

    if (customers.empty()) {
        System.out.println("No customers in the system.");
        return;
    }

    Customer target = findCustomerById();

    if (target == null) {
        System.out.println("Customer not found.");
        return;
    }

    LinkedList<Integer> orderList = target.getOrders();

    if (orderList.empty()) {
        System.out.println("No orders found for customer.");
        return;
    }

    System.out.println("Order History for Customer " + target.getCustomerId() + ":");

    orderList.findFirst();
    while (!orderList.last()) {
        System.out.println("Order ID: " + orderList.retrieve());
        orderList.findNext();
    }
    System.out.println("Order ID: " + orderList.retrieve());
    }


    public boolean customerExists(int id) {
        if (customers.empty())
            return false;

        customers.findFirst();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.retrieve().getCustomerId() == id)
                return true;

            customers.findNext();
        }
        return false;
    }

    public Customer findCustomerById() {
    if (customers.empty()) {
        System.out.println("No customers available.");
        return null;
    }

    System.out.print("Enter Customer ID: ");
    int id = input.nextInt();

    customers.findFirst();
    while (!customers.last()) {
        Customer current = customers.retrieve();
        if (current.getCustomerId() == id)
            return current;

        customers.findNext();
    }

    if (customers.retrieve().getCustomerId() == id)
        return customers.retrieve();

    System.out.println("There is no customer with this ID.");
    return null;
}



    public void printAllCustomers() {
        if (customers.empty()) {
            System.out.println("No customers available.");
            return;
        }

        customers.findFirst();
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.retrieve());
            customers.findNext();
        }
    }
}
