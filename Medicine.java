
package seniorproject;

/**
 *
 * @author Richard
 * Medicine is an item that improves a Pokemons stat. Inherits from Product.
 */
public class Medicine extends Product {
    private final String mStat;
    
    /**
     * Constructor for a medicine 
     * @param name name
     * @param ID ID
     * @param description Description
     * @param price Price
     * @param stock Amount in stock
     * @param type the stat that is raised
     */
    public Medicine(String name, String ID, String description, int price, int stock, String type) {
        super(name, ID, description, price, stock);
        mStat = type;
    }
    
    /**
     * Getter for a medicines stat
     * @return stat that medicine improves
     */
    public String getStat () {
        return mStat;
    }
    
}
