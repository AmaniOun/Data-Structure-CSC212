
package com.mycompany.inventoryandordersystem2;


import java.io.File;
import java.util.*;

public class Customers {
    public static Scanner input = new Scanner(System.in);
    public static AVL_Tree<Integer, Customers> customers = new AVL_Tree<>();
    
    public AVL_Tree<Integer, Customers> getCustomerList() {
        return customers;
    }
 
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orders = new LinkedList<>();
    
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
    
    // Add order - O(1) for LinkedList operation
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
    
    // Register customer - O(log n)
    public void registerCustomer() {
        try {
            System.out.print("Enter Customer ID: ");
            int id = input.nextInt();
            input.nextLine(); 

            // Check if ID already exists - O(log n)
            while (customerExists(id)) {
                System.out.println("This ID already exists. Try again.");
                id = input.nextInt();
                input.nextLine(); 
            }

            System.out.print("Enter Customer Name: ");
            String name = input.nextLine();

            System.out.print("Enter Customer Email: ");
            String email = input.nextLine();

            // Create and add customer - O(log n)
            Customers c = new Customers(id, name, email);
            customers.insert(id, c);
            
            System.out.println("Customer added successfully.");
            
        } catch(Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine(); 
        }
    }
    
    // Place order for customer
    public void placeOrderForCustomer(AVL_Tree<Integer, Products> products, AVL_Tree<Integer, Orders> orders) {
        try {
            // Find the customer - O(log n)
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

    // Show order history
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
            System.out.println("Error occurred while showing order history, please try again.");
        }
    }
    
    // Check if customer exists - O(log n)
    public boolean customerExists(int id) {
        try {
            return customers.findKey(id); // O(log n)
        } catch (Exception e) {
            System.out.println("Error occurred while checking customer ID, please try again.");
            return false;
        }
    }

    // Find customer by ID - O(log n)
    public Customers findCustomerById() {
        while (true) {
            try {
                if (customers.empty()) {
                    System.out.println("No customers available.");
                    return null;
                }
                
                System.out.print("Enter Customer ID: ");
                int id = input.nextInt();
                
                while (!customerExists(id)) {
                    System.out.println("There is no customer with this ID! \nEnter Customer ID again:");
                    id = input.nextInt();
                }
                
                // O(log n) search
                if (customers.findKey(id)) {
                    return customers.retrieve();
                }
                
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
    
    // Load customers from file - O(n log n)
    public Customers(String fileName) {
        try {
            File file = new File(fileName);
            
            System.out.println("Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in: (\"user.dir\")");
                return;
            }
            
            System.out.println("File found! Reading customers...");
            
            Scanner reader = new Scanner(file);
            
            // Skip header
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

                customers.insert(c.getCustomerId(), c); // O(log n)
                count++;
            }

            reader.close();
            System.out.println("Successfully loaded " + count + " customers from " + fileName);
            
        } catch (Exception e) {
            System.out.println("Error reading customers file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Print all customers sorted by ID - O(n)
    public void printAllCustomers() {
        if (customers.empty()) {
            System.out.println("No customers available.");
            return;
        }

        System.out.println("\n=== All Customers (Sorted by ID) ===");
        customers.inOrder(new AVL_Tree.Visitor<Customers>() {
            public void visit(Customers c) {
                System.out.println(c);
            }
        });
    }
    
    // IMPROVED: Print customers sorted alphabetically by name - O(n log n)
    // Uses AVL Tree indexed by name instead of bubble sort
    public void printCustomersAlphabetically() {
        if (customers.empty()) {
            System.out.println("No customers available.");
            return;
        }

        System.out.println("\n=== Sorting Customers Alphabetically... ===");
        
        // Create a temporary AVL Tree indexed by customer name
        AVL_Tree<String, Customers> nameIndexedTree = new AVL_Tree<>();
        
        // Populate the name-indexed tree - O(n log n)
        // Each insertion is O(log n), done n times
        final int[] insertCount = {0};
        customers.inOrder(new AVL_Tree.Visitor<Customers>() {
            public void visit(Customers c) {
                // Use name + ID as key to handle duplicate names
                // toLowerCase() ensures case-insensitive alphabetical ordering
                String key = c.getCusName().toLowerCase() + "_" + String.format("%010d", c.getCustomerId());
                nameIndexedTree.insert(key, c);
                insertCount[0]++;
            }
        });
        
        System.out.println("Processed " + insertCount[0] + " customers");
        System.out.println("\n=== All Customers (Sorted Alphabetically by Name) ===");
        System.out.println("=====================================================");
        
        // In-order traversal of name-indexed tree gives alphabetical order - O(n)
        final int[] displayCounter = {0};
        nameIndexedTree.inOrder(new AVL_Tree.Visitor<Customers>() {
            public void visit(Customers c) {
                displayCounter[0]++;
                System.out.printf("%3d. ", displayCounter[0]);
                System.out.println(c);
            }
        });
        
        System.out.println("=====================================================");
        System.out.println("Total: " + displayCounter[0] + " customers displayed");
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