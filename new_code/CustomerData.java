package com.mycompany.inventoryandordersystem;
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
            
            System.out.println(" Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in: (\"user.dir\")" );
                return;
            }
            
            System.out.println(" File found! Reading customers...");
            
            Scanner reader = new Scanner(file);
            
            // Skip ONLY the header line
            if (reader.hasNext())
                reader.nextLine();

            int count = 0;
            while (reader.hasNext()) {
                String row = reader.nextLine().trim();
                if (row.isEmpty()) continue;
                
                String[] info = row.split(",");

                Customer c = new Customer(
                        Integer.parseInt(info[0].trim()), 
                        info[1].trim(), 
                        info[2].trim()
                );

                customers.insert(c);
                count++;
            }

            reader.close();
            System.out.println(" Successfully loaded " + count + " customers from " + fileName);
            
        } catch (Exception e) {
            System.out.println(" Error reading customers file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registerCustomer() {
        
        System.out.print("Enter Customer ID: ");
        int id = input.nextInt();
        input.nextLine(); 

        // Check if ID already exists
        while (customerExists(id)) {
            System.out.println("This ID already exists. Try again.");
            id = input.nextInt();
            input.nextLine(); 

        }

        System.out.print("Enter Customer Name: ");
        String name = input.nextLine();

        System.out.print("Enter Customer Email: ");
        String email = input.nextLine();

        // Create and add customer
        Customer c = new Customer(id, name, email);
        
        if (customers.empty()) {
            customers.insert(c);
        } else {
            customers.findFirst();
            customers.insert(c);
        }

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

            if (!customers.last())
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
         while (!customerExists(id)) {
         System.out.println("There is no customer with this ID! \n Enter Customer ID again:");
         id = input.nextInt();
         }
            
     

        customers.findFirst();
        while (!customers.last()) {
            Customer current = customers.retrieve();
            if (current.getCustomerId() == id)
                return current;

            customers.findNext();
        }

        if (customers.retrieve().getCustomerId() == id)
            return customers.retrieve();


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
            if (!customers.last())
                customers.findNext();
        }
    }
}