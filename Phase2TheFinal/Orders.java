
package project;



import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Orders {
    
    public static AVL_Tree<Integer, Orders> orders = new AVL_Tree<>();
    public static Scanner input = new Scanner(System.in);

    public AVL_Tree<Integer, Orders> getAllOrders() {
        return orders;
    }
    
    private int orderId;
    private int customerRef;
    private AVL_Tree<Integer, Integer> products = new AVL_Tree<>();
    private double totalPrice;
    private String orderDate; 
    private String status;
    
    public Orders() {
        this.orderId = 0;
        this.customerRef = 0;
        this.totalPrice = 0;
        this.status = "";
    }

    // Constructor
    public Orders(int orderId, int customerRef, Integer[] ProductIDs, double totalPrice, String orderDate, String status) {
        this.orderId = orderId;
        this.customerRef = customerRef;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        
        for (int i = 0; i < ProductIDs.length; i++)
            products.insert(ProductIDs[i], ProductIDs[i]);
    }

    // Getters & Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(int customerRef) {
        this.customerRef = customerRef;
    }

     public AVL_Tree<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(AVL_Tree<Integer, Integer> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void addProduct(Integer product) {
        products.insert(product, product);
    }
    
    public boolean removeProduct(Integer productId) {
        return products.remove(productId);
    }
     
    // Create Order 
    public Orders createOrder(int customerId, AVL_Tree<Integer, Products> productsList) {
        try {
            Orders newOrder = new Orders();
            int totalPrice = 0;
            
            System.out.println("Enter order ID: ");
            int oid = input.nextInt();
            while (checkOrderID(oid)) { 
                System.out.println("Order ID is already taken! Enter Order ID again:");
                oid = input.nextInt();
            }
            newOrder.setOrderId(oid);
            
            newOrder.setCustomerRef(customerId);
            
            char answer = 'y';
            while (answer == 'y' || answer == 'Y') {
                System.out.println("Enter product ID:");
                int pid = input.nextInt();
               
                while (!productsList.findKey(pid)) { 
                    System.out.println("Product ID not found! Enter product ID again:");
                    pid = input.nextInt();
                }
                
                Products product = productsList.retrieve();
                if (product.getStock() == 0) {
                    System.out.println("Product out of stock, try another time");
                } else {
                    product.setStock(product.getStock() - 1);
                    productsList.update(pid, product); 
                    System.out.println("Product added to order");
                    
                    newOrder.addProduct(product.getProductId()); 
                    totalPrice += product.getProductPrice();
                }
                
                System.out.println("Do you want to continue adding products? (Y/N)");
                answer = input.next().charAt(0);
            }
            
            newOrder.setTotalPrice(totalPrice);
            
            boolean validDate = false;
            String formattedDate = "";
            
            while (!validDate) {
                try {
                    System.out.println("Enter order date (dd/MM/yyyy):");
                    String orderDateInput = input.next();
                    
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    
                    LocalDate date = LocalDate.parse(orderDateInput, inputFormatter);
                    formattedDate = date.format(outputFormatter);
                    
                    validDate = true;
                    System.out.println("Order date set to: " + formattedDate);
                    
                } catch (Exception e) {
                    System.out.println("Invalid date format! Please use (dd/MM/yyyy) format, Please try again.");
                }
            }
            
            newOrder.setOrderDate(formattedDate);
            String status;
            boolean validStatus = false;
            do {
                System.out.println("Enter status (pending, shipped, delivered, cancelled):");
                status = input.next().trim().toLowerCase();
                
                if (status.equals("pending") || status.equals("shipped") || 
                    status.equals("delivered") || status.equals("cancelled")) {
                    validStatus = true;
                } else {
                    System.out.println("Invalid status! Please enter one of: pending, shipped, delivered, cancelled");
                }
            } while (!validStatus);
            
            newOrder.setStatus(status);
            
            orders.insert(oid, newOrder);
            
            System.out.println("Order has been added successfully!");
            return newOrder;
            
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while creating the order. Please try again.");
            return null;
        }
    }
     
    // Cancel order
    public int cancelOrder(int id) {
        try {
            if (orders.empty()) {
                System.out.println("No orders available.");
                return 0;
            }

            if (orders.findKey(id)) {
                Orders order = orders.retrieve();
                
                if (order.getStatus().equalsIgnoreCase("cancelled")) {
                    System.out.println("Order " + id + " was already cancelled before.");
                    return 2;
                }

                order.setStatus("cancelled");
                orders.update(id, order);
                System.out.println("Order " + id + " has been cancelled successfully.");
                return 1;
            }

            System.out.println("Order " + id + " not found.");
            return 0;
        } catch (Exception e) {
            System.out.println("Error occurred while cancelling the order. Please try again.");
            return 0;
        }
    }

    // Update order status
    public boolean UpdateOrder(int orderID) {
        try {
            if (orders.empty()) {
                System.out.println("No orders available.");
                return false;
            }

            if (orders.findKey(orderID)) {
                Orders current = orders.retrieve();

                if (current.getStatus().equalsIgnoreCase("cancelled")) {
                    System.out.println("Could not change status for cancelled order.");
                    return false;
                }

                System.out.println("Current status: " + current.getStatus());
                String[] validStatuses = {"pending", "shipped", "delivered", "cancelled"};
                
                String newStatus;
                
                while (true) {
                    System.out.print("Enter new status (pending / shipped / delivered / cancelled): ");
                    newStatus = input.next().toLowerCase();

                    boolean isValid = false;

                    for (String status : validStatuses) {
                        if (newStatus.equalsIgnoreCase(status)) {
                            isValid = true;
                            break;
                        }
                    }

                    if (isValid) {
                        break; 
                    } else {
                        System.out.println("Invalid status. Please enter a valid status.");
                    }
                }

                current.setStatus(newStatus);
                orders.update(orderID, current);
                System.out.println("Order " + orderID + " status updated to " + newStatus);
                return true;
            }

            System.out.println("No such order found.");
            return false;
        } catch (Exception e) {
            System.out.println("Error occurred while updating the order. Please try again.");
            input.nextLine();
            return false;
        }
    }

    // Search by ID
    public static Orders searchById(int id) {
        try {
            if (orders.empty()) 
                return null;

            if (orders.findKey(id)) {
                return orders.retrieve();
            }
            
            System.out.println("There is no Order with this ID");
            return null;
        } catch (Exception e) {
            System.out.println("Error occurred while searching for the order. Please try again.");
            return null;
        }
    }

    public LinkedList<Orders> getOrdersBetween(String start, String end) {
        try {
            LinkedList<Orders> result = new LinkedList<>();

            if (orders.empty()) {
                System.out.println("No orders in system.");
                return result;
            }

            System.out.println("\nSearching for orders between: " + start + " and " + end);
            final int[] foundCount = {0};

            orders.inOrder(new AVL_Tree.Visitor<Orders>() {
                public void visit(Orders current) {
                    if (current.getOrderDate().compareTo(start) >= 0 &&
                        current.getOrderDate().compareTo(end) <= 0) {
                        
                        if (result.empty()) {
                            result.insert(current);
                        } else {
                            result.findFirst();
                            result.insert(current);
                        }
                        
                        foundCount[0]++;
                    }
                }
            });

            System.out.println("\nTotal orders found: " + foundCount[0]);
            return result;
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving orders between dates. Please try again.");
            return new LinkedList<Orders>();
        }
    }
    
    // Check if order ID exists
    public boolean checkOrderID(int oid) {
        try {
            return orders.findKey(oid);
        } catch (Exception e) {
            System.out.println("Error occurred while checking the order ID. Please try again.");
            return false;
        }
    }
     
    // Load orders from file
    public Orders(String fileName) {
        try {
            File file = new File(fileName);
            
            System.out.println("Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in: (\"user.dir\")");
                return;
            }
            
            System.out.println("File found! Reading...");
            
            Scanner reader = new Scanner(file);

            if (reader.hasNext()) {
                reader.nextLine();
            }

            int count = 0;
            while (reader.hasNext()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] data = line.split(",");

                int orderId = Integer.parseInt(data[0].trim());
                int customerId = Integer.parseInt(data[1].trim());

                String[] productText = data[2].replace("\"", "").split(";");
                Integer[] productIds = new Integer[productText.length];
                for (int i = 0; i < productText.length; i++) {
                    productIds[i] = Integer.parseInt(productText[i].trim());
                }

                double totalPrice = Double.parseDouble(data[3].trim());
                String date = data[4].trim();
                String status = data[5].trim();

                Orders o = new Orders(orderId, customerId, productIds, totalPrice, date, status);
                orders.insert(orderId, o);
                count++;
            }

            reader.close();
            System.out.println("Successfully loaded " + count + " orders from " + fileName);

        } catch (Exception ex) {
            System.out.println("Error loading orders: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String toString() {
        return "Order ID: " + orderId + ", Customer: " + customerRef + ", Total: " + totalPrice +
        ", Date: " + orderDate + ", Status: " + status;
    }
}