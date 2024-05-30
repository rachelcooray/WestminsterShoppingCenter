package WestminsterShoppingCenter;

/**
 * An interface that represents the shopping manager for the Westminster Shopping Center system.
 * It defines the methods for the operations a manager can perform.
 * Those operations are adding, deleting, printing, saving, and loading products as required.
 */
public interface ShoppingManager {
    void addProduct(Product product); // Adds a product to the shopping manager.
    void deleteProduct(String productID); // Deletes a product from the shopping manager based on its product ID.
    void printProducts(); // Prints the list of products in the shopping manager.
    void saveProducts(String fileName); // Saves the list of products in the shopping manager to a specified file.
    void loadProducts(String fileName); // Loads products from a specified file into the shopping manager.
}
