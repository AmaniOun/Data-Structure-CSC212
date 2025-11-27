package project;

 import java.io.File;
import java.util.Scanner;

public class Reviews {
    
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Reviews> reviews = new LinkedList<Reviews>();

    public LinkedList<Reviews> getReviewData() {
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
    
    
    public Reviews(String fileName) {
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
                
                Reviews review = new Reviews(reviewID, productID, customerID, rating, comment);
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
        
        if (reviews.empty()) {
            reviews.insert(review);
        } else {
            reviews.findFirst();
            reviews.insert(review);
        }
        
        return review;
    } catch (Exception e) {
        System.out.println("Invalid input, please try again.");
        input.nextLine();
        return null;
    }
}


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
            
            System.out.println("Review " + review.getReviewId() + " has been successfully updated");
            System.out.println(reviews.retrieve());
        }
    } catch (Exception e) {
        System.out.println("Invalid input, please try again.");
        input.nextLine();
    }
}

    public boolean checkReviewID(int rID) {
        try {
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
    } catch (Exception e) {
        System.out.println("Error occurred while checking review ID, please try again.");
        return false;
    }
}
    
    public LinkedList<Reviews> getReviewsByCustomer(int customerId) {
   
    LinkedList<Reviews> customerReviews = new LinkedList<>();
    try {
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
     } catch (Exception e) {
        System.out.println("Error occurred while retrieving reviews for the customer, please try again.");
    }
    
    return customerReviews;
}
    
    public LinkedList<Reviews> getReviewsByProduct(int productId) {

    LinkedList<Reviews> productReviews = new LinkedList<>();

    try {
        if (reviews.empty()) {
            return productReviews;
        }

        reviews.findFirst();
        for (int i = 0; i < reviews.size(); i++) {

            if (reviews.retrieve().getProduct() == productId) {
                if (productReviews.empty()) {
                    productReviews.insert(reviews.retrieve());
                } else {
                    productReviews.findFirst();
                    productReviews.insert(reviews.retrieve());
                }
            }

            if (!reviews.last())
                reviews.findNext();
        }

    } catch (Exception e) {
        System.out.println("Error occurred while retrieving reviews for the product, please try again.");
    }

    return productReviews;
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
    

    @Override
    public String toString() {
        return "\nReview{" + "reviewId=" + reviewId + ", product=" + productID 
                + ", customer=" + customerID + ", rating=" + rating + ", comment=" + comment + '}';
    }
    
    
    
}