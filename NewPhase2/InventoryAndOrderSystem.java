package project;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InventoryAndOrderSystem {
    public static Scanner input = new Scanner(System.in);

    public static Products pdata = new Products("prodcuts.csv");
    public static AVL_Tree<Integer, Products> products;
    
    public static Customers cdata = new Customers("customers.csv");
    public static AVL_Tree<Integer, Customers> customers;
    
    public static Orders odata = new Orders("orders.csv");
    public static AVL_Tree<Integer, Orders> orders;
    
    public static Reviews rdata = new Reviews("reviews.csv");
    public static LinkedList<Reviews> reviews;    
    // Load data from files - O(n log n) for each structure
    public static void loadData() {
        System.out.println("\n=== Loading data from files ===");
        products = pdata.getproductsData();
        customers = cdata.getCustomerList();
        orders = odata.getAllOrders();
        reviews = rdata.getReviewData();
        
        // Add Orders To Customers 
        System.out.println("Linking orders to customers...");
        LinkedList<Customers> custList = customers.toLinkedList();
        LinkedList<Orders> orderList = orders.toLinkedList();
        
        if (!custList.empty() && !orderList.empty()) {
            custList.findFirst();
            for (int i = 0; i < custList.size(); i++) {
                Customers cust = custList.retrieve();
                
                orderList.findFirst();
                for (int j = 0; j < orderList.size(); j++) {
                    if (cust.getCustomerId() == orderList.retrieve().getCustomerRef()) {
                        int orderid = orderList.retrieve().getOrderId();
                        cust.addOrder(orderList.retrieve());
                    }
                    if (!orderList.last())
                        orderList.findNext();
                }
                
                customers.update(cust.getCustomerId(), cust);
                
                if (!custList.last())
                    custList.findNext();
            }
        }
        
        // Add Reviews To Products
        System.out.println("Linking reviews to products...");
        LinkedList<Products> prodList = products.toLinkedList();
        LinkedList<Reviews> reviewList = reviews;
        
        if (!prodList.empty() && !reviewList.empty()) {
            prodList.findFirst();
            for (int i = 0; i < prodList.size(); i++) {
                Products prod = prodList.retrieve();
                
                reviewList.findFirst();
                for (int j = 0; j < reviewList.size(); j++) {
                    if (prod.getProductId() == reviewList.retrieve().getProduct()) {
                        int rid = reviewList.retrieve().getReviewId();
                        prod.addReview(rid);
                    }
                    if (!reviewList.last())
                        reviewList.findNext();
                }
                
                products.update(prod.getProductId(), prod);
                
                if (!prodList.last())
                    prodList.findNext();
            }
        }
        
        System.out.println("=== Data loading complete ===\n");
    }
    
    // Main menu
    public static int MainMenu() {
        int choice = -1;
        boolean valid = false;

        do {
            try {
                System.out.println("\n-----------------------------------------");
                System.out.println("   Inventory And Order System - Phase 2");
                System.out.println("          Using AVL Trees");
                System.out.println("-------------------------------------------");
                System.out.println("1. Products Management Menu");
                System.out.println("2. Customers Management Menu");
                System.out.println("3. Orders Management Menu");
                System.out.println("4. Reviews Management Menu");
                System.out.println("5. Advanced Queries Menu");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                choice = input.nextInt();  
                valid = true; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);

        return choice;
    }
    
    // Products menu
    public static void productsMenu() {
        int choice = -1;
        boolean valid = false;

        do {
            try {
                System.out.println("\n--- Products Menu ---");
                System.out.println("1. Add new product");
                System.out.println("2. Remove product");
                System.out.println("3. Update product");
                System.out.println("4. Search By ID");
                System.out.println("5. Search products by name");
                System.out.println("6. Track all Out of stock products");
                System.out.println("7. View all products sorted");
                System.out.println("8. Return Main Menu");
                System.out.print("Enter your choice: ");

                choice = input.nextInt();
                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);

        switch (choice) {
            case 1:
                pdata.addProduct();
                break;
            case 2:
                pdata.removeProduct();
                break;
            case 3:
                pdata.updateProduct();
                break;
            case 4: {
                Products pro = pdata.searchProducID();
                if (pro != null)
                    System.out.println(pro);
            }
            break;
            case 5: {
                Products pro = pdata.searchProducName();
                if (pro != null)
                    System.out.println(pro);
            }
            break;
            case 6:
                System.out.println("All out of stock products:");
                pdata.OutStockProducts();
                break;
            case 7:
                pdata.printAllProductsSorted();
                break;
            case 8:
                System.out.println("Returning to Main Menu");
                break;
            default:
                System.out.println("Invalid input! Please enter a valid number.");
                break;
        }
    }
    
    // Customer Menu
    public static void CustomersMenu() {
        int choice = -1;
        boolean valid = false;

        do {
            try {
                System.out.println("\n--- Customers Menu ---");
                System.out.println("1. Register new customer");
                System.out.println("2. Place New Order for specific customer");
                System.out.println("3. View Order history for specific customer");
                System.out.println("4. View all customers sorted Alphabetically");
                System.out.println("5. Return Main menu");
                System.out.print("Enter your choice: ");
                
                choice = input.nextInt();
                input.nextLine();
                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);
    
        switch (choice) {
            case 1:
                cdata.registerCustomer();
                break;
            case 2:
                cdata.placeOrderForCustomer(products, orders);
                break;
            case 3:
                cdata.showOrderHistory();
                break;
            case 4:
                cdata.printCustomersAlphabetically();
                break;
            case 5:
                System.out.println("Returning to Main Menu");
                break;
            default:
                System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    // Order Menu
    public static void OrdersMenu() {
        int choice = -1;
        boolean valid = false;

        do {
            try {
                System.out.println("\n=== Orders Menu ===");
                System.out.println("1. Create New Order");
                System.out.println("2. Cancel Order");
                System.out.println("3. Update Order Status");
                System.out.println("4. Search By ID");
                System.out.println("5. All orders between two dates");
                System.out.println("6. Return Main menu");
                System.out.print("Enter your choice: ");
                
                choice = input.nextInt();
                input.nextLine();
                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);
        
        switch (choice) {
            case 1:
                PlaceOrder();
                break;
            case 2:
                CancelOrder();
                break;
            case 3: {
                System.out.println("Update to new status...");
                if (orders.empty())
                    System.out.println("Empty Orders data");
                else {
                    int orderID = -1;
                    try {
                        System.out.println("Enter order ID: ");
                        orderID = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid number.");
                        input.nextLine();
                        return;
                    }
                    odata.UpdateOrder(orderID);
                }
            }
            break;
            
            case 4: {
                if (orders.empty())
                    System.out.println("Empty Orders data");
                else {
                    int orderID = -1;
                    try {
                        System.out.println("Enter order ID: ");
                        orderID = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid number.");
                        input.nextLine();
                        return;
                    }
                    Orders ord = Orders.searchById(orderID);
                    if (ord != null)
                        System.out.println(ord);
                }
            }
            break;
            
            case 5: {
                System.out.println("\n=== Search Orders by Date Range ===");
                System.out.println("Enter first date (dd/MM/yyyy):");
                String date1Input = input.next();
                
                System.out.println("Enter second date (dd/MM/yyyy):");
                String date2Input = input.next();
                
                try {
                    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    
                    LocalDate d1 = LocalDate.parse(date1Input, inputFormat);
                    LocalDate d2 = LocalDate.parse(date2Input, inputFormat);
                    
                    String date1 = d1.format(outputFormat);
                    String date2 = d2.format(outputFormat);
                    
                    LinkedList<Orders> result = odata.getOrdersBetween(date1, date2);
                    
                    if (result.empty()) {
                        System.out.println("\nNo orders found between " + date1 + " and " + date2);
                    } else {
                        System.out.println("\n" + result.size() + " orders Found:");
                        System.out.println("=====================================");
                        
                        result.findFirst();
                        for (int i = 0; i < result.size(); i++) {
                            System.out.println((i+1) + ". " + result.retrieve());
                            if (!result.last())
                                result.findNext();
                        }
                        System.out.println("=====================================");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date format! Please use dd/MM/yyyy");
                    System.out.println("Error: " + e.getMessage());
                }
            }
            break;
            
            case 6:
                System.out.println("Returning to Main menu");
                break;
            default:
                System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    // Review Menu
    public static void ReviewsMenu() {
        int choice = -1;
        boolean valid = false;
        
        do {
            try {
                System.out.println("\n=== Reviews Menu ===");
                System.out.println("1. Add review");
                System.out.println("2. Edit review");
                System.out.println("3. Get average rating for product");
                System.out.println("4. Extract reviews for a specific customer");
                System.out.println("5. Top 3 products");
                System.out.println("6. Common products with rating > 4 between 2 Customers");
                System.out.println("7. Return Main menu");
                System.out.print("Enter your choice: ");
                
                choice = input.nextInt();
                input.nextLine();
                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);
    
        switch (choice) {
            case 1:
                AddNewReview();
                break;
            case 2:
                rdata.updateReview();
                break;
            case 3: {
                int pid = -1;
                try {
                    System.out.println("Enter product ID to get average rating:");
                    pid = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    input.nextLine();
                    return;
                }

                while (!pdata.checkProductID(pid)) {
                    System.out.println("ID is not available! \nEnter product id again: ");
                    try {
                        pid = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid number.");
                        input.nextLine();
                        return;
                    }
                }
                float AVG = Reviews.AVG_Rating(pid);
                System.out.println("Average Rating for product " + pid + " is " + AVG);
            }
            break;
            
            case 4:
                extractCustomerReviews();
                break;
            case 5:
                top3Products();
                break;
            case 6: {
                Customers cid1 = cdata.findCustomerById();
                Customers cid2 = cdata.findCustomerById();
                
                if (cid1 == null || cid2 == null) {
                    System.out.println("Invalid customer ID(s). Please try again.");
                    break;
                }
                
                commonProducts(cid1.getCustomerId(), cid2.getCustomerId());
            }
            break;
            case 7:
                System.out.println("Returning to Main Menu");
                break;
            default:
                System.out.println("Invalid input! Please enter a valid number.");
        }
    }
    
    // NEW: Advanced Queries Menu (Phase 2 Requirements)
    public static void AdvancedQueriesMenu() {
        int choice = -1;
        boolean valid = false;
        
        do {
            try {
                System.out.println("\n========================================");
                System.out.println("   Advanced Queries Menu   ");
                System.out.println("========================================");
                System.out.println("1. Find All Orders Between Two Dates");
                System.out.println("2. List All Products Within a Price Range");
                System.out.println("3. Show Top 3 Most Reviewed/Highest Rated Products");
                System.out.println("4. List All Customers Sorted Alphabetically");
                System.out.println("5. Display All Customers Who Reviewed a Product");
                System.out.println("6. Return Main menu");
                System.out.print("Enter your choice: ");
                
                choice = input.nextInt();
                input.nextLine();
                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);
        
        switch (choice) {
            case 1:
                queryOrdersBetweenDates();
                break;
            case 2:
                queryProductsByPriceRange();
                break;
            case 3:
                top3Products();
                break;
            case 4:
                cdata.printCustomersAlphabetically();
                break;
            case 5:
                queryCustomersByProductReview();
                break;
            case 6:
                System.out.println("Returning to Main Menu");
                break;
            default:
                System.out.println("Invalid input! Please enter a valid number.");
        }
    }
    
    
    // Query 1
   public static void queryOrdersBetweenDates() {
    System.out.println("\n=== Find All Orders Between Two Dates ===");
    System.out.println("Enter first date (dd/MM/yyyy):");
    String date1Input = input.next();
    
    System.out.println("Enter second date (dd/MM/yyyy):");
    String date2Input = input.next();
    
    try {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        LocalDate d1 = LocalDate.parse(date1Input, inputFormat);
        LocalDate d2 = LocalDate.parse(date2Input, inputFormat);
        
        String date1 = d1.format(outputFormat);
        String date2 = d2.format(outputFormat);
        
        LinkedList<Orders> result = odata.getOrdersBetween(date1, date2);
        
        if (result.empty()) {
            System.out.println("\nNo orders found between " + date1 + " and " + date2);
        } else {
            System.out.println("\n" + result.size() + " orders Found:");
            System.out.println("=====================================");
            
            result.findFirst();
            for (int i = 0; i < result.size(); i++) {
                System.out.println((i+1) + ". " + result.retrieve());
                if (!result.last())
                    result.findNext();
            }
            System.out.println("=====================================");
        }
    } catch (Exception e) {
        System.out.println("Invalid date format! Please use dd/MM/yyyy");
    }
}

// Query 2
public static void queryProductsByPriceRange() {
    try {
        System.out.println("\n=== List All Products Within a Price Range ===");
        System.out.println("Enter minimum price:");
        double minPrice = input.nextDouble();
        
        System.out.println("Enter maximum price:");
        double maxPrice = input.nextDouble();
        
        LinkedList<Products> result = pdata.getProductsByPriceRange(minPrice, maxPrice);
        
        if (result.empty()) {
            System.out.println("\nNo products found in price range [" + minPrice + " - " + maxPrice + "]");
        } else {
            System.out.println("\n" + result.size() + " products found:");
            System.out.println("=====================================");
            
            result.findFirst();
            for (int i = 0; i < result.size(); i++) {
                System.out.println((i+1) + ". " + result.retrieve());
                if (!result.last())
                    result.findNext();
            }
            System.out.println("=====================================");
        }
    } catch (Exception e) {
        System.out.println("Invalid input! Please enter valid numbers.");
        input.nextLine();
    }
}

// Query 5
public static void queryCustomersByProductReview() {
    try {
        System.out.println("\n=== Display All Customers Who Reviewed a Product ===");
        System.out.println("Enter Product ID:");
        int productId = input.nextInt();
        
        if (!pdata.checkProductID(productId)) {
            System.out.println("Product ID not found!");
            return;
        }
        
        LinkedList<Reviews> productReviews = rdata.getReviewsByProduct(productId);
        
        if (productReviews.empty()) {
            System.out.println("\nNo reviews found for product " + productId);
        } else {
            System.out.println("\n" + productReviews.size() + " customers reviewed this product:");
            System.out.println("=====================================");
            
            productReviews.findFirst();
            for (int i = 0; i < productReviews.size(); i++) {
                Reviews r = productReviews.retrieve();
                
                if (customers.findKey(r.getCustomer())) {
                    Customers c = customers.retrieve();
                    System.out.println((i+1) + ". Customer: " + c.getCusName() + 
                                     " (ID: " + c.getCustomerId() + ")");
                    System.out.println("   Rating: " + r.getRating() + "/5");
                    System.out.println("   Comment: " + r.getComment());
                    System.out.println("   ---");
                }
                
                if (!productReviews.last())
                    productReviews.findNext();
            }
            System.out.println("=====================================");
        }
    } catch (Exception e) {
        System.out.println("Invalid input!");
        input.nextLine();
    }
}
    
   
    
    // Add new Review
    public static void AddNewReview() {
        int cID = -1;
        int pID = -1;
        boolean valid = false;

        do {
            try {
                System.out.println("Enter customer ID:");
                cID = input.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);

        while (!cdata.customerExists(cID)) {
            valid = false;
            do {
                try {
                    System.out.println("Customer ID not available! \nEnter customer ID again:");
                    cID = input.nextInt();
                    valid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    input.nextLine();
                }
            } while (!valid);
        }

        valid = false;
        do {
            try {
                System.out.println("Enter product ID:");
                pID = input.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
            }
        } while (!valid);

        while (!pdata.checkProductID(pID)) {
            valid = false;
            do {
                try {
                    System.out.println("Product ID not available! \nEnter product ID again:");
                    pID = input.nextInt();
                    valid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    input.nextLine();
                }
            } while (!valid);
        }

        Reviews review = rdata.AddReview(cID, pID);
        if (review != null) {
            System.out.println("New Review (" + review.getReviewId() 
                    + ") added for product " + review.getProduct() 
                    + " by customer (" + review.getCustomer() + "), Rate(" 
                    + review.getRating() + ") with comment: " + review.getComment() + ".");
        }
    }
    
    // Place Order
    public static void PlaceOrder() {
        try {
            System.out.println("Enter customer ID:");
            int cid = input.nextInt();
            while (!cdata.customerExists(cid)) {
                System.out.println("Customer ID is not available! \nEnter customer ID again:");
                cid = input.nextInt();
            }
            
            Orders newOrder = odata.createOrder(cid, products);
            
            if (newOrder != null) {
                if (customers.findKey(newOrder.getCustomerRef())) {
                    Customers cust = customers.retrieve();
                    cust.addOrder(newOrder);
                    customers.update(cust.getCustomerId(), cust);
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while placing the order. Please try again.");
        }
    }
    
    // Cancel Order
     public static void CancelOrder() {
        try {
            System.out.println("Enter order ID to cancel: ");
            int oid = input.nextInt();
            Orders cancel_order = Orders.searchById(oid);
            
            while (cancel_order == null) {
                System.out.println("Re-enter order id, is not available, try again");
                oid = input.nextInt();
                cancel_order = Orders.searchById(oid);
            }
        
            if (odata.cancelOrder(oid) == 1) {

    if (customers.findKey(cancel_order.getCustomerRef())) {
        Customers cust = customers.retrieve();
        cust.removeOrder(cancel_order.getOrderId());
        customers.update(cust.getCustomerId(), cust);
    }

    AVL_Tree<Integer, Integer> tree = cancel_order.getProducts();

    tree.inOrder(new AVL_Tree.Visitor<Integer>() {
        public void visit(Integer prodId) {

            if (products.findKey(prodId)) {
                Products prod = products.retrieve();
                prod.addStock(1);
                products.update(prodId, prod);
            }
        }
    });

    System.out.println("Order (" + cancel_order.getOrderId() + ") has been cancelled");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while cancelling the order. Please try again.");
        }
    }
    
    // Extract customer reviews
    public static void extractCustomerReviews() {
        try {
            System.out.print("Enter Customer ID :");
                 int customerId = input.nextInt();
             while (!cdata.customerExists(customerId)) {
            System.out.println("Customer ID not found! \nEnter Customer ID again: ");
            customerId = input.nextInt();
        }
        
        LinkedList<Reviews> customerReviews = rdata.getReviewsByCustomer(customerId);
        
        if (customerReviews.empty()) {
            System.out.println("No reviews found for customer " + customerId);
        } else {
            System.out.println("\n=== Reviews by Customer " + customerId + " ===");
            System.out.println("Total reviews: " + customerReviews.size());
            System.out.println("=====================================");
            
            customerReviews.findFirst();
            for (int i = 0; i < customerReviews.size(); i++) {
                Reviews r = customerReviews.retrieve();
                System.out.println("\nReview #" + (i + 1));
                System.out.println("  Review ID: " + r.getReviewId());
                System.out.println("  Product ID: " + r.getProduct());
                System.out.println("  Rating: " + r.getRating() + "/5");
                System.out.println("  Comment: " + r.getComment());
                
                if (!customerReviews.last())
                    customerReviews.findNext();
            }
            System.out.println("=====================================");
        }
    } catch (Exception e) {
        System.out.println("An error occurred while extracting customer reviews. Please try again.");
    }
}

// Top 3 products
public static void top3Products() {
    try {
        PQ_LinkedList<Products> top3 = new PQ_LinkedList<>();
        
        if (!products.empty()) {
            LinkedList<Products> prodList = products.toLinkedList();
            
            prodList.findFirst();
            for (int i = 0; i < prodList.size(); i++) {
                Products p = prodList.retrieve();
                float AVGrating = Reviews.AVG_Rating(p.getProductId());
                top3.enqueue(p, AVGrating);
                
                if (!prodList.last())
                    prodList.findNext();
            }
        }
        
        System.out.println("\n=== Top 3 Products by Average Rating ===");
        
        if (top3.length() == 0) {
            System.out.println("No products available to display.");
            return;
        }
        
        for (int j = 1; j <= 3 && top3.length() > 0; j++) {
            PQ_Element<Products> top = top3.serve();
            System.out.println("Product " + j + ": ID=" + top.data.getProductId() 
                    + ", Name=" + top.data.getProductName() 
                    + ", AVG Rating=" + String.format("%.2f", top.priority));
        }
    } catch (Exception e) {
        System.out.println("An error occurred while retrieving the top 3 products. Please try again.");
        e.printStackTrace(); 
    }
}

// Common products with rating > 4 between 2 customers
public static void commonProducts(int cid1, int cid2) {
    try {
        LinkedList<Integer> pcustomer1 = new LinkedList<>();
        LinkedList<Integer> pcustomer2 = new LinkedList<>();
        
        LinkedList<Reviews> reviewList = reviews;
        
        if (!reviewList.empty()) {
            reviewList.findFirst();
            for (int i = 0; i < reviewList.size(); i++) {
                if (reviewList.retrieve().getCustomer() == cid1) {
                    pcustomer1.findFirst();
                    boolean found1 = false;
                    for (int x = 0; x < pcustomer1.size(); x++) {
                        if (pcustomer1.retrieve().equals(reviewList.retrieve().getProduct())) {
                            found1 = true;
                            break;
                        }
                        if (!pcustomer1.last())
                            pcustomer1.findNext();
                    }
                    if (!found1) {
                        if (pcustomer1.empty())
                            pcustomer1.insert(reviewList.retrieve().getProduct());
                        else {
                            pcustomer1.findFirst();
                            pcustomer1.insert(reviewList.retrieve().getProduct());
                        }
                    }
                }
                
                if (reviewList.retrieve().getCustomer() == cid2) {
                    pcustomer2.findFirst();
                    boolean found2 = false;
                    for (int x = 0; x < pcustomer2.size(); x++) {
                        if (pcustomer2.retrieve().equals(reviewList.retrieve().getProduct())) {
                            found2 = true;
                            break;
                        }
                        if (!pcustomer2.last())
                            pcustomer2.findNext();
                    }
                    if (!found2) {
                        if (pcustomer2.empty())
                            pcustomer2.insert(reviewList.retrieve().getProduct());
                        else {
                            pcustomer2.findFirst();
                            pcustomer2.insert(reviewList.retrieve().getProduct());
                        }
                    }
                }
                
                if (!reviewList.last())
                    reviewList.findNext();
            }
            
            System.out.println("\nProducts reviewed by Customer " + cid1 + ":");
            pcustomer1.print();
            System.out.println("Products reviewed by Customer " + cid2 + ":");
            pcustomer2.print();
            
            PQ_LinkedList<Products> AVGrate45 = new PQ_LinkedList<>();
            
            if (!pcustomer1.empty() && !pcustomer2.empty()) {
                pcustomer1.findFirst();
                for (int m = 0; m < pcustomer1.size(); m++) {
                    int pID = pcustomer1.retrieve();
                    
                    pcustomer2.findFirst();
                    for (int n = 0; n < pcustomer2.size(); n++) {
                        if (pID == pcustomer2.retrieve()) {
                            float AVGrating = Reviews.AVG_Rating(pID);
                            if (AVGrating > 4) {
                                Products p = pdata.getProductData(pID);
                                if (p != null)
                                    AVGrate45.enqueue(p, AVGrating);
                            }
                        }
                        if (!pcustomer2.last())
                            pcustomer2.findNext();
                    }
                    
                    if (!pcustomer1.last())
                        pcustomer1.findNext();
                }
                
                System.out.println("\n=== Common Products with Rating > 4 ===");
                if (AVGrate45.length() == 0) {
                    System.out.println("No common products with rating > 4 found.");
                } else {
                    while (AVGrate45.length() > 0) {
                        PQ_Element<Products> product_rate = AVGrate45.serve();
                        System.out.println("Product ID: " + product_rate.data.getProductId() 
                                + ", Name: " + product_rate.data.getProductName() 
                                + ", Rating: " + String.format("%.2f", product_rate.priority));
                        System.out.println(product_rate.data);
                        System.out.println();
                    }
                }
            } else {
                System.out.println("NO COMMON products between the two customers");
            }
        } else {
            System.out.println("Reviews not available for all products");
        }
    } catch (Exception e) {
        System.out.println("An error occurred while finding common products. Please try again.");
    }
}

public static void main(String[] args) {
    loadData();
    int choice;
    
    do {
        choice = MainMenu();
        switch (choice) {
            case 1:
                productsMenu();
                break;
            case 2:
                CustomersMenu();
                break;
            case 3:
                OrdersMenu();
                break;
            case 4:
                ReviewsMenu();
                break;
            case 5:
                AdvancedQueriesMenu();
                break;
            case 6:
                System.out.println("\n=== Thank you for using Our system! ===");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    } while (choice != 6);
}}