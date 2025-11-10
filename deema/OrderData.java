/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectt;



import java.io.File;
import java.util.Scanner;

public class OrderData {

    public static LinkedList<Order> orders = new LinkedList<Order>();
    public static Scanner input = new Scanner(System.in);

    public LinkedList<Order> getAllOrders() {
        return orders;
    }

    public OrderData(String fileName) {
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

                Order o = new Order(orderId, customerId, productIds, totalPrice, date, status);
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

    public int cancelOrder(int id) {
        try{
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
         } catch (NullPointerException e) {
            System.out.println("Error: Null pointer while cancelling order");
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Error cancelling order: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public boolean UpdateOrder(int orderID) {
        try{
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
            Order current = orders.retrieve();

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
          } catch (NullPointerException e) {
            System.out.println("Error: Null pointer while updating order");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Order searchById(int id) {
        try{
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
        } catch (NullPointerException e) {
            System.out.println("Error: Null pointer while searching for order");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("Error searching for order: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public LinkedList<Order> getOrdersBetween(String start, String end) {
        LinkedList<Order> result = new LinkedList<>();

        if (orders.empty()) {
            System.out.println("No orders in system.");
            return result;
        }

        System.out.println("\n Searching for orders between: " + start + " and " + end);
        int foundCount = 0;

        orders.findFirst();
        for (int i = 0; i < orders.size(); i++) {
            Order current = orders.retrieve();
            

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
    }

    public boolean checkOrderID(int oid) {
        try{
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
              
        } catch (NullPointerException e) {
            System.out.println("Error: Null pointer while checking order ID");
            return false;
        } catch (Exception e) {
            System.out.println("Error checking order ID: " + e.getMessage());
            return false;
        }
    }
    
}