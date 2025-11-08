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

    public String toString() {
        return "Order ID: " + orderId + ", Customer: " + customerRef + ", Total: " + totalPrice +
        ", Date: " + orderDate + ", Status: " + status;
    }
}
