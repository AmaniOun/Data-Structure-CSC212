package project;


public class Product {
    int productId;
    String name;
    double price;
    int stock;
    LinkedList <Integer> reviews = new LinkedList <Integer> ();

    public Product() {
        this.productId = 0;
        this.name = "";
        this.price = 0;
        this.stock = 0;
    }

    public Product(int productId, String name, double price, int stock) {
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