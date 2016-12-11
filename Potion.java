
package seniorproject;

/**
 *
 * @author Richard
 * A potion is used to heal Pokemon. Inherits from Product.
 */
public class Potion extends Product {
    private final int mAmount;
    
    /**
     * Constructor for a Potion
     * @param name Name
     * @param ID ID
     * @param description Description
     * @param price Price
     * @param stock Amount in stock
     * @param amount Amount healed
     */
    public Potion(String name, String ID, String description, int price, int stock, int amount) {
        super(name, ID, description, price, stock);
        mAmount = amount;
    }
    
    /**
     * Getter for amount this Potion heals
     * @return amount this Potion heals
     */
    public int getAmount () {
        return mAmount;
    }
    
}
