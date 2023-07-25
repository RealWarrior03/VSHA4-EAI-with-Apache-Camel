package OrderMessage;

import java.io.Serializable;
import java.util.Objects;

public class OrderMessage implements Serializable {
    private int customerID;
    private String firstName;
    private String lastName;
    private int overallItems;
    private int numberOfDivingSuits;
    private int numberOfSurfboards;
    private boolean valid;
    private String validationResult;
    private boolean resSysWasHere;
    private int orderID;

    public OrderMessage(String[] params) throws Exception {
        if(params.length == 5){
            this.customerID = Integer.parseInt(params[0]);
            this.firstName = params[1];
            this.lastName = params[2];
            this.numberOfDivingSuits = Integer.parseInt(params[3]);
            this.numberOfSurfboards = Integer.parseInt(params[4]);


            this.overallItems = -1;
            this.valid = false;
            this.validationResult = null;
            this.orderID = -1;
            resSysWasHere = false;
        }else if(params.length == 10){
            this.customerID = Integer.parseInt(params[0]);
            this.firstName = params[1];
            this.lastName = params[2];
            this.numberOfDivingSuits = Integer.parseInt(params[3]);
            this.numberOfSurfboards = Integer.parseInt(params[4]);
            this.overallItems = Integer.parseInt(params[5]);
            this.valid = StringToBool(params[6]);
            this.validationResult = params[7];
            this.orderID = Integer.parseInt(params[8]);
            resSysWasHere = StringToBool(params[9]);
        }else{
            throw new Exception("Error while constructing OrderMessage");
        }
    }

    public OrderMessage(int customerID, String firstName, String lastName, int numberOfDivingSuits, int numberOfSurfboards) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfDivingSuits = numberOfDivingSuits;
        this.numberOfSurfboards = numberOfSurfboards;

        this.orderID = -1;
        this.overallItems = -1;
        this.valid = false;
        this.validationResult = null;
        resSysWasHere = false;
    }

    public OrderMessage(int customerID, String firstName, String lastName, int overallItems, int numberOfDivingSuits, int numberOfSurfboards, boolean valid, String validationResult) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.overallItems = overallItems;
        this.numberOfDivingSuits = numberOfDivingSuits;
        this.numberOfSurfboards = numberOfSurfboards;
        this.orderID = -1;
        this.valid = valid;
        this.validationResult = validationResult;
        resSysWasHere = false;
    }


    public String[] getAllValuesAsStringArray(){
        String[] values = new String[10];

        values[0] = Integer.toString(this.customerID);
        values[1] = this.firstName;
        values[2] = this.lastName;
        values[3] = Integer.toString(this.numberOfDivingSuits);
        values[4] = Integer.toString(this.numberOfSurfboards);
        values[5] = Integer.toString(this.overallItems);
        values[6] = BoolToString(this.valid);
        values[7] = this.validationResult;
        values[8] = Integer.toString(this.orderID);
        values[9] = BoolToString(this.resSysWasHere);

        return  values;
    }

    private String BoolToString(boolean bool){
        if (bool){
            return "true";
        }else{
            return "false";
        }
    }

    private boolean StringToBool(String string){
        return Objects.equals(string, "true");
    }

    public boolean isResSysWasHere() {
        return resSysWasHere;
    }

    public void setResSysWasHere(boolean resSysWasHere) {
        this.resSysWasHere = resSysWasHere;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getOverallItems() {
        return overallItems;
    }

    public void setOverallItems(int overAllItems) {
        this.overallItems = overAllItems;
    }

    public int getNumberOfDivingSuits() {
        return numberOfDivingSuits;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setNumberOfDivingSuits(int numberOfDivingSuits) {
        this.numberOfDivingSuits = numberOfDivingSuits;
    }

    public int getNumberOfSurfboards() {
        return numberOfSurfboards;
    }

    public void setNumberOfSurfboards(int numberOfSurfboards) {
        this.numberOfSurfboards = numberOfSurfboards;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public String toString(){
        String validationResultPlaceholder = "null";
        String overallItemsPlaceholder = "null";

        if(validationResult != null){
            validationResultPlaceholder = validationResult;
        }
        if(overallItems != -1){
            overallItemsPlaceholder = Integer.toString(overallItems);
        }

        return (Integer.toString(customerID) + ", " + firstName + ", " + lastName + ", DivingSuits: " + Integer.toString(numberOfDivingSuits) + ", Surfboards: " + Integer.toString(numberOfSurfboards) + ", Valid: "
                + String.valueOf(valid) + ", validation Result: " + validationResultPlaceholder+ ", OrderID: " + Integer.toString(orderID));
    }
}
