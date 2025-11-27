
package com.mycompany.inventoryandordersystem2;


import java.io.File;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Products {
    
    public static Scanner input = new Scanner(System.in);
    public static AVL_Tree<Integer, Products> products = new AVL_Tree<>();

    public AVL_Tree<Integer, Products> getproductsData() {
        return products;
    }
    
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Integer> reviews = new LinkedList<>();

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

    public void addStock(int stock) {
        this.stock += stock;
    }
    
    public void removeStock(int stock) {
        this.stock -= stock;
    }
    
    public void addReview(Integer reviewId) {
        reviews.insert(reviewId);
    }
    
    public boolean removeReview(Integer reviewId) {
        if (!reviews.empty()) {
            reviews.findFirst();
            while (!reviews.last()) {
                if (reviews.retrieve().equals(reviewId)) {
                    reviews.remove();
                    return true;
                } else
                    reviews.findNext();
            }
            if (reviews.retrieve().equals(reviewId)) {
                reviews.remove();
                return true;
            }
        }
        return false;
    }
    
    public LinkedList<Integer> getReviews() {
        return reviews;
    }
    
    // Load products from file - O(n log n)
    public Products(String fileName) {
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
            String line = reader.nextLine(); // Skip header
            
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
                products.insert(productId, product); // O(log n)
                count++;
            }
            reader.close();
            System.out.println("Successfully loaded " + count + " products from " + fileName);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Add product - O(log n)
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
            products.insert(productId, product); // O(log n)
            System.out.println("Product added successfully!");
            
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
        }
    }
    
    // Search by ID - O(log n)
    public Products searchProducID() {
        try {
            if (products.empty()) {
                System.out.println("The product list is currently empty");
                return null;
            }
            
            System.out.println("Please enter the product ID: ");
            int productID = input.nextInt();
            
            if (products.findKey(productID)) { // O(log n)
                return products.retrieve();
            }
            
            System.out.println("Product not found in the system");
            return null;
            
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            return null;
        }
    }
    
    // Remove product - O(log n)
    public Products removeProduct() {
        try {
            if (products.empty()) {
                System.out.println("The product list is currently empty");
                return null;
            }
            
            System.out.println("Enter the product ID to remove: ");
            int productID = input.nextInt();
            
            if (products.findKey(productID)) { // O(log n)
                Products removedProduct = products.retrieve();
                products.remove(productID); // O(log n)
                System.out.println("Product " + productID + " has been removed from the system");
                return removedProduct;
            }
            
            System.out.println("Product not found in the system");
            return null;
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            return null;
        }
    }
   
    // Update product - O(log n)
    public Products updateProduct() {
        try {
            if (products.empty()) {
                System.out.println("The product list is currently empty");
                return null;
            }
            
            System.out.println("Enter the product ID to update: ");
            int productID = input.nextInt();
            
            if (products.findKey(productID)) { // O(log n)
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
                products.update(productID, p); // O(log n)
                return p;
            }
            
            System.out.println("Product not found in the system");
            return null;
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            return null;
        }
    }

    // Search by name - O(n) - requires full traversal
    public Products searchProducName() {
        try {
            if (products.empty()) {
                System.out.println("The product list is currently empty");
                return null;
            }
            
            System.out.println("Enter product Name to search: ");
            String name = input.nextLine();
            name = input.nextLine();
            
            final String searchName = name;
            final Products[] result = new Products[1];
            
            products.inOrder(new AVL_Tree.Visitor<Products>() {
                public void visit(Products p) {
                    if (p.getProductName().equalsIgnoreCase(searchName)) {
                        result[0] = p;
                    }
                }
            });
            
            if (result[0] != null) {
                return result[0];
            }

            System.out.println("No product found with this name");
            return null;
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            return null;
        }
    }

    // Out of stock products - O(n)
    public void OutStockProducts() {
        try {
            if (products.empty()) {
                System.out.println("The product list is currently empty");
                return;
            }
            
            final boolean[] found = {false};
            
            products.inOrder(new AVL_Tree.Visitor<Products>() {
                public void visit(Products p) {
                    if (p.getStock() == 0) {
                        System.out.println(p);
                        found[0] = true;
                    }
                }
            });
            
            if (!found[0]) {
                System.out.println("There is NO product out of stock");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking stock, please try again.");
        }
    }

    // Check if product ID exists - O(log n)
    public boolean checkProductID(int productId) {
        try {
            return products.findKey(productId); // O(log n)
        } catch (Exception e) {
            System.out.println("Error occurred while checking product ID, please try again.");
            return false;
        }
    }

    // Get product data by ID - O(log n)
    public Products getProductData(int ProductId) {
        try {
            if (products.findKey(ProductId)) { // O(log n)
                return products.retrieve();
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving product data, please try again.");
            return null;
        }
    }
    
    // NEW: Range query by price - O(n) with pruning
    public LinkedList<Products> getProductsByPriceRange(double minPrice, double maxPrice) {
        LinkedList<Products> result = new LinkedList<>();
        
        products.inOrder(new AVL_Tree.Visitor<Products>() {
            public void visit(Products p) {
                if (p.getProductPrice() >= minPrice && p.getProductPrice() <= maxPrice) {
                    if (result.empty())
                        result.insert(p);
                    else {
                        result.findFirst();
                        result.insert(p);
                    }
                }
            }
        });
        
        return result;
    }
    
    // NEW: Get all products sorted by ID
    public void printAllProductsSorted() {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }
        
        System.out.println("\n=== All Products (Sorted by ID) ===");
        products.inOrder(new AVL_Tree.Visitor<Products>() {
            public void visit(Products p) {
                System.out.println(p);
            }
        });
    }
    
    @Override
    public String toString() {
        String str = "\nproductId=" + productId + ", name=" + name + ", price=" + price + ", stock=" + stock;
        if (!reviews.empty()) {
            str += " (reviews List: ";
            reviews.findFirst();
            while (!reviews.last()) {
                str += reviews.retrieve() + " ";
                reviews.findNext();
            }
            str += reviews.retrieve() + ")";
        }
        return str;
    }
}