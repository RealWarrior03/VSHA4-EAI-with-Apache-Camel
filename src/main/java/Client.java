import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class Client {
    String clientFirstName;
    int clientID;
    String orderFrom;

    public Client(String clientFirstName, String clientLastName,String orderFrom) {
        this.clientFirstName = clientFirstName;
        this.orderFrom = orderFrom;
        init();
    }


    private void init() {
        //TODO
    }

    public boolean placeOrder(int numOfSurfboards, int numOfBathingsuits) {

        if(Objects.equals(orderFrom, "Web")){

        }

        //TODO
        return true;
    }

    public boolean isValidOrder(String[] order) {
        if (order.length != 2) {
            return false;
        }
        return (isValidNumber(order[0]) && isValidNumber(order[0]))
                && !((Integer.parseInt(order[0]) == 0) && (Integer.parseInt(order[1]) == 0));// Checks if both numbers are valid(see isValidNumber) and if not both numbers are zero)
    }

    public boolean isValidNumber(String s) { // Checks if the give string is a number and non negative
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


    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String clientFirstName = "ERROR";
            String clientLastName = "ERROR";
            boolean nameValid = false;
            do {
                System.out.println("Enter the your name(Firsname Lastname):");
                String[] readName = reader.readLine().split(" ");
                if (readName.length == 2) {
                    nameValid = true;
                    clientFirstName = readName[0];
                    clientLastName = readName[1];
                } else {
                    System.out.println("Not a valid name. Try again");
                }
            } while (!nameValid);

            boolean orderFromIsValid = false;
            String orderFrom = "ERROR";
            do {
                System.out.println("Where do you want to order from? Web or CallCenter");
                String readOrderFrom = reader.readLine();
                if (Objects.equals(readOrderFrom, "Web") || Objects.equals(readOrderFrom, "CallCenter")) {
                    orderFromIsValid = true;
                    orderFrom = readOrderFrom;

                } else {
                    System.out.println("Not a valid place to order from. Try again");
                }
            } while (!orderFromIsValid);



            Client thisClient = new Client(clientFirstName, clientLastName,orderFrom);

            boolean running = true;
            while (running) {
                System.out.println("Enter order in Format: numberOfSurfboards numOfBathingsuits");
                String[] order = reader.readLine().split(" ");

                if (thisClient.isValidOrder(order)) {
                    if (thisClient.placeOrder(Integer.parseInt(order[0]), Integer.parseInt(order[1]))) {
                        System.out.println("Order sucessfully placed");
                    } else {
                        System.out.println("Order not placed. Try again");
                    }
                } else {
                    System.out.println("Not a valid order! Try again");
                }

            }

        } catch (IOException ex) {
            System.out.println("Client has crashed");
        }

    }
}
