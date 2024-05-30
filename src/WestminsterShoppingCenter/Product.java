package WestminsterShoppingCenter;

import java.io.Serializable;

/**
 * This class implements the Serializable interface.
 * It is used for object serialization.
 */

public abstract class Product implements Serializable {

    //Attributes of a product
    private String productID; //A product's unique identifier
    private String productName;
    private int numOfItemsAvailable;
    private double price;

    /**
     * Constructor to initialize a product object.
     * The attributes are initialized through the parameters passed.
     */
    public Product(String productID, String productName, int numOfItemsAvailable,double price){
        this.productID = productID;
        this.productName = productName;
        this.numOfItemsAvailable = numOfItemsAvailable;
        this.price = price;
    }
    // Getter and setter methods to access and modify the attributes of a product object.
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumOfItemsAvailable() {
        return numOfItemsAvailable;
    }

    public void setNumOfItemsAvailable(int numOfItemsAvailable) {
        this.numOfItemsAvailable = numOfItemsAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // A string representation of the product details
    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", numOfItemsAvailable=" + numOfItemsAvailable +
                ", price=" + price +
                '}';
    }
    /**
     * An abstract method to be implemented in the subclasses.
     * Returns the additional product information of a product.
     */
    public abstract String getProductInfo();

    /* An abstract method to be implemented in the subclasses.
     * Returns the category of a product.*/
    public abstract String getCategory();
}
