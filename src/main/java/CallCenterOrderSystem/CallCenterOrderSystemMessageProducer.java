package CallCenterOrderSystem;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;


public class CallCenterOrderSystemMessageProducer {
    private int fileCounter;

    public static void main(String[] args) throws Exception {
        List<String> allorders = new ArrayList<String>();
        List<String> orders = new ArrayList<String>();
        allorders.add("10, Justus Jonas, 1, 1");
        allorders.add("11, Peter Shaw, 5, 3");
        allorders.add("12, Bob Andrews, 4, 3");
        allorders.add("13, John Doe, 2, 1");
        allorders.add("14, Bames Jond, 4, 2");


        String fileName = "src/main/inputfiles/callcenterordersysteminput.txt";

        while (true) {
            //shuffle List and pick 3 random elements
            Collections.shuffle(allorders);
            orders = allorders.subList(0, 5);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                // Iterate through the list and write each string as a line in the file
                for (String line : orders) {
                    writer.write(line);
                    writer.newLine(); // Add a newline character to separate each line
                }
                System.out.println("File created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(120000);

        }

    }


}
