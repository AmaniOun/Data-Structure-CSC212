package com.mycompany.ds;

import java.io.File;
import java.util.Scanner;

public class productsData {
    public static Scanner input = new Scanner (System.in);
    public static LinkedList<Product> products = new LinkedList<Product> ();


    public LinkedList<Product>  getproductsData ( )
    {
        return products;
    }
    

    public productsData ( String fileName)
    {
        
       try{
                File docsfile = new File(fileName);
                Scanner reader = new Scanner (docsfile);
                String line = reader.nextLine();
                
                while(reader.hasNext())
                {
                    line = reader.nextLine();
                    String [] data = line.split(","); 
                    
                    int productId = Integer.parseInt(data[0]);
                    String name =  data[1].trim();
                    double price = Double.parseDouble(data[2]);
                    int stock = Integer.parseInt(data[3]);

                    Product product = new Product(productId, name, price, stock );
                    products.insert(product);
                }
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
 
    public void addProduct()
    {
        System.out.println("Please enter the product ID:");
        int productId = input.nextInt();
        while (checkProductID(productId))
        {
            System.out.println("This ID already exists, please try a different one:");
            productId = input.nextInt();
        }
        
        System.out.println("Enter the product name:");
        String name =input.next();
        
        System.out.println("Enter the price:");
        double price = input.nextDouble();
        
        System.out.println("stock:");
        int stock = input.nextInt();
        
        Product product = new Product(productId, name, price, stock);
        products.findFirst();
        products.insert(product);
    }
    
    public Product searchProducID()
    {
        if (products.empty())
        {
            System.out.println("The product list is currently empty");
        }
        else
        {
            System.out.println("Please enter the product ID: ");
            int productID = input.nextInt();
            
            boolean found = false;
            
            products.findFirst();
            while (!products.last())
            {
                if (products.retrieve().getProductId()== productID)
                {
                    found = true;
                    break;
                }
                products.findNext();
            }
            if (products.retrieve().getProductId()== productID)
                found = true;
        
            if (found )
                return products.retrieve();
        }
        System.out.println("Product not found in the system");
        return null;
    }

    public Product removeProduct()
    {
        if (products.empty())
        {
            System.out.println("The product list is currently empty");
        }
        else
        {
            System.out.println("Enter the product ID to remove:  ");
            int productID = input.nextInt();
            
            boolean found = false;
            
            products.findFirst();
            while (!products.last())
            {
                if (products.retrieve().getProductId()== productID)
                {
                    found = true;
                    break;
                }
                products.findNext();
            }
            if (products.retrieve().getProductId()== productID)
                found = true;
        
            if (found )
            {
                
                Product p = products.retrieve();
                products.remove();
                p.setStock(0);
                products.insert(p);
                return p;
            }
        }
        System.out.println("Product not found in the system");
        return null;
    }

    
    public Product updateProduct()
    {
        if (products.empty())
        {
            System.out.println("The product list is currently empty");
        }
        else
        {
            System.out.println("Enter the product ID to update: ");
            int productID = input.nextInt();
            
            boolean found = false;
            
            products.findFirst();
            while (!products.last())
            {
                if (products.retrieve().getProductId()== productID)
                {
                    found = true;
                    break;
                }
                products.findNext();
            }
            if (products.retrieve().getProductId()== productID)
                found = true;
        
            if (found )
            {
                Product p = products.retrieve();
                products.remove();
                
                System.out.println("1. Update product Name");
                System.out.println("2. Update product price");
                System.out.println("3. Update stock");
                System.out.println("Enter your choice");
                int choice = input.nextInt();
                
                switch ( choice )
                {
                    case 1:
                    {
                        System.out.println("Enter new product name:");
                        p.setProductName(input.next());
                        products.insert(p);
                    }
                    break;
                    case 2:
                    {
                        System.out.println("Enter new price:");
                        p.setProductPrice( input.nextDouble());
                        products.insert(p);
                    }
                    break;
                    case 3:
                    {
                        System.out.println("Enter new stock quantity:");
                        int stock = input.nextInt();
                        p.setStock(stock);
                        products.insert(p);
                    }
                    break;
                    default:
                        System.out.println("Invalid selection");
                }
                return p;
            }
        }
        System.out.println("Product not found in the system");
        return null;
    }

    
    public Product searchProducName()
    {
        if (products.empty())
            System.out.println("The product list is currently empty");
        else
        {
            System.out.println("Enter product Name to search: ");
            String name = input.nextLine();
            name = input.nextLine();
            
            products.findFirst();
            for ( int i = 0 ; i < products.size() ; i++)
            {
                if (products.retrieve().getProductName().compareToIgnoreCase(name)== 0)
                    return products.retrieve();
                products.findNext();
            }
        }

        System.out.println("No product found with this name");
        return null;
    }

    
    public void OutStockProducts()
    {
        if (products.empty())
        {
            System.out.println("The product list is currently empty");
        }
        else
        {
            products.findFirst();
            for ( int i = 0 ; i < products.size() ; i++)
            {
                if (products.retrieve().getStock() == 0)
                    System.out.println(products.retrieve());    
                products.findNext();
            }
        }
    }


    public boolean checkProductID( int productId )
    {
        if (! products.empty())
        {
            products.findFirst();
            for ( int i = 0 ; i< products.size() ; i++)
            {
                if (products.retrieve().getProductId() == productId)
                    return true;
                products.findNext();
            }
        }
        return false ;
    }

    
    public Product getProductData ( int ProductId )
    {
        if (! products.empty())
        {
            products.findFirst();
            while (!products.last())
            {
                if (products.retrieve().getProductId() == ProductId)
                    return products.retrieve();
                products.findNext();
            }
            if (products.retrieve().getProductId()== ProductId)
                return products.retrieve();
        }
        return null;
    }


}
