package OrderMessage;

public class OrderMessage {
    private int customerID;
    private String firstName;
    private String lastName;
    private int overallItems;
    private int numberOfDivingSuits;
    private int numberOfSurfboards;
    private boolean valid;
    private String validationResult;


    public OrderMessage(int customerID, String firstName, String lastName, int numberOfDivingSuits, int numberOfSurfboards) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfDivingSuits = numberOfDivingSuits;
        this.numberOfSurfboards = numberOfSurfboards;

        this.overallItems = -1;
        this.valid = false;
        this.validationResult = null;
    }

    public OrderMessage(int customerID, String firstName, String lastName, int overallItems, int numberOfDivingSuits, int numberOfSurfboards, boolean valid, String validationResult) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.overallItems = overallItems;
        this.numberOfDivingSuits = numberOfDivingSuits;
        this.numberOfSurfboards = numberOfSurfboards;
        this.valid = valid;
        this.validationResult = validationResult;
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

    public String isValidationResult() {
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

        return (Integer.toString(customerID) + ", " + firstName + ", " + lastName + ", " + overallItemsPlaceholder
                + ", " + Integer.toString(numberOfDivingSuits) + ", " + Integer.toString(numberOfSurfboards) + ", "
                + String.valueOf(valid) + ", " + validationResultPlaceholder);
    }
}
