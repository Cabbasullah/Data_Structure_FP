package SearchContent;

import java.util.*;
import java.io.*;

public class SearchContent {
    Scanner scn = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public void searchedRecord(String category) {
        System.out.println("Number of records to search: ");
        int n = scn.nextInt();
        scn.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nSearch by category name# " + (i + 1) + ": ");
            String evn = scn.nextLine();
            /* String sevn=((String)evn).trim(); */
            boolean found = false;
            try {
                BufferedReader breader = new BufferedReader(new FileReader("financial_records.txt"));
                String line = breader.readLine();
                while (line != null) {
                    if (line.startsWith(evn) && !line.isEmpty()) {
                        System.out.println(line);
                        found = true;
                        line = breader.readLine();
                        while (line != null && !line.isEmpty()) {
                            System.out.println(line);
                            line = breader.readLine();
                        }
                        break;

                    }
                    line = breader.readLine();
                }
                breader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!found) {
                System.out.println("Event name not found, please search for it again");
            }
        }

    }
}