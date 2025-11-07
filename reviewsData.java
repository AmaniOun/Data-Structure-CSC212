/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ds;


import java.io.File;
import java.util.Scanner;


public class reviewsData {
    public static Scanner input = new Scanner (System.in);
    public static LinkedList<Review> reviews = new LinkedList<Review> ();


    public LinkedList<Review>  getreviewsData ( )
    {
        return reviews;
    }
    
    public reviewsData ( String fileName)
    {
        
        try{
                File docsfile = new File(fileName);
                Scanner reader = new Scanner (docsfile);
                String line = reader.nextLine();
                
                while(reader.hasNext())
                {
                    line = reader.nextLine();
                    String [] data = line.split(","); 
                    int reviewID = Integer.parseInt(data[0]);
                    int productID = Integer.parseInt(data[1]);
                    int customerID = Integer.parseInt(data[2]);
                    int rating =  Integer.parseInt(data[3]);
                    String comment =  data[4];
                    
                    Review review = new Review(reviewID, productID, customerID, rating, comment );
                    reviews.insert(review);
                }
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    //==============================================================
    public Review AddReview(int customerID, int productID)
    {
        System.out.println("Please enter the review ID:");
        int reviewID = input.nextInt();
        
        while (checkReviewID(reviewID))
        {
            System.out.println("This review ID is already taken, please try another one:");
            reviewID = input.nextInt();
        }        
        
        System.out.println("Enter your rating (1 to 5): ");
        int rate = input.nextInt();
        while ( rate >5 || rate <1)
        {
            System.out.println("Invalid rating, please enter a number between 1 and 5:");
            rate = input.nextInt();
        }

        System.out.println("Enter your comment:");
        String comment = input.nextLine();
        comment = input.nextLine();

        Review review = new Review (reviewID, productID, customerID, rate,  comment );
        reviews.findFirst();
        reviews.insert(review);
        return review;
    }

    public void updateReview()
   {
       if  ( reviews.empty() )
       {
           System.out.println("There are no reviews available");
           return;
       }
       
       Review review = new Review();
       
       System.out.println("Enter the review ID you want to update:");
        int reviewID = input.nextInt();
        
        while (! checkReviewID(reviewID))
        {
            System.out.println("Review ID not found, please enter a valid ID:");
            reviewID = input.nextInt();
        }        
       
        reviews.findFirst();
        while (! reviews.last())
        {
            if (reviews.retrieve().getReviewId() == reviewID)
                break;
           reviews.findNext();
        }
        if (reviews.retrieve().getReviewId() == reviewID)
        {
            review = reviews.retrieve();
            reviews.remove();
            
            System.out.println("1. update rating");
            System.out.println("2. update comment");
            System.out.println("Please select an option:");
            int choice = input.nextInt();

            switch(choice)
            {
                case 1:
                {
                    System.out.println("Enter new rating (1 to 5)");
                    int rate = input.nextInt();
                    while ( rate >5 || rate <1)
                    {
                        System.out.println("Invalid rating, please enter a number between 1 and 5:");
                        rate = input.nextInt();
                    }
                    review.setRating(rate);
                }
                break;

                case 2:
                {
                    System.out.println("Enter new comment: ");
                    String comment = input.nextLine();
                    comment = input.nextLine();
                    review.setComment(comment);
                }
                break;
            }
            reviews.insert(review);
            System.out.println("Review " + review.getReviewId() + " has been successfully updated");
            System.out.println(reviews.retrieve());
        }
   }
   
    
    public boolean checkReviewID( int rID )
    {
        if (! reviews.empty())
        {
            reviews.findFirst();
            for (int i = 0 ; i< reviews.size() ; i++)
            {
                if (reviews.retrieve().getReviewId()== rID)
                    return true;
                reviews.findNext();
            }
        }
        return false ;
    }
}

