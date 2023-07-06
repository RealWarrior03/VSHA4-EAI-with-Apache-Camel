package Database;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class Database {
    HashMap<Integer, Integer> customerHashMap = new HashMap<>();
    HashMap<String, Integer> inventoryHashMap = new HashMap<>();

    public Database() {
        //inventoryHashMap.put("nmbof")
    }

    public void newCustomer(int customerID) {
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
    }
}
