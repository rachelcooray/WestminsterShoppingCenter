package Test;

import WestminsterShoppingCenter.WestminsterShoppingManager;
import WestminsterShoppingCenter.Clothing;

import static org.junit.Assert.*;

import GUI.UserShoppingCenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This class contains JUnit tests for the WestminsterShoppingManager class to test its options in the console menu.
 */
public class WestminsterShoppingManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // ByteArrayOutputStream to capture console output for later assertions
    private final PrintStream originalOut = System.out; // Original System.out to restore console output after testing

    private WestminsterShoppingManager manager;

    /**
     * The Setup method to initialize necessary objects before each test case.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        UserShoppingCenter shoppingCenter = new UserShoppingCenter();
        manager = new WestminsterShoppingManager(shoppingCenter);
    }

    /**
     * The Cleanup method to restore original "System.out" after each test case.
     */
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Test case for adding a product to the manager's product list.
     */
    @Test
    public void testAddProduct() {
        Clothing clothingItem = new Clothing("dress1", "Short Black", 10, 19.99, "M", "Black");
        manager.addProduct(clothingItem);
        assertEquals(15, manager.getProductList().size());
    }

    /**
     * Test case for deleting a product from the manager's product list.
     */
    @Test
    public void testDeleteProduct() {
        Clothing clothingItem = new Clothing("dress1", "Short Black", 10, 19.99, "M", "Black");
        manager.addProduct(clothingItem);
        assertEquals(14, manager.getProductList().size()); // Assert that the size of the product list is as expected

        // Redirect console output to check printed messages
        System.setOut(new PrintStream(outContent));

        // Provide "yes" as input to simulate deletion confirmation
        System.setIn(new ByteArrayInputStream("yes".getBytes()));

        manager.deleteProduct("C001");

        // Restore original console output
        System.setOut(originalOut);

        assertEquals(14, manager.getProductList().size()); // Assert that the size of the product list is as expected after deletion
        assertTrue(outContent.toString().contains("Product with ID C001 deleted.")); // Assert that the deletion message is printed to the console
    }

    /**
     * Test case for printing the products in the manager's product list.
     */
    @Test
    public void testPrintProducts() {
        Clothing clothingItem = new Clothing("dress1", "Short Black", 10, 19.99, "M", "Black");
        manager.addProduct(clothingItem);

        // Redirect console output to check printed messages
        System.setOut(new PrintStream(outContent));

        manager.printProducts();

        // Restore original console output
        System.setOut(originalOut);

        // Assert that the printed output contains information about the added product
        assertTrue(outContent.toString().contains("Product type: Clothing"));
        assertTrue(outContent.toString().contains("T-Shirt"));
    }

    /**
     * Test case for saving the products in the manager's product list to a file.
     */
    @Test
    public void testSaveProducts() {
        Clothing clothingItem = new Clothing("C001", "T-Shirt", 10, 19.99, "M", "Blue");
        manager.addProduct(clothingItem);

        // Redirect console output to check printed messages
        System.setOut(new PrintStream(outContent));

        manager.saveProducts("TestProductData.txt");

        // Restore original console output
        System.setOut(originalOut);

        assertTrue(outContent.toString().contains("Products saved to file: TestProductData.txt")); // Assert that the save confirmation message is printed to the console
    }
}

// REFERENCES

/* Java Testing
* JUnit - https://www.oracle.com/technical-resources/articles/adf/essentials-part5.html , https://www.geeksforgeeks.org/junit-5-how-to-write-parameterized-tests/?ref=header_search
*/