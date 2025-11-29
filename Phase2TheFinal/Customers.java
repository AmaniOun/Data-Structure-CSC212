
package project;

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
    
    private AVL_Tree<Integer, Orders> orders = new AVL_Tree<>();
    
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
    
    public AVL_Tree<Integer, Orders> getOrders() {
    return orders;
}
    
    // addOrder method
    public void addOrder(Orders orderObj) {
        try {
            orders.insert(orderObj.getOrderId(), orderObj);
        } catch (Exception e) {
            System.out.println("Error while adding order.");
        }
    }
    
    public boolean removeOrder(int orderId) {
        try {
            return orders.remove(orderId);
        } catch (Exception e) {
            System.out.println("Error while removing order.");
            return false;
        }
    }
    
    public void registerCustomer() {
        try {
        System.out.print("Enter Customer ID: ");
        int customerId = input.nextInt();
        input.nextLine(); 

        // Check if ID already exists
        while (customerExists(customerId)) {
            System.out.println("This ID already exists. Try again.");
            customerId = input.nextInt();
            input.nextLine(); 

        }

        System.out.print("Enter Customer Name: ");
        String name = input.nextLine();

        System.out.print("Enter Customer Email: ");
        String email = input.nextLine();

        // Create and add customer
        Customers c = new Customers(customerId, name, email);
            customers.insert(customerId, c);
        
        System.out.println("Customer added successfully.");
        
    } catch(Exception e){
        System.out.println(" Invalid input, please try again.");
        input.nextLine(); 

                }
    }
    
     //Place a new order for a specific customer
     public void placeOrderForCustomer(AVL_Tree<Integer, Products> products, AVL_Tree<Integer, Orders> ordersTree) {
            Customers customer = findCustomerById();
            if (customer == null) {
                System.out.println("Customer not found. Cannot place order.");
                return;
            }

            // Create new order 
            Orders orderHandler = new Orders();
            Orders newOrder = orderHandler.createOrder(customer.getCustomerId(), products);

            if (newOrder != null) {
                try{
                customer.getOrders().insert(newOrder.getOrderId(), newOrder);

                ordersTree.insert(newOrder.getOrderId(), newOrder);
                
                System.out.println("Order successfully placed for customer " + customer.getCustomerId());
                System.out.println(newOrder);
            } catch (Exception e) {
            System.out.println("An unexpected error occurred while placing the order. Please try again.");
        }
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

            AVL_Tree<Integer, Orders> tree = target.getOrders();
            if (tree.empty()) {

            System.out.println("No orders found for customer.");
            return;
        }

        System.out.println("Order History for Customer " + target.getCustomerId() + ":");

        tree.inOrder(new AVL_Tree.Visitor<Orders>() {
                public void visit(Orders o) {
                    if (o != null)
                        System.out.println("Order ID: " + o.getOrderId());
                }
            });
    } catch (Exception e) {
        System.out.println(" Error occurred while Showing Order history, please try again.");
    }
}
    
    public boolean customerExists(int id) {
        try {
            return customers.findKey(id);
        } catch (Exception e) {
            System.out.println("Error occurred while checking customer ID, please try again.");
            return false;
        }
    }
    
    // Find customer bt ID
    public Customers findCustomerById() {
        while (true) {
            try {
                if (customers.empty()) {
                    System.out.println("No customers available.");
                    return null;
                }

                System.out.print("Enter Customer ID: ");
                int customerId = input.nextInt();

                while (!customerExists(customerId)) {
                    System.out.println("There is no customer with this ID! \nEnter Customer ID again:");
                    customerId = input.nextInt();
                }

                if (customers.findKey(customerId)) {
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
    
    // Load Customers from file
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

                customers.insert(c.getCustomerId(), c);
                count++;
            }

            reader.close();
            System.out.println(" Successfully loaded " + count + " customers from " + fileName);
            
        } catch (Exception e) {
            System.out.println(" Error reading customers file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // List All Customers Sorted Alphabetically.
     public void printCustomersAlphabetically() {
        if (customers.empty()) {
            System.out.println("No customers available.");
            return;
        }

        System.out.println("\n--- Sorting Customers Alphabetically... ---");
        
        AVL_Tree<String, Customers> nameIndexedTree = new AVL_Tree<>();
        
        
        final int[] insertCount = {0};
        customers.inOrder(new AVL_Tree.Visitor<Customers>() {
            public void visit(Customers c) {
              
                String key = c.getCusName().toLowerCase() + "_" + String.format("%010d", c.getCustomerId());
                nameIndexedTree.insert(key, c);
                insertCount[0]++;
            }
        });
        
        System.out.println("Processed " + insertCount[0] + " customers");
        System.out.println("\nAll Customers (Sorted Alphabetically by Name)");
        
        final int[] displayCounter = {0};
        nameIndexedTree.inOrder(new AVL_Tree.Visitor<Customers>() {
            public void visit(Customers c) {
                displayCounter[0]++;
                System.out.printf("%3d. ", displayCounter[0]);
                System.out.println(c);
            }
        });
        
        System.out.println("Total: " + displayCounter[0] + " customers displayed");
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email + ", orders=" + orders ;
    }
     
     
     
     
    }

    

    