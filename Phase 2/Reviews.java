
package com.mycompany.inventoryandordersystem2;


import java.io.File;
import java.util.Scanner;

public class Reviews {
    
    public static Scanner input = new Scanner(System.in);
    public static AVL_Tree<Integer, Reviews> reviews = new AVL_Tree<>();

    public AVL_Tree<Integer, Reviews> getReviewData() {
        return reviews;
    }
    
    private int reviewId;
    private int productID;
    private int customerID;
    private int rating;
    private String comment;

    public Reviews() {
        this.reviewId = 0;
        this.productID = 0;
        this.customerID = 0;
        this.rating = 0;
        this.comment = "";
    }
    
    public Reviews(int reviewId, int productID, int customerID, int rating, String comment) {
        this.reviewId = reviewId;
        this.productID = productID;
        this.customerID = customerID;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProduct() {
        return productID;
    }

    public void setProduct(int product) {
        this.productID = product;
    }

    public int getCustomer() {
        return customerID;
    }

    public void setCustomer(int customer) {
        this.customerID = customer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    // Load reviews from file - O(n log n)
    public Reviews(String fileName) {
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
            String line = reader.nextLine(); 
            
            int count = 0;
            while (reader.hasNext()) {
                line = reader.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] data = line.split(",");
                int reviewID = Integer.parseInt(data[0].trim());
                int productID = Integer.parseInt(data[1].trim());
                int customerID = Integer.parseInt(data[2].trim());
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim();
                
                Reviews review = new Reviews(reviewID, productID, customerID, rating, comment);
                reviews.insert(reviewID, review); // O(log n)
                count++;
            }
            reader.close();
            System.out.println("Successfully loaded " + count + " reviews from " + fileName);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Add review - O(log n)
    public Reviews AddReview(int customerID, int productID) {
        try {
            System.out.println("Please enter the review ID:");
            int reviewID = input.nextInt();
            
            while (checkReviewID(reviewID)) {
                System.out.println("This review ID is already taken, please try another one:");
                reviewID = input.nextInt();
            }
            
            System.out.println("Enter your rating (1 to 5): ");
            int rate = input.nextInt();
            while (rate > 5 || rate < 1) {
                System.out.println("Invalid rating, please enter a number between 1 and 5:");
                rate = input.nextInt();
            }

            System.out.println("Enter your comment:");
            String comment = input.nextLine();
            comment = input.nextLine();

            Reviews review = new Reviews(reviewID, productID, customerID, rate, comment);
            reviews.insert(reviewID, review); // O(log n)
            
            return review;
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            return null;
        }
    }

    // Update review - O(log n)
    public void updateReview() {
        try {
            if (reviews.empty()) {
                System.out.println("There are no reviews available");
                return;
            }

            System.out.println("Enter the review ID you want to update:");
            int reviewID = input.nextInt();
            
            while (!checkReviewID(reviewID)) {
                System.out.println("Review ID not found, please enter a valid ID:");
                reviewID = input.nextInt();
            }

            if (reviews.findKey(reviewID)) { // O(log n)
                Reviews review = reviews.retrieve();
                
                System.out.println("1. update rating");
                System.out.println("2. update comment");
                System.out.println("Please select an option:");
                int choice = input.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter new rating (1 to 5)");
                        int rate = input.nextInt();
                        while (rate > 5 || rate < 1) {
                            System.out.println("Invalid rating, please enter a number between 1 and 5:");
                            rate = input.nextInt();
                        }
                        review.setRating(rate);
                        break;

                    case 2:
                        System.out.println("Enter new comment: ");
                        String comment = input.nextLine();
                        comment = input.nextLine();
                        review.setComment(comment);
                        break;
                }
                
                reviews.update(reviewID, review); // O(log n)
                System.out.println("Review " + review.getReviewId() + " has been successfully updated");
                System.out.println(review);
            }
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
        }
    }

    // Check review ID - O(log n)
    public boolean checkReviewID(int rID) {
        try {
            return reviews.findKey(rID); // O(log n)
        } catch (Exception e) {
            System.out.println("Error occurred while checking review ID, please try again.");
            return false;
        }
    }
    
    // Get reviews by customer - O(n)
    public LinkedList<Reviews> getReviewsByCustomer(int customerId) {
        LinkedList<Reviews> customerReviews = new LinkedList<>();
        try {
            if (reviews.empty()) {
                return customerReviews;
            }
            
            reviews.inOrder(new AVL_Tree.Visitor<Reviews>() {
                public void visit(Reviews r) {
                    if (r.getCustomer() == customerId) {
                        if (customerReviews.empty()) {
                            customerReviews.insert(r);
                        } else {
                            customerReviews.findFirst();
                            customerReviews.insert(r);
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving reviews for the customer, please try again.");
        }
        
        return customerReviews;
    }
    
    // NEW: Get reviews by product - O(n)
    public LinkedList<Reviews> getReviewsByProduct(int productId) {
        LinkedList<Reviews> productReviews = new LinkedList<>();
        try {
            if (reviews.empty()) {
                return productReviews;
            }
            
            reviews.inOrder(new AVL_Tree.Visitor<Reviews>() {
                public void visit(Reviews r) {
                    if (r.getProduct() == productId) {
                        if (productReviews.empty()) {
                            productReviews.insert(r);
                        } else {
                            productReviews.findFirst();
                            productReviews.insert(r);
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving reviews for the product, please try again.");
        }
        
        return productReviews;
    }
    
    // Get average rating for product - O(n)
    public static float AVG_Rating(int pid) {
        try {
            final int[] counter = {0};
            final float[] rate = {0};
            
            reviews.inOrder(new AVL_Tree.Visitor<Reviews>() {
                public void visit(Reviews r) {
                    if (r.getProduct() == pid) {
                        counter[0]++;
                        rate[0] += r.getRating();
                    }
                }
            });
            
            if (counter[0] == 0)
                return 0;
            
            return (rate[0] / counter[0]);
        } catch (Exception e) {
            System.out.println("An error occurred while calculating the average rating.");
            return 0;
        }
    }

    @Override
    public String toString() {
        return "\nReview{" + "reviewId=" + reviewId + ", product=" + productID 
                + ", customer=" + customerID + ", rating=" + rating + ", comment=" + comment + '}';
    }
}