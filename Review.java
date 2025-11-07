package com.mycompany.ds;
 

public class Review {
    int reviewId;
    int productID;
    int customerID;
    int rating;
    String comment;

    public Review() {
        this.reviewId = 0;
        this.productID = 0;
        this.customerID = 0;
        this.rating = 0;
        this.comment = "";
    }
    public Review(int reviewId, int productID, int customerID, int rating, String comment) {
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

    @Override
    public String toString() {
        return "\nReview{" + "reviewId=" + reviewId + ", product=" + productID 
                + ", customer=" + customerID + ", rating=" + rating + ", comment=" + comment + '}';
    }
    
    
    
}
