package WestminsterShoppingCenter;

/**
 * A class representing a user in the Westminster Shopping Center system.
 * Here a user is identified as one who shops at the Westminster Shopping Center system.
 * Users have a username, password, and a flag indicating whether it's their first-time purchase.
 */
public class User {
    //Attributes of a user
    private String username;
    private String password;
    private boolean isFirstTimePurchase;

    /**
     * Constructor to initialize a User with a username and password.
     * The attributes are initialized through the parameters passed.
     * By default, isFirstTimePurchase is set to false.
     */

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isFirstTimePurchase = false;
    }

    // Getter and setter methods to access and modify the attributes of a user.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstTimePurchase() {
        return isFirstTimePurchase;
    }

    public void setFirstTimePurchase(boolean isFirstTimePurchase) {
        this.isFirstTimePurchase = isFirstTimePurchase;
    }
}
