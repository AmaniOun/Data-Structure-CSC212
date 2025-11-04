package project;

public class Order {
    int orderId;
    int customerRef;
    LinkedList<Integer> products = new LinkedList <Integer> ();
    double totalPrice;
    String orderDate; 
    String status;
    
    public Order() {
        this.orderId = 0;
        this.customerRef = 0;
        this.totalPrice = 0;
        this.status = "";
    }

    // Constructor
    public Order(int orderId, int customerRef, Integer [] ProductIDs, double totalPrice, String orderDate, String status) {
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


    // Create Order
    // Creates a new order by inserting it into the list
    public static void createOrder(LinkedList<Order> list, Order order) {
        list.insert(order);
    }

    // Cancel Order
    // Removes the order if found in the list
    public static boolean cancelOrder(LinkedList<Order> list, int id) {
        if (list.empty()) return false;

        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getOrderId() == id) {
                list.remove();
                return true;
            }
            list.findNext();
        }

        if (list.retrieve().getOrderId() == id) {
            list.remove();
            return true;
        }

        return false;
    }

    // Update Order Status
    // Finds the order by ID and updates its status
    public static boolean updateStatus(LinkedList<Order> list, int id, String newStatus) {
        if (list.empty()) return false;

        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getOrderId() == id) {
                list.retrieve().setStatus(newStatus);
                return true;
            }
            list.findNext();
        }

        if (list.retrieve().getOrderId() == id) {
            list.retrieve().setStatus(newStatus);
            return true;
        }

        return false;
    }

    // Search Order by ID
    // Returns the order if found, otherwise returns null
    public static Order searchById(LinkedList<Order> list, int id) {
        if (list.empty()) return null;

        list.findFirst();
        while (!list.last()) {
            if (list.retrieve().getOrderId() == id) {
                return list.retrieve();
            }
            list.findNext();
        }

        if (list.retrieve().getOrderId() == id) {
            return list.retrieve();
        }

        return null;
    }

    public String toString() {
        return "Order ID: " + orderId + ", Customer: " + customerRef + ", Total: " + totalPrice +
        ", Date: " + orderDate + ", Status: " + status;
    }
}
