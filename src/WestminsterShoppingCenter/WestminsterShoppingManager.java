package WestminsterShoppingCenter;

import GUI.UserShoppingCenter;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

/**
 * A class that represents the shopping manager for Westminster Shopping Center.
 * This class allows managers to manage the list of products and provides methods to add, delete, and display products.
 * It also handles saving and loading products to and from a file.
 */
public class WestminsterShoppingManager implements ShoppingManager {
    // Attributes of a shopping manager
    private static final int MAX_PRODUCTS = 50; // The maximum number of items that can be in the system is 50

    private ArrayList<Product> productList;
    private UserShoppingCenter userShoppingCenter;

    /**
     * A constructor to initialize WestminsterShoppingManager.
     * The passed parameter is the associated UserShoppingCenter GUI.
     */
    public WestminsterShoppingManager(UserShoppingCenter userShoppingCenter) {
        this.productList = new ArrayList<>();
        this.userShoppingCenter = userShoppingCenter;
        loadProducts("Product_Data.txt");
    }

    /**
     * This method adds a product to the shopping manager.
     * If the number of products in the system is less than MAX_PRODUCTS, the new product is added.
     * It adds the product details to the JTable of the Westminster Shopping Center system.
     * It calls the saveProducts method to save them in a file.
     */
    public void addProduct(Product product) {
        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            System.out.println(productList.size() + "/50 item(s) are in the system.");
            userShoppingCenter.addProductToTable(product.getProductID(), product.getProductName(), product.getCategory(), product.getPrice(), product.getProductInfo());
            refreshProductTable();
            saveProducts("Product_Data.txt");
        } else {
            System.out.println("Product List exceeded limit.");
        }
    }

    /**
     * This method deletes a product from the shopping manager based on its product ID.
     * The product ID of the product to be deleted, is passed and if it exists it is deleted.
     * Its product details are shown and gets re-confirmation from manager to delete the product.
     */
    public void deleteProduct(String productID) {
        Product productToRemove = getProductById(productID);

        if (productToRemove != null) { // Makes sure the product list is not empty.
            System.out.println("Product details to delete:");
            System.out.println(productToRemove.toString());

            String category;

            /* Checks the category of a product */
            if (productToRemove instanceof Electronics) {
                category = "Electronics";
            }
            else if (productToRemove instanceof Clothing) {
                category = "Clothing";
            }
            else {
                category = "Unknown";
            }
            System.out.println("Category: " + category);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Is this the item you wish to delete? (yes/no)"); // Gets re-confirmation from manager to delete the product
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("yes")) {
                productList.remove(productToRemove);
                userShoppingCenter.removeProductFromTable(productID);

                /* Displays the details of the product deleted */
                System.out.println("Product with ID " + productID + " deleted.");
                System.out.println("Deleted Product Details:");
                System.out.println(productToRemove.toString());
                System.out.println("Category: " + category);
                System.out.println("Total number of products left in the system: " + productList.size());
                refreshProductTable();
                saveProducts("Product_Data.txt");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Product with ID " + productID + " not found.");
        }
    }

    /**
     * This method prints the list of products in the shopping manager.
     * The printed list is sorted by the alphabetical order of product ID.
     */
    public void printProducts() {
        ArrayList<Product> tempProductList = new ArrayList<>(); // Creating a list to temporarily store the list data
        for (int i = 0; i < productList.size(); i++) {
            tempProductList.add(productList.get(i));
        }
        if (tempProductList.size() > 0) { // Iterates through the products in the temporarily created list
            Collections.sort(tempProductList, Comparator.comparing(Product::getProductID)); // Sorts the list
            for (Product product : tempProductList) {
                if (product instanceof Electronics) {
                    System.out.println("Product type: Electronics");
                } else if (product instanceof Clothing) {
                    System.out.println("Product type: Clothing");
                }
                System.out.println(product.toString());
            }
            saveProducts("Product_Data.txt"); // Updates the file
        } else {
            System.out.println("No products added");
        }
    }

    /**
     * This method saves the list of products to a specified file using object serialization.
     * The name of the file the products are saved to, is passed through the parameter.
     */
    public void saveProducts(String fileName) {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName, false))) {

            // Creates an ObjectOutputStream to serialize the objects and write to the specified file
            outputStream.writeObject(productList);
            System.out.println("Products saved to file: " + fileName);
        }
        catch (IOException exception) { // Handles the IOException if an issue occurs during the serialization or file writing process
            System.out.println("Error saving products to file: " + exception.getMessage());
        }
    }

    /**
     * This method loads products from a specified file into the shopping manager using object deserialization.
     * The name of the file the products are loaded from, is passed through the parameter.
     */
    public void loadProducts(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {

            // Creates an ObjectInputStream to deserialize objects from the specified file
            List<Product> savedProducts = (List<Product>) inputStream.readObject();

            // Clears the existing product list and add all products from the loaded list
            productList.clear();
            productList.addAll(savedProducts);
            System.out.println("Products loaded from file: " + fileName);
        }
        catch (IOException | ClassNotFoundException exception) {
            // Handles the IOException or ClassNotFoundException in case of any issues during deserialization or file reading
            System.out.println("Error loading products from file: " + exception.getMessage());
        }
    }

    /**
     * A method to return the list of products managed by the shopping manager.
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * A method to return a product by its product ID, or null if not found.
     */
    public Product getProductById(String productId) {
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                return product; // Return the product if its ID matches the specified ID
            }
        }
        return null; // Return null if no product with the specified ID is found
    }

    /**
     * Refreshes the product table in the associated GUI.
     */
    private void refreshProductTable() {
        if (userShoppingCenter != null) { // Check if the associated UserShoppingCenter GUI is not null
            DefaultTableModel model = userShoppingCenter.getProductTableModel(); // Retrieve the DefaultTableModel from the GUI
            if (model != null) {
                model.fireTableDataChanged(); // Notifies all listeners in the table that all cell values in the table's rows may have changed and triggers a refresh
            }
        }
    }
}

//REFERENCES

/* Java ExceptionHandling
 * IOException - https://docs.oracle.com/javase/8/docs/api/java/io/IOException.html
 * ClassNotFoundException - https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html
 */

/* GUI
 * fireTableDataChanged() - https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/table/AbstractTableModel.html#fireTableDataChanged()
 */