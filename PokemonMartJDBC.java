/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonmartjdbc;
import java.sql.*;
import java.util.Scanner;
import static pokemonmartjdbc.Customer.stmt;

/**
 *
 * @author jalex_000
 */


public class PokemonMartJDBC {
    
   static Scanner sc = new Scanner (System.in);
   static Connection conn = null;
   static Statement stmt = null;
   
   public static void main(String [] args) {
       
       Scanner sc = new Scanner(System.in);
        
        int menu = 0;
        //PreparedStatement pstmt = null;
        //String sql = null;
        
        //String inputID, inputFName, inputLName, inputPhone, inputEmail, inputZipCode;
        
        try {
        while(true) {                
            System.out.println("1) Add Customer" + "\n" + "2) Remove Customer" + "\n" + "3) Modify Customer" + "\n" + "4) Search for Customer"
                    + "\n5) Quit");                    
            menu = sc.nextInt();
        
            switch(menu) {
                case 1:
                    Customer.AddCustomer(conn);                
                break;
                case 2: 
                    Customer.RemoveCustomer(conn);                
                break;
                case 3: 
                    Customer.ModifyCustomer(conn);
                break;
                case 4: 
                    Customer.SearchCustomer(conn);
                break;
                case 5:
                    System.exit(0);
                break;
        }//end switch
        }//end while
        }catch(SQLException sqle) {
                sqle.printStackTrace();
        }finally{
            try {
           if(stmt != null) {
               stmt.close();
           }
           
           if(conn != null) {
               conn.close();
           }
           }catch(SQLException sqle2) {
               sqle2.printStackTrace();
           }        
        }//end finally
    }// end main
}//end class
