
package com.mycompany.final_code;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InventoryAndOrderSystem {
    public static Scanner input = new Scanner (System.in);


    public static ProductData pdata = new ProductData("prodcuts.csv");
    public static LinkedList<Product>  products;
    
    public static CustomerData cdata = new CustomerData("customers.csv");
    public  static LinkedList<Customer> customers;
    
    public static OrderData odata = new OrderData("orders.csv");
    public static LinkedList<Order>  orders;
    
    public static ReviewData rdata = new ReviewData("reviews.csv");
    public static LinkedList<Review>  reviews;
    
    // read data from files 
    public static void loadData ()
    {
        System.out.println("loading data from files");
        products = pdata.getproductsData();
        customers = cdata.getCustomerList();
        orders = odata.getAllOrders();
        reviews = rdata.getReviewData();
        
        ///add Orders To Customers
        customers.findFirst();
        for(int i = 0 ; i < customers.size(); i++)
        {
            orders.findFirst();
            for ( int j = 0 ; j < orders.size() ; j ++)
            {
                if (customers.retrieve().getCustomerId() == orders.retrieve().getCustomerRef())
                {
                    int orderid =orders.retrieve().getOrderId();
                    customers.retrieve().addOrder(orderid);
                }
                orders.findNext();
            }
            customers.findNext();
        }
        
        ///add Rviews To products
        products.findFirst();
        for(int i = 0 ; i < products.size(); i++)
        {
            reviews.findFirst();
            for ( int j = 0 ; j < reviews.size() ; j ++)
            {
                if (products.retrieve().getProductId() == reviews.retrieve().getProduct())
                {
                    int rid =reviews.retrieve().getReviewId();
                    products.retrieve().addReview(rid);
                }
                reviews.findNext();
            }
            products.findNext();
        }
        
    }
    
    //Main menu
    public static int MainMenu()
    {
    int choice = -1;
    boolean valid = false;

    do {
        try {
            System.out.println("-------------------------------------------");
            System.out.println("Welcome To Inventory And Order System!");
            System.out.println("Please choose one of the following:");
            System.out.println("1. Products");
            System.out.println("2. Customers");
            System.out.println("3. Orders");
            System.out.println("4. Reviews");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();  
            valid = true; 
        } 
        catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        }
    } while (!valid);

    return choice;
    }
    
