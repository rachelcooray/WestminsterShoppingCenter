package WestminsterShoppingCenter;

/**
 * A class representing clothing products.
 * It extends the abstract class Product.
 * It includes additional attributes of clothing items such as size and color.
 */
public class Clothing extends Product{
    private String size;
    private String color;

    /**
     * Constructor to initialize a clothing object.
     * The attributes are initialized through the parameters passed.
     */
    public Clothing(String productID, String productName, int numOfItemsAvailable,double price, String size, String color) {
        super(productID, productName, numOfItemsAvailable, price); //Calls the constructor of the superclass Product to initialize common attributes
        this.size = size;
        this.color = color;
    }

    // Getter and setter methods to access and modify the additional attributes of a clothing object.
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Overrides the getProductInfo method in the superclass.
     * It provides additional clothing-specific information.
     * Returns size and color information of the clothing product.
     */
    @Override
    public String getProductInfo() {
        return size + ", " + color;
    }

    /**
     * Overrides the getCategory method in the superclass.
     * It returns the category of the product (Clothing).
     */
    public String getCategory() {
        //Additional attributes for clothing products
        return "Clothing";
    }
}
