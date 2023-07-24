package Client;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class Client {


    public static boolean isValidOrder(String[] order) {
        if (order.length != 5) {
            return false;
        }
        return (isValidNumber(order[2]) && isValidNumber(order[3]))
                && !((Integer.parseInt(order[2]) == 0) && (Integer.parseInt(order[3]) == 0));// Checks if both numbers are valid(see isValidNumber) and if not both numbers are zero)
    }

    public static boolean isValidNumber(String s) { // Checks if the give string is a number and non negative
        try {
            int sAsInt = Integer.parseInt(s);
            if (sAsInt < 0) {
                return false;
            }
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }

    public static String getOrderFromTerminal() {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the your order in the format Firstname Lastname NumberofSurfboeards NumberOfDivingSuits CustomerID");
            String[] order;
            try {
                order = reader.readLine().split(" ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (isValidOrder(order)) {
                return String.join(", ", order);
            } else {
                System.out.println("Not a valid order. Try again");
            }
        }
    }

    public static void main(String[] args) {

    }
}
