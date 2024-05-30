package WestminsterShoppingCenter;

import GUI.UserShoppingCenter;

import javax.swing.*;
import java.util.Scanner;

/**
 * The main class responsible for initiating the Westminster Shopping Center system.
 */
public class Main {
    private static WestminsterShoppingManager manager;

    public static void main(String[] args) {

        // Initialize the UserShoppingCenter and WestminsterShoppingManager
        UserShoppingCenter shoppingCenter = new UserShoppingCenter();
        manager = new WestminsterShoppingManager(shoppingCenter);

        shoppingCenter.setManager(manager); // Set the manager for the shopping center

        Scanner scanner = new Scanner(System.in); // Initialize the scanner for user input
        int mainOption;

        // FOR TESTING PURPOSES ONLY - Created item objects and added them to the system

//        Electronics e1 = new Electronics("watch1", "Samsung Watch 3", 25, 275, "Samsung", 2);
//        Clothing c1 = new Clothing("skirt1", "Yellow Dress", 20, 39.99, "M", "Yellow");
//
//        Electronics e2 = new Electronics("watch2", "Apple Watch 1", 21, 499.99, "Apple", 1);
//        Clothing c2 = new Clothing("pant4", "Frill Blue Pant", 2, 42, "S", "Blue");
//
//        Electronics e3 = new Electronics("phone5", "Sony 2", 18, 150, "Sony", 2);
//        Clothing c3 = new Clothing("dress4", "Short Brown Dress", 22, 19.99, "XS", "Brown");
//
//        Electronics e4 = new Electronics("PC1", "Dell PC", 5, 550, "Dell", 2);
//        Clothing c4 = new Clothing("top6", "Long Pink Shirt", 1, 25, "XL", "Pink");
//
//        manager.addProduct(e1);
//        manager.addProduct(c1);
//        manager.addProduct(e2);
//        manager.addProduct(c2);
//        manager.addProduct(e3);
//        manager.addProduct(c3);
//        manager.addProduct(e4);
//        manager.addProduct(c4);

        // Main menu loop
        do {
            // Display the main menu
            System.out.println("""
                    =======================================================
                    |          Westminster Shopping Center                |
                    =======================================================
                    |          Choose your option to proceed              |
                    |                                                     |
                    |         Press (1) -> Manager Console                |
                    |         Press (2) -> Shopping Center System         |
                    |         Press (3) -> Exit                           |
                    |                                                     |
                    =======================================================""");

            // Validate and get the mainOption
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number for the option.");
                scanner.next(); // Consume the invalid input
            }
            mainOption = scanner.nextInt();
            scanner.nextLine(); // Consume a newline

            // Switch statement for main menu options
            switch (mainOption) {
                case 1 -> openManagerConsole(manager, scanner); // Navigate to the Manager Console
                case 2 -> {
                    System.out.println("Opening Shopping Center System...");
                    initializeShoppingCenter(manager, scanner); // Initialize and open the Shopping Center System
                }
                case 3 -> {
                    System.out.println("Exiting the program.");
                    System.exit(0); // Exit the program
                }
                default -> System.out.println("Invalid option!");

            }
        } while (mainOption != 3);
    }

    /**
     * Opens the Manager Console for handling the manager's tasks.
     */
    private static void openManagerConsole(WestminsterShoppingManager manager, Scanner scanner) {
        int managerOption;

        // Manager Console loop
        do {
            System.out.println("""
                =======================================================
                |          Welcome to the Manager's Console           |
                =======================================================
                |          Choose your option to proceed              |
                |                                                     |
                |         Press (1) -> Add a New Product              |
                |         Press (2) -> Delete a Product               |
                |         Press (3) -> Print Product List             |
                |         Press (4) -> Save in File                   |
                |         Press (5) -> Main Page                      |
                |                                                     |
                =======================================================""");

            // Validate and get the managerOption
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number for the option.");
                scanner.next(); // Consume the invalid input
            }
            managerOption = scanner.nextInt();
            scanner.nextLine(); // Consume a newline

            // Switch statement for Manager Console options
            switch (managerOption) {
                case 1 -> addProductHandler(manager, scanner); // Add a new product
                case 2 -> deleteProductHandler(manager, scanner); // Delete a product
                case 3 -> manager.printProducts(); // Print product list
                case 4 -> manager.saveProducts("Product_Data.txt"); // Save products in a file
                case 5 -> System.out.println("Moving to the main menu."); // Return to the main menu
                default -> System.out.println("Invalid option!");
            }
        } while (managerOption != 5);
    }

    /**
     * Initializes the Shopping Center System and allows users to access the main page GUI.
     */
    private static void initializeShoppingCenter(WestminsterShoppingManager manager, Scanner scanner) {
        UserShoppingCenter shoppingCenter = new UserShoppingCenter();
        shoppingCenter.setManager(manager);

        // Ask for username and password
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");

        // Create a User object based on entered credentials
        User user = new User(username, password);

        // Add the username to the list
        shoppingCenter.getShoppingCart().addUsername(username);

        // Display the shopping center frame
        shoppingCenter.setVisible(true);

        int shoppingCenterOption;
        do {
            System.out.println("""
                =======================================================
                |       Shopping Center System                        |
                =======================================================
                |          Choose your option to proceed              |
                |                                                     |
                |         Press (1) -> Main Page                      |
                |                                                     |
                =======================================================""");

            // Validate and get the shoppingCenterOption
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number for the option.");
                scanner.next(); // Consume the invalid input
            }
            shoppingCenterOption = scanner.nextInt();
            scanner.nextLine(); // Consume a newline

            if (shoppingCenterOption != 1) {
                System.out.println("Invalid option!");
            }
        } while (shoppingCenterOption != 1);
    }

    /**
     * Handles the addition of a new product by gathering information from the manager.
     */
    private static void addProductHandler(WestminsterShoppingManager manager, Scanner scanner) {
        String productID;
        String productName;
        int numOfItemsAvailable;
        double price;
        int itemOption;

        // Validate and get the product ID
        boolean isProductIdValid;
        do {
            System.out.println("Enter the product ID: ");
            productID = scanner.nextLine();
            isProductIdValid = validateProductId(manager, productID);
            if (!isProductIdValid) {
                System.out.println("Product ID already exists. Please enter a new one.");
            }
        } while (productID.isEmpty() || !isProductIdValid);

        // Validate and get the product name
        do {
            System.out.println("Enter the product name: ");
            productName = scanner.nextLine();
        } while (productName.isEmpty());

        // Validate and get the number of items available
        do {
            System.out.println("Enter the number of items available: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number for items available.");
                scanner.next(); // Consume the invalid input
            }
            numOfItemsAvailable = scanner.nextInt();
            scanner.nextLine(); // Consume a newline
        } while (numOfItemsAvailable <= 0);

        // Validate and get the price of the product
        do {
            System.out.println("Enter the price of the product: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input! Please enter a valid number for the price.");
                scanner.next(); // Consume the invalid input
            }
            price = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
        } while (price <= 0);

        // Validate and get the product type (electronics or clothing)
        do {
            System.out.println("Is the product an electronic or clothing item? Enter 1 for electronics or 2 for clothing:");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter 1 for electronics or 2 for clothing.");
                scanner.next(); // Consume the invalid input
            }
            itemOption = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } while (itemOption != 1 && itemOption != 2);

        if (itemOption == 1) {
            // For electronics, validate and get the brand and warranty period
            String brand;
            int warrantyPeriod;

            do {
                System.out.println("Enter the brand: ");
                brand = scanner.nextLine();
            } while (brand.isEmpty());

            do {
                System.out.println("Enter the warranty period: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid number for the warranty period.");
                    scanner.next(); // Consume the invalid input
                }
                warrantyPeriod = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } while (warrantyPeriod <= 0);

            // Create and add the electronics item to the manager
            Electronics electronicsItem = new Electronics(productID, productName, numOfItemsAvailable, price, brand, warrantyPeriod);
            manager.addProduct(electronicsItem);
            System.out.println(productName + " added successfully.");
        }
        else if (itemOption == 2) {

            // For clothing, validate and get the size and color
            String size;
            String color;

            do {
                System.out.println("Enter the size: ");
                size = scanner.nextLine();
            } while (size.isEmpty());

            do {
                System.out.println("Enter the color: ");
                color = scanner.nextLine();
            } while (color.isEmpty());

            // Create and add the clothing item to the manager
            Clothing clothingItem = new Clothing(productID, productName, numOfItemsAvailable, price, size, color);
            manager.addProduct(clothingItem);
            System.out.println(productName + " added successfully.");
        }
    }

    /**
     * Handles the deletion of a product based on the product ID entered by the manager.
     */
    private static void deleteProductHandler(WestminsterShoppingManager manager, Scanner scanner) {
        System.out.println("Enter the product ID of the product you wish to delete: ");
        String productIDToDelete = scanner.nextLine();
        manager.deleteProduct(productIDToDelete);
    }

    /**
     * Validates whether a product with the given product ID already exists in the manager's product list.
     */
    private static boolean validateProductId(WestminsterShoppingManager manager, String productID) {
        Product existingProduct = manager.getProductById(productID); // Retrieve the product with the given product ID from the manager's product list
        return existingProduct == null;
    }
}
