package Database;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class Database {
    //HashMap<Integer, Integer> customerHashMap = new HashMap<>();
    HashMap<String, Integer> inventoryHashMap = new HashMap<>();

    public Database() {
        inventoryHashMap.put("nmbOfDivingsuits", 100);
        inventoryHashMap.put("nmbOfSurfboards", 100);
    }

    // DEPRECATED
    /*public void newCustomer(int customerID) {
        customerHashMap.put(customerID, 500);
    }

    public int getCustomerCreditStanding(int customerID) {
        return customerHashMap.get(customerID);
    }

    public void updateCustomerCreditStanding(int customerID, int amount) {
        int oldStanding = customerHashMap.get(customerID);
        int newStanding = oldStanding - amount;
        customerHashMap.put(customerID, newStanding);
        //System.out.println("Hier der print für Tobi, irgendwas ist passiert beim Update des Kontostands. (Ziemlich sicher ist alles gut gegangen)")
    }*/

    public void sellSurfboard(int amount) {
        int newAmount = inventoryHashMap.get("nmbOfSurfboards");
        newAmount -= amount;
        inventoryHashMap.put("nmbOfSurfboards", newAmount);
    }

    public void sellDivingsuit(int amount) {
        int newAmount = inventoryHashMap.get("nmbOfDivingsuits");
        newAmount -= amount;
        inventoryHashMap.put("nmbOfDivingsuits", newAmount);
    }
}
