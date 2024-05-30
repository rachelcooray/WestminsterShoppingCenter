package WestminsterShoppingCenter;

/**
 * A class representing electronic products.
 * It extends the abstract class Product.
 * It includes additional attributes of electronic items such as brand and warranty period.
 */
public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;
    /**
     * Constructor to initialize an electronic object.
     * The attributes are initialized through the parameters passed.
     */
    public Electronics(String productID, String productName, int numOfItemsAvailable,double price, String brand, int warrantyPeriod) {
        super(productID, productName, numOfItemsAvailable, price); //Calls the constructor of the superclass Product to initialize common attributes
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter and setter methods to access and modify the additional attributes of an electronic object.
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    /**
     * Overrides the getProductInfo method in the superclass.
     * It provides additional electronic item-specific information.
     * Returns brand and warranty period information of the electronic product.
     */
    @Override
    public String getProductInfo() {
        return brand + ", " + warrantyPeriod;
    }
    /**
     * Overrides the getCategory method in the superclass.
     * It returns the category of the product (Electronics).
     */
    public String getCategory() {
        //Additional attributes for electronic products
        return "Electronics";
    }
}
