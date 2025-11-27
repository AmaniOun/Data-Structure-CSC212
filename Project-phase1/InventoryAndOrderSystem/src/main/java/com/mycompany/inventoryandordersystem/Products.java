
package com.mycompany.inventoryandordersystem;

import java.io.File;
import java.util.Scanner;

public class Products {
    
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Products> products = new LinkedList<Products>();

    public LinkedList<Products> getproductsData() {
        return products;
    }
    
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList <Integer> reviews = new LinkedList <Integer> ();

    public Products() {
        this.productId = 0;
        this.name = "";
        this.price = 0;
        this.stock = 0;
    }

    public Products(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public double getProductPrice() {
        return price;
    }

    public void setProductPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addStock ( int stock)
    {
        this.stock += stock;
    }
    
    public void removeStock ( int stock)
    {
        this.stock -= stock;
    }
    
    
    public void addReview( Integer Rating)
    {
        reviews.insert(Rating);
    }
    
    public boolean removeReview( Integer Rating)
    {
        if ( ! reviews.empty())
        {
            reviews.findFirst();
            while(!reviews.last())
            {
                if (reviews.retrieve() == Rating)
                {
                    reviews.remove();
                    return true;
                }
                else
                    reviews.findNext();
            }
            if (reviews.retrieve() == Rating)
            {
                reviews.remove();
                return true;
            }
        }
        return false;
    }
    
    public LinkedList<Integer> getReviews ()
    {
        return reviews;
    }
    
    public Products(String fileName) {
        try {
            File file = new File(fileName);
            
            System.out.println("Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in:(\"user.dir\")");
                return;
            }
            
            System.out.println(" File found! Reading...");
            
            Scanner reader = new Scanner(file);
            String line = reader.nextLine(); 
            
            int count = 0;
            while (reader.hasNext()) {
                line = reader.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] data = line.split(",");
                
                int productId = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                double price = Double.parseDouble(data[2].trim());
                int stock = Integer.parseInt(data[3].trim());

                Products product = new Products(productId, name, price, stock);
                products.insert(product);
                count++;
            }
            reader.close();
            System.out.println("Successfully loaded " + count + " products from " + fileName);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addProduct() {
        try {
        System.out.println("Please enter the product ID:");
        int productId = input.nextInt();
        while (checkProductID(productId)) {
            System.out.println("This ID already exists, please try a different one:");
            productId = input.nextInt();
        }
        
        System.out.println("Enter the product name:");
        String name = input.next();
        
        System.out.println("Enter the price:");
        double price = input.nextDouble();
        
        System.out.println("stock:");
        int stock = input.nextInt();
        
        Products product = new Products(productId, name, price, stock);
        if (products.empty()) {
            products.insert(product);
        } else {
            products.findFirst();
            products.insert(product);
        }
        } catch(Exception e){
        System.out.println(" Invalid input, please try again.");
        input.nextLine(); 

                }
    }
    
    public Products searchProducID() {
        try {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return null;
        }
        
        System.out.println("Please enter the product ID: ");
        int productID = input.nextInt();
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getProductId() == productID) {
                return products.retrieve();
            }
            if (!products.last())
                products.findNext();
        }
        
        System.out.println("Product not found in the system");
        return null;
        
        } catch(Exception e){
        System.out.println(" Invalid input, please try again.");
        input.nextLine(); 
        return null;
                }
    }
        
        
   public Products removeProduct() {
    try {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return null;
        }
        
        System.out.println("Enter the product ID to remove: ");
        int productID = input.nextInt();
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getProductId() == productID) {
                Products removedProduct = products.retrieve();
                products.remove();
                System.out.println("Product " + productID + " has been removed from the system");
                return removedProduct;
            }
            if (!products.last())
                products.findNext();
        }
        
        System.out.println("Product not found in the system");
        return null;
    } catch (Exception e) {
        System.out.println("Invalid input, please try again.");
        input.nextLine();
        return null;
    }
}
   
    public Products updateProduct() {
         try {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return null;
        }
        
        System.out.println("Enter the product ID to update: ");
        int productID = input.nextInt();
        
        products.findFirst();
        boolean found = false;
        
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getProductId() == productID) {
                found = true;
                break;
            }
            if (!products.last())
                products.findNext();
        }
        
        if (found) {
            Products p = products.retrieve();
            
            System.out.println("1. Update product Name");
            System.out.println("2. Update product price");
            System.out.println("3. Update stock");
            System.out.println("Enter your choice");
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("Enter new product name:");
                    p.setProductName(input.next());
                    break;
                case 2:
                    System.out.println("Enter new price:");
                    p.setProductPrice(input.nextDouble());
                    break;
                case 3:
                    System.out.println("Enter new stock quantity:");
                    int stock = input.nextInt();
                    p.setStock(stock);
                    break;
                default:
                    System.out.println("Invalid selection");
            }
            return p;
        }
        
        System.out.println("Product not found in the system");
        return null;
  } catch (Exception e) {
        System.out.println(" Invalid input, please try again.");
        input.nextLine();
        return null;
    }
}

    public Products searchProducName() {
        try {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return null;
        }
        
        System.out.println("Enter product Name to search: ");
        String name = input.nextLine();
        name = input.nextLine();
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getProductName().equalsIgnoreCase(name)) {
                return products.retrieve();
            }
            if (!products.last())
                products.findNext();
        }

        System.out.println("No product found with this name");
        return null;
    } catch (Exception e) {
        System.out.println(" Invalid input, please try again.");
        input.nextLine();
        return null;
    }
}

    public void OutStockProducts() {
        try {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return;
        }
        
         boolean found = false;
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getStock() == 0) {
                System.out.println(products.retrieve());
                found = true;
            }
            if (!products.last())
                products.findNext();
        }
         if (!found) {
        System.out.println("There is NO product out of stock");
    }
    } catch (Exception e) {
        System.out.println(" Error occurred while checking stock, please try again.");
    }
}

    public boolean checkProductID(int productId) {
        try {
        if (!products.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {
                if (products.retrieve().getProductId() == productId)
                    return true;
                if (!products.last())
                    products.findNext();
            }
        }
        return false;
    } catch (Exception e) {
        System.out.println(" Error occurred while checking product ID, please try again.");
        return false;
    }
}


    public Products getProductData(int ProductId) {
        try {
        if (!products.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {
                if (products.retrieve().getProductId() == ProductId) {
                    return products.retrieve();
                }
                if (!products.last())
                    products.findNext();
            }
        }
        return null;
    } catch (Exception e) {
        System.out.println(" Error occurred while retrieving product data, please try again.");
        return null;
    }
    }
    
    
    @Override
    public String toString() {
        String str =  "\nproductId=" + productId + ", name=" + name + ", price=" + price + ", stock=" + stock ;
        if ( ! reviews.empty())
        {
            str += "(reviews List" ;
            reviews.findFirst();
            while(! reviews.last())
            {
                str += reviews.retrieve() + " ";
                reviews.findNext();
            }
            str += reviews.retrieve() + " )";
        }
        return str;        
    }
    
}