//products menu
    public static void productsMenu()
{
    int choice = -1;
    boolean valid = false;

    do {
        try {
            System.out.println("1. Add new product");
            System.out.println("2. Remove product");
            System.out.println("3. Update product");
            System.out.println("4. Search By ID ");
            System.out.println("5. Search products by name");
            System.out.println("6. Track all Out of stock products");
            System.out.println("7. Return Main Menu");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            valid = true;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        }
    } while (!valid);

    switch (choice)
    {
        case 1:
                pdata.addProduct();
            break;
        case 2:
            pdata.removeProduct();
            break;
        case 3:
            pdata.updateProduct();
            break;
        case 4:
        {
            Product pro = pdata.searchProducID();
            if (pro != null)
                System.out.println(pro);
        }
        break;
        case 5:
        {
            Product pro = pdata.searchProducName();
            if (pro != null)
                System.out.println(pro);
        }
        break;
        case 6:
            System.out.println("All out of stock products:");
            pdata.OutStockProducts();
            break;
        case 7:
            System.out.println("Return to Main Menu");
            break;
            
        default:
            System.out.println("Invalid input! Please enter a valid number.");
            break;

    }
}
    
    //Customer Menu
    public static void CustomersMenu()
    {
        
        int choice = -1;
    boolean valid = false;

    do {
        try {
        System.out.println("1. Register new customer");
        System.out.println("2. Place New Order for specific customer");
        System.out.println("3. View Order history for specific customer");
        System.out.println("4. Return Main menu");
        System.out.println("Enter your choice");
        
        choice = input.nextInt();
        input.nextLine();
        
        valid = true;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        }
        } while (!valid);
    
        switch (choice)
        {
            case 1:
                cdata.registerCustomer();
                break;
            case 2:
                PlaceOrder();
                break;
            case 3:
                cdata.showOrderHistory();
                break;
            case 4:
                System.out.println("Return to Main Menu");
            break;
            default:
            System.out.println("Invalid input! Please enter a valid number.");
       }
    }

    //Order Menu
    public static void OrdersMenu()
    {
        int choice = -1;
    boolean valid = false;

    do {
        try {
        System.out.println("1. Place New Order");
        System.out.println("2. Cancel Order");
        System.out.println("3. Update Order Status");
        System.out.println("4. Search By ID");
        System.out.println("5. All orders between two dates");
        System.out.println("6. Return Main menu");
        System.out.println("Enter your choice");
        
        choice = input.nextInt();
        input.nextLine();
        
        valid = true;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        }
        } while (!valid);
        
        switch (choice)
        {
            case 1:
                PlaceOrder();
                break;
            case 2:
                CancelOrder();
                break;
            case 3:
            {
                System.out.println("update to new status...");
                if (orders.empty())
                    System.out.println("empty Orders data");
                else
                {
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
            
            case 4:
            {
                if (orders.empty())
                    System.out.println("empty Orders data");
                else
                {
                    int orderID = -1;
                    
                    try {
                    System.out.println("Enter order ID: ");
                    orderID = input.nextInt();
                    } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    input.nextLine();
            return;
        }
                    
            System.out.println(odata.searchById(orderID));
                }
            }
            break;
            
case 5:
{
    System.out.println("\n=== Search Orders by Date Range ===");
    System.out.println("Enter first date(dd/MM/yyyy):");
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
        
      
        LinkedList<Order> result = odata.getOrdersBetween(date1, date2);
        
        if (result.empty()) {
            System.out.println("\n No orders found between " + date1 + " and " + date2);
        } else {
            System.out.println("\n " + result.size() + " orders Found: ");
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
                System.out.println("Return to Main menu");
                break;

            default:
            System.out.println("Invalid input! Please enter a valid number.");
                
                
        }
    }

    //Review Menu
    public static void ReviewsMenu()
    {
        
        int choice = -1;
    boolean valid = false;
    do {
        try {
        System.out.println("1. Add review");
        System.out.println("2. Edit review");
        System.out.println("3. Get an average rating for product");
        System.out.println("4. Extract reviews for a specific customer");
        System.out.println("5. Top 3 products");
        System.out.println("6. Common products with an average rating 4 out of 5 between 2 Customers");
        System.out.println("7. Return Main menu");
        System.out.println("Enter your choice");
        
         choice = input.nextInt();
            input.nextLine();
            valid = true;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.nextLine();
        }
    } while (!valid);
    
        switch (choice)
        {
            case 1:
                AddNewReview();
                break;
            case 2:
                rdata.updateReview();
                break;
            case 3:
            {
                int pid = -1;

            try {
                System.out.println("Enter product ID to Get an average rating:");
                pid = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                input.nextLine();
                return;
            }

                while (!pdata.checkProductID(pid))
                {
                    System.out.println("ID is not available! \nEnter product id again: ");
                try {
                    pid = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    input.nextLine();
                    return;
                }
            }
                float AVG = AVG_Rating(pid);
                System.out.println("Average Rating for " + pid + " is " + AVG);
            }
            break;
             case 4:
                extractCustomerReviews();
                break;
                
            case 5:
                top3Products();
                break;
            case 6:
            {
                Customer cid1 =cdata.findCustomerById();
                Customer cid2 =cdata.findCustomerById();
                
                if (cid1 == null || cid2 == null) {
        System.out.println("Invalid customer ID(s). Please try again.");
        break;
    }
                
                commonProducts(cid1.getCustomerId(), cid2.getCustomerId());
            }
            break;
            case 7:
                break;
            default:
                System.out.println("Return to Main Menu");
        }
    }
    
    // Add new Review
public static void AddNewReview()
{
    int cID = -1;
    int pID = -1;
    boolean valid = false;

    // Customer ID input
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

    while (!cdata.customerExists(cID))
    {
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

    // Product ID input
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

    while (!pdata.checkProductID(pID))
    {
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

    Review review = rdata.AddReview(cID, pID);
    System.out.println("New Review (" + review.getReviewId() 
            + ") added for " + review.getProduct() 
            + " by customer (" + review.getCustomer() + "), Rate(" 
            + review.getRating() + ") with comment: " + review.getComment() + ".");
}

    
    public static void PlaceOrder(){
        try {
        Order new_order = new Order ();
        int total_price = 0;
        
        System.out.println("Enter order ID: ");
        int oid = input.nextInt();
        while (odata.checkOrderID(oid))
        {
            System.out.println("order id is available!  \n Enter Order ID again:");
            oid = input.nextInt();
        }
        new_order.setOrderId(oid);
        
        System.out.println("Enter customer ID:");
        int cid = input.nextInt();
        while(! cdata.customerExists(cid))
        {
            System.out.println("customer ID is not available! \n Enter customer ID again:");
            cid = input.nextInt();
        }
        new_order.setCustomerRef(cid);
        
        char answer = 'y';
        while (answer == 'y' || answer == 'Y')
        {
            System.out.println("Enter product ID:");
            int pid = input.nextInt();
           

            while (!pdata.checkProductID(pid))
            {
                System.out.println("Product ID not found! \n Enter product ID again:");
                pid = input.nextInt();
            }
            
            boolean found = false;
            
            products.findFirst();
            for ( int i = 0 ;  i < products.size() ;i++)
            {
                if (products.retrieve().getProductId() == pid)
                {
                    if (products.retrieve().getStock() == 0)
                        System.out.println("product out stock , try another time");
                    else
                    {
                        products.retrieve().setStock(products.retrieve().getStock() - 1);
                        System.out.println("product added to order");
                        found = true;
                        
                        new_order.addProduct(products.retrieve().getProductId());
                        total_price += products.retrieve().getProductPrice();
                    }
                    break;
                }

                if (!products.last())
                    products.findNext();
            }
            
            System.out.println("Do you want to continue adding product? (Y/N)");
            answer = input.next().charAt(0);
        }
        
        new_order.setTotalPrice(total_price);
        
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
                
                validDate = true; // if Date is valid, exit loop
                System.out.println("Order date set to: " + formattedDate);
                
            } catch (Exception e) {
                System.out.println("Invalid date format! Please use dd/MM/yyyy format");
                System.out.println("Please try again.");
            }
        }
      
        System.out.println("Enter status (pending, shipped, delivered, cancelled)....");
        new_order.setStatus(input.next());
        
        orders.insert(new_order);
        
        // add order to customer list
        customers.findFirst();
        for(int i = 0 ; i < customers.size(); i++)
        {
            if (customers.retrieve().getCustomerId() == new_order.getCustomerRef())
            {
                Customer cust = customers.retrieve();
                customers.remove();
                cust.addOrder(oid);
                customers.insert(cust);
                break;
            }
            if (!customers.last())
                customers.findNext();
        }   
        
        System.out.println("Order has been added successfully!");
        System.out.println(orders.retrieve());
} catch (Exception e) {
        System.out.println("An unexpected error occurred while placing the order. Please try again.");
    }
}
    
    //=================================================================
    public static void CancelOrder()
    {
        try {
            System.out.println("Enter order ID to cancel: ");
            int oid = input.nextInt();
            Order cancel_order = odata.searchById(oid);
            while (cancel_order == null)
            {
                System.out.println("Re-enter order id, is not available , try again");
                oid = input.nextInt();
                cancel_order = odata.searchById(oid);
            }
        
            if ( odata.cancelOrder(oid) == 1)
            {
                customers.findFirst();
                for ( int i = 0 ; i < customers.size() ; i++)
                {
                    if (customers.retrieve().getCustomerId() == cancel_order.getCustomerRef() )
                    {
                        Customer cust = customers.retrieve();
                        customers.remove();
                        cust.removeOrder(cancel_order.getOrderId());
                        customers.insert(cust);
                        break;
                    }
                    customers.findNext();
                }
                
                cancel_order.getProducts().findFirst();
                for ( int x = 0 ; x < cancel_order.getProducts().size() ; x++  )
                {
                        products.findFirst();
                        for (int i = 0 ; i < products.size() ; i++)
                        {
                            if (products.retrieve().getProductId() == cancel_order.getProducts().retrieve().byteValue())
                                products.retrieve().addStock(1);
                            products.findNext();
                        }
                        cancel_order.getProducts().findNext();
                }
                
                System.out.println("order (" + cancel_order.getOrderId() + ") had been cancelled") ;
            }    
    } catch (Exception e) {
        System.out.println("An unexpected error occurred while cancelling the order. Please try again.");
    }
}

    
    //Get an average rating for product.
    public static float AVG_Rating(int pid)
    {
        try {
        int counter =0;
        float rate = 0;
        
        reviews.findFirst();
        while (! reviews.last())
        {
            if (reviews.retrieve().getProduct() == pid)
            {
                counter ++;
                rate += reviews.retrieve().getRating();
            }
            reviews.findNext();
        }
        if (reviews.retrieve().getProduct() == pid)
        {
            counter ++;
            rate += reviews.retrieve().getRating();
        }
        
        return (rate/counter);
     } catch (Exception e) {
        System.out.println("An error occurred while calculating the average rating.");
        return 0;
    }
}
    
    //Extract reviews from a specific customer for all products
    public static void extractCustomerReviews() {
        try {
    System.out.print("Enter Customer ID to extract reviews: ");
    int customerId = input.nextInt();
    
    while (!cdata.customerExists(customerId)) {
        System.out.println("Customer ID not found! \nEnter Customer ID again: ");
        customerId = input.nextInt();
    }
    
    LinkedList<Review> customerReviews = rdata.getReviewsByCustomer(customerId);
    
    if (customerReviews.empty()) {
        System.out.println("No reviews found for customer " + customerId);
    } else {
        System.out.println("\n=== Reviews by Customer " + customerId + " ===");
        System.out.println("Total reviews: " + customerReviews.size());
        System.out.println("=====================================");
        
        customerReviews.findFirst();
        for (int i = 0; i < customerReviews.size(); i++) {
            Review r = customerReviews.retrieve();
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

    //top 3 products
    public static void top3Products()
    {
        try {
        PQ_LinkedList<Product> top3 = new PQ_LinkedList<Product> ();
        
        if (!products.empty())
        {
            products.findFirst();
            for (int i = 0 ; i < products.size() ; i++)
            {
                
                Product p = products.retrieve();
                float AVGrating = AVG_Rating (p.productId);
                top3.enqueue(p, AVGrating);
                
                products.findNext();
            }
            
        }
        
        System.out.println("top 3 products by average rating from most to least.");
        for ( int j = 1 ; j <= 3 && top3.length() > 0 ; j++)
        {
            PQ_Element<Product> top = top3.serve();
            System.out.println("Product " + j + "  " + top.data.getProductId() 
                    + " " + top.data.getProductName() + " AVG rate (" + top.priority + ")" );
        }
    } catch (Exception e) {
        System.out.println("An error occurred while retrieving the top 3 products. Please try again.");
    }
}
    
    
    //Given two customers IDs, show a list of common products that have been 
    //reviewed with an average rating of more than 4 out of 5.
    public static void commonProducts( int cid1 , int cid2)
    {
        try {
        LinkedList<Integer> pcustomer1 = new LinkedList<Integer> ();
        LinkedList <Integer> pcustomer2 = new LinkedList <Integer> ();
        
        //find all products for customer1 1 and customer 2 that are reviewd
        reviews = rdata.getReviewData();
        
        if (! reviews.empty())
        {
            reviews.findFirst();
            for (int i =1 ;i <= reviews.size() ; i++)
            {
                if (reviews.retrieve().getCustomer() == cid1 )
                {
                    pcustomer1.findFirst();
                    boolean found1 = false;
                    for (int x = 1; x <= pcustomer1.size() ; x++)
                    {
                        if (pcustomer1.retrieve() == reviews.retrieve().getProduct())
                        {
                            found1 = true;
                            break;
                        }
                        pcustomer1.findNext();
                    }
                    pcustomer1.findFirst();
                    if (! found1 )
                        pcustomer1.insert(reviews.retrieve().getProduct());
                }
                
                if (reviews.retrieve().getCustomer() == cid2 )
                {
                    pcustomer2.findFirst();
                    boolean found2 = false;
                    for (int x = 1; x <= pcustomer2.size() ; x++)
                    {
                        if (pcustomer2.retrieve() == reviews.retrieve().getProduct())
                        {
                            found2 = true;
                            break;
                        }
                        pcustomer2.findNext();
                    }
                    
                    pcustomer2.findFirst();
                    if (! found2 )
                        pcustomer2.insert(reviews.retrieve().getProduct());
                }
                reviews.findNext();
            }
            
            pcustomer1.print();
            pcustomer2.print();
            
            // find common products for both lists
            // add common after finding avg rate > 4 in new linked list
            PQ_LinkedList<Product> AVGrate45 = new PQ_LinkedList<Product> ();
            if (! pcustomer1.empty() && ! pcustomer2.empty())
            {
                pcustomer1.findFirst();
                for ( int m =1; m <= pcustomer1.size() ; m++)
                {
                    int pID = pcustomer1.retrieve();
                    pcustomer2.findFirst();
                    for (int n = 1 ; n <= pcustomer2.size() ; n++)
                    {
                        if ( pID == pcustomer2.retrieve())
                        {
                            float AVGrating = AVG_Rating (pID);
                            if ( AVGrating >= 4)
                            {
                                Product p = pdata.getProductData(pID);
                                AVGrate45.enqueue(p, AVGrating);
                            }
                        }
                        pcustomer2.findNext();
                    }
                    pcustomer1.findNext();
                }                
            
                // printing common products
                System.out.println("Common Products with rate above 4 are ");
                while (AVGrate45.length() > 0)
                {
                    PQ_Element<Product> product_rate = AVGrate45.serve();
                    System.out.println(" Product (" + product_rate.data.productId + ") " + product_rate.data.getProductName() + " with rate " + product_rate.priority );
                    System.out.println(product_rate.data);
                    System.out.println("\n");
                }
            }
            else
                System.out.println("NO COMMON products between the two customers ");
        }
        else
            System.out.println("Reviews not available for all products");
    } catch (Exception e) {
        System.out.println("An error occurred while finding common products. Please try again.");
    }
}
    
    
    public static void main(String[] args) {

        
        loadData();
        int choice;
        do {
                choice = MainMenu();
               switch (choice)
                {
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
                        break;
                    default:
                        System.out.println("Try again!!!");
                }
        }while (choice != 5);
    } 
    

}
