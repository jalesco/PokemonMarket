
package seniorproject;

/**
 *
 * @author Richard
 * Pokeballs are used to catch pokemon. Inherits from Product.
 */
public class Pokeball extends Product {
    private final int mCatchRate;
    
    /**
     * Constructor for a Pokeball
     * @param name Name
     * @param ID ID
     * @param description Description
     * @param price Price
     * @param stock Amount in stock
     * @param catchrate The rate (as an int) that it catches Pokemon with
     */
    public Pokeball(String name, String ID, String description, int price, int stock, int catchrate) {
        super(name, ID, description, price, stock);
        mCatchRate = catchrate;
    }
    
    /**
     * Getter for a Pokeballs catch rate
     * @return The rate (as an int) that this Pokeball catches Pokemon with
     */
    public int getRate () {
        return mCatchRate;
    }
}
