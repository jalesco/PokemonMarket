
package seniorproject;

/**
 *
 * @author Richard
 * TM is an item that teaches a Pokemon a move. TM inherits from Product.
 */
public class TM extends Product {
    private final String mAbility;
    
    /**
     * Constructor for a TM
     * @param name Name of TM
     * @param ID ID
     * @param description Description
     * @param price Price
     * @param stock Amount in stock
     * @param ability name of the ability (not the TM!)
     */
    public TM(String name, String ID, String description, int price, int stock, String ability) {
        super(name, ID, description, price, stock);
        mAbility = ability;
    }
    
    /**
     * Getter for a TM's ability
     * @return ability that TM teaches
     */
    public String getAbility () {
        return mAbility;
    }    
    
}
