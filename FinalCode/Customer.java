package project;

public class Customer {
    
    private int customerId;
    private String name;
    private String email;
    private LinkedList<Integer> orders = new LinkedList<Integer>();
    
    public Customer() {
        this.customerId = 0;
        this.name = "";
        this.email = "";
    }
    
    public Customer(int customerId, String name, String email) {
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