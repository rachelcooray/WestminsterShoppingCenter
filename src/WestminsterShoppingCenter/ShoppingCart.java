package WestminsterShoppingCenter;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> shoppingList = new ArrayList<>();

    public void addItem(Product product) {

        shoppingList.add(product);
    }
    public void removeItem(Product product) {

        shoppingList.remove(product);
    }
    public double calculateTotalCost() {
        double totalCost = 0;
        for (Product product : shoppingList) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }
}
