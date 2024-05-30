package GUI;

import WestminsterShoppingCenter.User;
import WestminsterShoppingCenter.WestminsterShoppingManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the GUI of the user's shopping cart.
 * It manages the display of the shopping cart, added details table, product quantities and discounts.
 */
public class UserShoppingCart extends JFrame {
    private DefaultTableModel cartTableModel;
    private static JTable shoppingTable;
    private JTextArea discountTextArea;
    private Set<String> existingUsernames; // Set only allows unique values
    private boolean validForFirstTimeDiscount;
    private boolean validForCategoryDiscount;

    private static final String EXISTING_USERNAMES_FILE = "existing_usernames.txt"; // File to store usernames
    private int electronicCount;
    private int clothingCount;

    /**
     * A constructor for creating a UserShoppingCart associated with a WestminsterShoppingManager.
     */
    public UserShoppingCart(WestminsterShoppingManager manager) {
        initialize();
    }

    /**
     * A constructor for creating a UserShoppingCart associated with a User.
     */
    public UserShoppingCart(User user) {
        this.existingUsernames = loadExistingUsernames();
        initialize();
    }

    /**
     * This method initializes the UserShoppingCart GUI components.
     * The GUI consists of two panels: one for displaying the shopping cart table and another for showing totals and discounts.
     */
    private void initialize() {
        setTitle("Shopping Cart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Panel 1
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        String[] columnNames = {"Product", "Quantity", "Price", "Category"}; // Column names for the shopping cart table
        Object[][] data = {}; // // Initializing an empty data array for the table

        cartTableModel = new DefaultTableModel(data, columnNames) { // Creating a DefaultTableModel for the shopping cart table
            @Override
            public boolean isCellEditable(int row, int column) { // To allow cells in the table to be edited
                return false;
            }
        };

        JTable shoppingTable = new JTable(cartTableModel); // Creating the JTable using the DefaultTableModel

        JScrollPane scrollPane = new JScrollPane(shoppingTable); // Create a JScrollPane to enable scrolling in the shopping cart table
        p1.add(scrollPane, BorderLayout.CENTER);

        // Panel 2
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        discountTextArea = new JTextArea(); // Create a JTextArea for displaying discount information
        p2.add(discountTextArea, BorderLayout.CENTER);

        // Add both panels to the main frame
        add(p1);
        add(p2);

        // To adjust the frame size based on its components
        pack();

    }

    /**
     * Adds a product to the shopping cart with the specified details.
     * If the product already exists in the cart, it updates the quantity and price.
     * Else, it adds a new row to the cart.
     * It updates the total price and checks for category discounts.
     */
    public void addProductToCart(String productName, int quantity, double price, String category) {
            boolean productExists = false;
            int existingRow = -1;

            // Check if the product already exists in the cart
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String rowProductName = cartTableModel.getValueAt(i, 0).toString();
                if (productName.equals(rowProductName)) {
                    productExists = true;
                    existingRow = i;
                    break;
                }
            }

            if (productExists) {
                // If the product exists, update quantity and price
                int currentQuantity = (int) cartTableModel.getValueAt(existingRow, 1);
                double currentPrice = parseDoubleValue(cartTableModel.getValueAt(existingRow, 2));

                cartTableModel.setValueAt(currentQuantity + 1, existingRow, 1);
                cartTableModel.setValueAt(currentPrice + price, existingRow, 2);
            } else {
                // If the product doesn't exist, add a new row
                Object[] rowData = {productName, 1, price, category};
                cartTableModel.addRow(rowData);
            }

            updateTotal(); // Updates the total price


            // Check for category discounts
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String rowCategory = cartTableModel.getValueAt(i, 3).toString();

                if ("electronics".equalsIgnoreCase(rowCategory)) {
                    electronicCount++;
                } else if ("clothing".equalsIgnoreCase(rowCategory)) {
                    clothingCount++;
                }
            }

