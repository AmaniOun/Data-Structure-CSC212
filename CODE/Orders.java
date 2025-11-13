package project;
import java.io.File;
import java.util.Scanner;

public class Orders {
    
    public static LinkedList<Orders> orders = new LinkedList<Orders>();
    public static Scanner input = new Scanner(System.in);

    public LinkedList<Orders> getAllOrders() {
        return orders;
    }
    private int orderId;
    private int customerRef;
    private LinkedList<Integer> products = new LinkedList <Integer> ();
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
    public Orders(int orderId, int customerRef, Integer [] ProductIDs, double totalPrice, String orderDate, String status) {
        this.orderId = orderId;
        this.customerRef = customerRef;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        
        for (int i = 0 ; i < ProductIDs.length ; i++)
          products.insert(ProductIDs[i]);
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

    public LinkedList<Integer> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Integer> products) {
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
    
    public void addProduct (Integer product )
    {
        products.insert(product);
    }
    
     public boolean removeProduct( Integer P)
    {
        if ( ! products.empty())
        {
            products.findFirst();
            while(! products.last())
            {
                if (products.retrieve() == P)
                {
                    products.remove();
                    return true;
                }
                else
                    products.findNext();
            }
            if (products.retrieve() == P)
            {
                products.remove();
                return true;
            }
        }
        return false;
    }
     
     public int cancelOrder(int id) {
        try {
        if (orders.empty()) {
            System.out.println("No orders available.");
            return 0;
        }

        orders.findFirst();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.retrieve().getOrderId() == id) {
                if (orders.retrieve().getStatus().equalsIgnoreCase("cancelled")) {
                    System.out.println("Order " + id + " was already cancelled before.");
                    return 2;
                }

                orders.retrieve().setStatus("cancelled");
                System.out.println("Order " + id + " has been cancelled successfully.");
                return 1;
            }
            
            if (!orders.last())
                orders.findNext();
        }

        System.out.println("Order " + id + " not found.");
        return 0;
    } catch (Exception e) {
        System.out.println("Error occurred while cancelling the order. Please try again.");
        return 0;
    }
}

    public boolean UpdateOrder(int orderID) {
        try {
        if (orders.empty()) {
            System.out.println("No orders available.");
            return false;
        }

        orders.findFirst();
        boolean found = false;
        
        for (int i = 0; i < orders.size(); i++) {
            if (orders.retrieve().getOrderId() == orderID) {
                found = true;
                break;
            }
            if (!orders.last())
                orders.findNext();
        }

        if (found) {
            Orders current = orders.retrieve();

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
    } catch (Exception e) {
        System.out.println("Error occurred while updating the order. Please try again.");
        input.nextLine();
        return false;
    }
}

    public static Orders searchById(int id) {
        try {
        if (orders.empty()) 
            return null;

        orders.findFirst();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.retrieve().getOrderId() == id) {
                return orders.retrieve();
            }
            if (!orders.last())
                orders.findNext();
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

        System.out.println("\n Searching for orders between: " + start + " and " + end);
        int foundCount = 0;

        orders.findFirst();
        for (int i = 0; i < orders.size(); i++) {
            Orders current = orders.retrieve();
            

            if (current.getOrderDate().compareTo(start) >= 0 &&
                current.getOrderDate().compareTo(end) <= 0) {
                
                if (result.empty()) {
                    result.insert(current);
                } else {
                    result.findFirst();
                    result.insert(current);
                }
                
                foundCount++;
            }

            if (!orders.last())
                orders.findNext();
        }

        System.out.println("\n Total orders found: " + foundCount);
        return result;
     } catch (Exception e) {
        System.out.println("Error occurred while retrieving orders between dates. Please try again.");
        return new LinkedList<Orders>();
    }
}
    
     
     public boolean checkOrderID(int oid) {
        try {
        if (!orders.empty()) {
            orders.findFirst();
            for (int i = 0; i < orders.size(); i++) {
                if (orders.retrieve().getOrderId() == oid)
                    return true;
                if (!orders.last())
                    orders.findNext();
            }
        }
        return false;
    } catch (Exception e) {
        System.out.println("Error occurred while checking the order ID. Please try again.");
        return false;
    }
    }
     
     
         public Orders(String fileName) {
        try {
            File file = new File(fileName);
            
            System.out.println("Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in: (\"user.dir\")" );
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
                orders.insert(o);
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


