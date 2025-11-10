package com.mycompany.inventoryandordersystem;

import java.io.File;
import java.util.Scanner;

public class reviewsData {
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Review> reviews = new LinkedList<Review>();

    public LinkedList<Review> getreviewsData() {
        return reviews;
    }

    public reviewsData(String fileName) {
        try {
            File file = new File(fileName);
            
            System.out.println("Searching for: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File NOT found: " + fileName);
                System.out.println("Please put the file in: (\"user.dir\")");
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
                int reviewID = Integer.parseInt(data[0].trim());
                int productID = Integer.parseInt(data[1].trim());
                int customerID = Integer.parseInt(data[2].trim());
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim();
                
                Review review = new Review(reviewID, productID, customerID, rating, comment);
                reviews.insert(review);
                count++;
            }
            reader.close();
            System.out.println("Successfully loaded " + count + " reviews from " + fileName);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Review AddReview(int customerID, int productID) {
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

        Review review = new Review(reviewID, productID, customerID, rate, comment);
        
        if (reviews.empty()) {
            reviews.insert(review);
        } else {
            reviews.findFirst();
            reviews.insert(review);
        }
        
        return review;
    }

    public void updateReview() {
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

        reviews.findFirst();
        boolean found = false;
        
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.retrieve().getReviewId() == reviewID) {
                found = true;
                break;
            }
            if (!reviews.last())
                reviews.findNext();
        }
        
        if (found) {
            Review review = reviews.retrieve();
            
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
            
            System.out.println("Review " + review.getReviewId() + " has been successfully updated");
            System.out.println(reviews.retrieve());
        }
    }

    public boolean checkReviewID(int rID) {
        if (!reviews.empty()) {
            reviews.findFirst();
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.retrieve().getReviewId() == rID)
                    return true;
                if (!reviews.last())
                    reviews.findNext();
            }
        }
        return false;
    }
    
    public LinkedList<Review> getReviewsByCustomer(int customerId) {
   
    LinkedList<Review> customerReviews = new LinkedList<>();
    
    if (reviews.empty()) {
        return customerReviews;
    }
    
    reviews.findFirst();
    for (int i = 0; i < reviews.size(); i++) {
        if (reviews.retrieve().getCustomer() == customerId) {
            if (customerReviews.empty()) {
                customerReviews.insert(reviews.retrieve());
            } else {
                customerReviews.findFirst();
                customerReviews.insert(reviews.retrieve());
            }
        }
        
        if (!reviews.last())
            reviews.findNext();
    }
    
    return customerReviews;
}
}