            // Set the flag for category discount based on the count
            if (electronicCount >= 3 || clothingCount >= 3) {
                validForCategoryDiscount = true;
            }
            else {
            validForCategoryDiscount = false;
            }
    }

    /**
     * Updates the total price in the discountTextArea based on the contents of the cartTableModel.
     */
    private void updateTotal() {
        int rows = cartTableModel.getRowCount();
        double total = 0;

        // Iterates through the rows of the cartTableModel
        for (int i = 0; i < rows; i++) {
            // Converts to appropriate types if needed
            double price = parseDoubleValue(cartTableModel.getValueAt(i, 2));
            int quantity = parseIntValue(cartTableModel.getValueAt(i, 1));

            total += price * quantity; // Calculate the total price
        }

        discountTextArea.setText(calculateDiscounts(total)); // Update the discountTextArea with the calculated discounts
    }

    /* Helper methods to safely convert Object to double and int */

    /**
     * Converts an Object value to a double, or 0 if the conversion fails.
     */
    private double parseDoubleValue(Object value) {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException exception) {
            return 0; // Handle the exception
        }
    }

    /**
     * Converts an Object value to an int, or 0 if the conversion fails.
     */
    private int parseIntValue(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException exception) {
            return 0; // Handle the exception
        }
    }

    /**
     * Calculates discounts based on the total price.
     * Two types of discounts are given.
     * 1: First time users receive a 10% discount
     * 2: If 3 products of the same category are selected a 20% discount is given
     * A formatted string representing the total, first purchase discount, category discount, and final price after applying discounts.
     */
    private String calculateDiscounts(double total) {
        // Initialize discount variables
        double firstPurchaseDiscount = 0;
        double categoryDiscount = 0;

        if (validForFirstTimeDiscount){ // Applies first-time purchase discount if valid
            firstPurchaseDiscount = total * 0.1;
        }

        if (validForCategoryDiscount) { // Applies category discount if valid
            categoryDiscount = total * 0.2;
        }

        double finalPrice = total - firstPurchaseDiscount - categoryDiscount; // Calculates the final price after applying discounts

        // Format and return the result
        return String.format("Total: £%.2f\nFirst Purchase Discount: £%.2f\nThree items in the same Category Discount: £%.2f\nFinal Price: £%.2f",
                total, firstPurchaseDiscount, categoryDiscount, finalPrice);
    }

    /**
     * Adds a new username to the existing usernames set and saves the updated set to a file.
     */
    public void addUsername(String username) {
        existingUsernames.add(username);
        // Save the updated usernames to the file
        saveExistingUsernames();
    }

    /**
     * Creates the file for storing existing usernames.
     * This method is called when the file does not exist.
     */
    private void createExistingUsernamesFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXISTING_USERNAMES_FILE))) {
            // An empty file is created
        } catch (IOException exception) {
            exception.printStackTrace(); // Prints this throwable
        }
    }

    /**
     * This method saves the updated set of existing usernames to the file.
     * It checks if each username is already in the file before writing.
     * Updates the boolean flag validForFirstTimeDiscount is a new username is added.
     */
    private void saveExistingUsernames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_USERNAMES_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(EXISTING_USERNAMES_FILE, true))) {

            Set<String> existingUsernamesInFile = new HashSet<>();
            String line;

            // Read existing usernames from the file
            while ((line = reader.readLine()) != null) {
                existingUsernamesInFile.add(line.trim());
            }

            boolean isNewUsernameAdded = false;
            // Add new usernames that are not already in the file
            for (String username : existingUsernames) {
                if (!existingUsernamesInFile.contains(username)) {
                    writer.write(username);
                    writer.newLine();
                    isNewUsernameAdded = true;
                }
                validForFirstTimeDiscount = isNewUsernameAdded;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            // Handle the exception appropriately
        }
    }

    /**
     * This method loads existing usernames from a file and returns them as a set.
     * If the file is not found, it creates the file and returns an empty set.
     */
    private Set<String> loadExistingUsernames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXISTING_USERNAMES_FILE))) {
            Set<String> usernames = new HashSet<>();
            String line;

            while ((line = reader.readLine()) != null) { // Read usernames from the file
                usernames.add(line);
            }
            return usernames;
        } catch (IOException exception) {
            // Handle file reading exception
            createExistingUsernamesFile(); // Create the file if it doesn't exist
            return new HashSet<>(); // Return an empty set if there's an issue
        }
    }

}

// REFERENCES

/* Java ExceptionHandling
 * NumberFormatException - https://docs.oracle.com/javase/8/docs/api/java/lang/NumberFormatException.html
 */

/* GUI
 * printStackTrace() - https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#printStackTrace--
 */


