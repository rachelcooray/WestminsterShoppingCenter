package GUI;

import WestminsterShoppingCenter.Product;
import WestminsterShoppingCenter.User;
import WestminsterShoppingCenter.WestminsterShoppingManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The UserShoppingCenter class represents the main GUI for Westminster Shopping Center system.
 * It allows users to view, select, and add products to their shopping cart.
 */
public class UserShoppingCenter extends JFrame {
    private JPanel p1, p2, p3;
    private JComboBox<String> categoryComboBox; // A drop-down menu
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton, shoppingCartButton;
    private WestminsterShoppingManager manager;
    private User currentUser;
    private UserShoppingCart shoppingCart;
    private List<String> existingUsernames;

    /**
     * A constructor for the UserShoppingCenter.
     * Initializes the GUI components and sets up event listeners.
     */
    public UserShoppingCenter() {
        // Initialize attributes
        this.manager = null;
        this.shoppingCart = new UserShoppingCart(currentUser);
        this.existingUsernames = new ArrayList<>();

        shoppingCart = new UserShoppingCart(manager);

        // Initialize components
        addComponents();

        // Add ActionListeners
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                categorySelection();
            }
        });

        productTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                productSelection();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                addToCartHandler();
            }
        });

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                openShoppingCart();
            }
        });

        // Set frame properties
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add panels to the main frame
        add(p1);
        add(p2);
        add(p3);

        // To adjust the frame size based on its components
        pack();
    }

    /**
     * Sets the WestminsterShoppingManager for the UserShoppingCenter.
     */
    public void setManager(WestminsterShoppingManager manager) {
        this.manager = manager;
        displayAllProducts(); // Display all products in the user interface

    }

    /**
     * Adds the components to the UserShoppingCenter GUI.
     * Initializes and adds labels, panels, buttons, tables and a drop-down menu.
     */
    private void addComponents() {
        // Panel 1
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        JLabel selectCategoryLabel = new JLabel("Select product category");
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        shoppingCartButton = new JButton("Shopping Cart");
        p1.add(selectCategoryLabel);
        p1.add(categoryComboBox);
        p1.add(shoppingCartButton);

        // Panel 2
        p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"}; // Column names of the table
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        productTable.setDefaultRenderer(Object.class, new CustomCellRenderer());

        JScrollPane scrollPane = new JScrollPane(productTable);
        p2.add(scrollPane, BorderLayout.CENTER);

        // Panel 3
        p3 = new JPanel();
        p3.setLayout(new BorderLayout());

        JButton sortByProductIDButton = new JButton("Sort by ProductID");
        sortByProductIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortProductsByProductID();
            }
        });

        p3.add(sortByProductIDButton, BorderLayout.NORTH);
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Shopping Cart");
        p3.add(productDetailsTextArea, BorderLayout.CENTER);
        p3.add(addToCartButton, BorderLayout.SOUTH);

        // Initialize shopping cart
        shoppingCart = new UserShoppingCart(currentUser);

        // Initialize existing usernames list
        existingUsernames = new ArrayList<>();
    }

    /**
     * Adds a product to the product table with all necessary product details.
     */
    public void addProductToTable(String productID, String productName, String category, double price, String productInfo) {
        Object[] rowData = {productID, productName, category, price, productInfo}; // Creates an array containing the product details
        productTableModel.addRow(rowData); // Add the product details to the product table as a new row
    }

    /**
     * Removes a product with the specified product ID from the product table.
     */
    public void removeProductFromTable(String productID) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {  // Iterates through each row to check if the current product ID matches the specified product ID
            if (model.getValueAt(i, 0).equals(productID)) {
                model.removeRow(i);
                break;
            }
        }
    }

    /**
     * Handles the selection of a product category from the categoryComboBox (drop-down menu).
     * Displays products based on the selected category.
     */
    private void categorySelection() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem(); // Get the selected category from the combo box

        // Display products based on the selected category
        if ("All".equals(selectedCategory)) {
            displayAllProducts();
        } else if ("Electronics".equals(selectedCategory)) {
            displayElectronicsProducts();
        } else if ("Clothing".equals(selectedCategory)) {
            displayClothingProducts();
        }
    }

    /**
     * Displays all the products in the product table.
     * It clears the existing rows in the table and adds each product from the manager's product list.
     */
    private void displayAllProducts() {
        productTableModel.setRowCount(0); // Clearing the table
        for (Product product : manager.getProductList()) {
            addProductToTable(product.getProductID(), product.getProductName(), product.getCategory(), product.getPrice(), product.getProductInfo());
        }
    }

    /**
     * Displays all the electronic products in the product table.
     * It clears the existing rows in the table and adds each electronic product from the manager's product list.
     */
    private void displayElectronicsProducts() {
        productTableModel.setRowCount(0); // Clearing the table
        for (Product product : manager.getProductList()) {
            if ("Electronics".equals(product.getCategory())) {
                addProductToTable(product.getProductID(), product.getProductName(), product.getCategory(), product.getPrice(), product.getProductInfo());
            }
        }
    }

    /**
     * Displays all the clothing products in the product table.
     * It clears the existing rows in the table and adds each clothing product from the manager's product list.
     */
    private void displayClothingProducts() {
        productTableModel.setRowCount(0); // Clearing the table
        for (Product product : manager.getProductList()) {
            if ("Clothing".equals(product.getCategory())) {
                addProductToTable(product.getProductID(), product.getProductName(), product.getCategory(), product.getPrice(), product.getProductInfo());
            }
        }
    }

    /**
     * Handles the selection of a product in the product table.
     * Returns the details of the selected product and updates the product details display.
     */
    private void productSelection() {
        int selectedRow = productTable.getSelectedRow(); // Get the index of the selected row in the product table

        if (selectedRow != -1) { // Check if a valid row is selected
            // Retrieve information of the selected product from the table
            String productID = productTable.getValueAt(selectedRow, 0).toString();
            String name = productTable.getValueAt(selectedRow, 1).toString();
            String category = productTable.getValueAt(selectedRow, 2).toString();
            String price = productTable.getValueAt(selectedRow, 3).toString();
            String info = productTable.getValueAt(selectedRow, 4).toString();

            // Create a string with the selected product details
            String productDetails = "Selected product details:\n" +
                    "Product ID - " + productID + "\n" +
                    "Category - " + category + "\n" +
                    "Name - " + name + "\n" +
                    "Price - " + price + "\n" +
                    "Info - " + info + "\n";

            productDetailsTextArea.setText(productDetails); // Update the product details display with the selected product information
        }
    }

    /**
     * Handles the event of when the "Add to Shopping Cart" button is clicked.
     * It retrieves information of the selected product and adds it to the shopping cart.
     * Displays a message if there are not enough items available for the selected product.
     */
    private void addToCartHandler() {
        int selectedRow = productTable.getSelectedRow();  // Get the index of the selected row in the product table

        if (selectedRow != -1) { // Check if a valid row is selected

            // Retrieve information of the selected product from the table
            String productID = productTable.getValueAt(selectedRow, 0).toString();
            String productName = productTable.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(productTable.getValueAt(selectedRow, 3).toString());
            String category = productTable.getValueAt(selectedRow, 2).toString();
            int quantity = 1;

            // Get the available items for the selected product
            int availableItems = manager.getProductById(productID).getNumOfItemsAvailable();

            // Add product to the shopping cart
            if (availableItems >= quantity) {

                // Add product to the shopping cart
                shoppingCart.addProductToCart(productName, quantity, price, category);
                shoppingCart.setVisible(true);

            } else {
                // Display a message indicating that there are not enough items available
                JOptionPane.showMessageDialog(this, "Not enough items available for product: " + productName);
            }
        }
    }

    /**
     * Opens the UserShoppingCart window, making it visible to the user.
     * This method is called when the "Shopping Cart" button is clicked.
     */
    private void openShoppingCart() {
        shoppingCart.setVisible(true);
    }

    /**
     * Custom cell renderer for the JTable in the UserShoppingCenter GUI.
     * Highlights cells with low inventory in red to alert the user.
     */
    private class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (manager != null) { // Check if a product manager is set
                String productId = table.getValueAt(row, 0).toString();
                Product product = manager.getProductById(productId);

                if (product != null && product.getNumOfItemsAvailable() < 3) { // Highlight cells with low availability in red
                    rendererComponent.setBackground(Color.RED);
                } else {
                    rendererComponent.setBackground(Color.GRAY);
                }
            }
            return rendererComponent;
        }
    }

    /**
     * Sorts the products displayed in the table by Product ID alphabetically.
     */
    private void sortProductsByProductID() {
        // Get the current table model
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();

        // Get the data from the table model
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData[j] = model.getValueAt(i, j);
            }
            data.add(rowData);
        }

        // Sort the data by Product ID in ascending order
        data.sort(Comparator.comparing(o -> (String) o[0]));

        // Clear the table model
        model.setRowCount(0);

        // Add the sorted data back to the table model
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
    }

    /**
     * Returns the DefaultTableModel that is associated with the product table.
     */
    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }

    /**
     * Returns the UserShoppingCart instance associated with this UserShoppingCenter.
     */
    public UserShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}

//REFERENCES

/* Java Comparator
 * Comparator.comparing - https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
 */

/* GUI
 * CustomCellRenderer,TableCellRenderer - https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/table/TableCellRenderer.html
 */

