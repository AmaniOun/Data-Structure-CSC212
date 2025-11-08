package com.mycompany.inventoryandordersystem;

import java.io.File;
import java.util.Scanner;

public class productsData {
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Product> products = new LinkedList<Product>();

    public LinkedList<Product> getproductsData() {
        return products;
    }

    public productsData(String fileName) {
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

                Product product = new Product(productId, name, price, stock);
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
        
        Product product = new Product(productId, name, price, stock);
        if (products.empty()) {
            products.insert(product);
        } else {
            products.findFirst();
            products.insert(product);
        }
    }
    
    public Product searchProducID() {
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
    }

    public Product removeProduct() {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return null;
        }
        
        System.out.println("Enter the product ID to remove:  ");
        int productID = input.nextInt();
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getProductId() == productID) {
                products.retrieve().setStock(0);
                System.out.println("Product stock set to 0");
                return products.retrieve();
            }
            if (!products.last())
                products.findNext();
        }
        
        System.out.println("Product not found in the system");
        return null;
    }

    public Product updateProduct() {
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
            Product p = products.retrieve();
            
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
    }

    public Product searchProducName() {
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
    }

    public void OutStockProducts() {
        if (products.empty()) {
            System.out.println("The product list is currently empty");
            return;
        }
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getStock() == 0) {
                System.out.println(products.retrieve());
            }
            if (!products.last())
                products.findNext();
        }
    }

    public boolean checkProductID(int productId) {
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
    }

    public Product getProductData(int ProductId) {
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
    }
}