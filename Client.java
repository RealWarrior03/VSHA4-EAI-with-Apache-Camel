import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;



public class Client {
    String clientFirstName;
    int clientID;
    public Client(String clientFirstName,String clientLastName){
        this.clientFirstName=clientFirstName;
        this.clientID = fetchClientID();
    }

    private int fetchClientID(){
        //TODO
        return 0;
    }

    public boolean placeOrder(int numOfSurfboards,int numOfBathingsuits){
        //TODO
        return  true;
    }



    public boolean isValidOrder(String[] order){
        if(order.length!=2){
            return false;
        }
        return (isValidNumber(order[0]) && isValidNumber(order[0]))
                && !((Integer.parseInt(order[0]) == 0) && (Integer.parseInt(order[1]) == 0));// Checks if both numbers are valid(see isValidNumber) and if not both numbers are zero)
    }

    public boolean isValidNumber(String s){ // Checks if the give string is a number and non negative
        try {
            int sAsInt = Integer.parseInt(s);
            if(sAsInt<0){
                return false;
            }
        }
        catch (NumberFormatException numberFormatException){
            return false;
        }
        return true;
    }

    public boolean isValidName(String[] name){
       return name.length!=2;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String clientFirstName = "ERROR";
            String clientLastName= "ERROR";
            boolean nameValid = false;
            do{
                System.out.println("Enter the your name(Firsname Lastname):");
                String[] readName = reader.readLine().split(" ");
                if(readName.length==2){
                    nameValid=true;
                    clientFirstName = readName[0];
                    clientLastName = readName[1];
                }
                else{
                    System.out.println("Not a valid name. Try again");
                }
            }while(!nameValid);

            Client thisClient = new Client(clientFirstName,clientLastName);

            boolean running = true;
            while(running) {
                System.out.println("Enter order in Format: numberOfSurfboards numOfBathingsuits");
                String[] order = reader.readLine().split(" ");

                if(thisClient.isValidOrder(order)){
                    if(thisClient.placeOrder(Integer.parseInt(order[0]),Integer.parseInt(order[1]))){
                        System.out.println("Order sucessfully placed");
                    }
                    else{
                        System.out.println("Order not placed. Try again");
                    }
                }
                else{
                    System.out.println("Not a valid order! Try again");
                }

            }

        } catch (IOException ex) {
            System.out.println("Client has crashed");
        }

    }
}
