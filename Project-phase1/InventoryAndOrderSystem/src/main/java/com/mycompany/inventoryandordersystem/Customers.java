
package com.mycompany.inventoryandordersystem;

import java.io.File;
import java.util.*;


public class Customers {
 public static Scanner input = new Scanner(System.in);
 public static LinkedList<Customers> customers = new LinkedList<Customers>();
 
 public LinkedList<Customers> getCustomerList() {
        return customers;
    }
 
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orders = new LinkedList<Integer>();
    
    public Customers() {
        this.customerId = 0;
        this.name = "";
        this.email = "";
    }
    
    public Customers(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getCusName() {
        return name;
    }
    
    public void setCusName(String name) {
        this.name = name;
    }
    
    public String getCusEmail() {
        return email;
    }
    
    public void setCusEmail(String email) {
        this.email = email;
    }
    
    public LinkedList<Integer> getOrders() {
        return orders;
    }
    
    // addOrder method
    public void addOrder(Integer orderId) {
        if (orders.empty()) {
            orders.insert(orderId);
            return;
        }

        orders.findFirst();
        while (!orders.last()) {
            orders.findNext();
        }
        
        orders.insert(orderId);
    }
    
    public boolean removeOrder(Integer target) {
        if (orders.empty()) {
            return false;
        }
        
        orders.findFirst();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.retrieve().equals(target)) {
                orders.remove();
                return true;
            }
            if (!orders.last())
                orders.findNext();
        }
        
        return false;
    }
    
    public void registerCustomer() {
        try {
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
        Customers c = new Customers(id, name, email);
        
        if (customers.empty()) {
            customers.insert(c);
        } else {
            customers.findFirst();
            customers.insert(c);
        }
        
        System.out.println("Customer added successfully.");
        
    } catch(Exception e){
        System.out.println(" Invalid input, please try again.");
        input.nextLine(); 

                }
    }
    
     //Place a new order for a specific customer
    public void placeOrderForCustomer(LinkedList<Products> products, LinkedList<Orders> orders) {
        try {
            // Find the customer
            Customers customer = findCustomerById();
            if (customer == null) {
                System.out.println("Customer not found. Cannot place order.");
                return;
            }

            // Create new order 
            Orders orderHandler = new Orders();
            Orders newOrder = orderHandler.createOrder(customer.getCustomerId(), products);
            
            if (newOrder != null) {
                
                customer.addOrder(newOrder.getOrderId());
                System.out.println("Order successfully placed for customer " + customer.getCustomerId());
                System.out.println(newOrder);
            }
            
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while placing the order. Please try again.");
        }
    }

    public void showOrderHistory() {
        try {
        if (customers.empty()) {
            System.out.println("No customers in the system.");
            return;
        }

        Customers target = findCustomerById();

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
    } catch (Exception e) {
        System.out.println(" Error occurred while Showing Order history, please try again.");
    }
}
    
    public boolean customerExists(int id) {
        try {
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
  } catch (Exception e) {
        System.out.println(" Error occurred while checking Customer ID, please try again.");
        return false;
    }
}

    public Customers findCustomerById() {
        while (true) {
        try{
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
            Customers current = customers.retrieve();
            if (current.getCustomerId() == id)
                return current;

            customers.findNext();
        }

        if (customers.retrieve().getCustomerId() == id)
            return customers.retrieve();
        
        System.out.println("Customer not found. Please try again.");
        return null;
            
    } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            input.nextLine();
            return null;
        }
    }
}
    
    
    public Customers(String fileName) {
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

                Customers c = new Customers(
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
    
    @Override
public String toString() {
        String str = "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email;
        
        if (!orders.empty()) {
            str += ", Orders: ";
            orders.findFirst();
            while (!orders.last()) {
                str += orders.retrieve() + " ";
                orders.findNext();
            }
            str += orders.retrieve();
        }
        
        return str;
    }


}
    
    