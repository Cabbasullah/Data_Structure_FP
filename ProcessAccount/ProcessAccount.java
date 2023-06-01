package ProcessAccount;
import java.util.*;
import java.io.*;
import UserDetails.UserDetails;


public class ProcessAccount {
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    LinkedList<UserDetails> userlist = new LinkedList<>();

    Scanner scn = new Scanner(System.in);

    public void createAccount() {
      
        String cn = "CREATE A NEW ACCOUNT";
        int width = 80; // the total width of the console screen
        int leftPadding = (width - cn.length()) / 5;
        System.out.print(String.format(ANSI_BLACK+"%" + leftPadding + "s%s%n", "", cn, ANSI_RESET));
         
        String un = "User Name: ";
         int nwidth = 80; // the total width of the console screen
        int nleftPadding = (nwidth - un.length()) / 5;
        System.out.print(String.format("%" + nleftPadding + "s%s", "", un));
        String username = scn.nextLine();
        
        String np = "New Password: ";
        System.out.print(String.format("%" + nleftPadding + "s%s", "", np));
       
        Object password = scn.next();

        UserDetails userdts = new UserDetails(username, password);

        try {
            BufferedWriter bwriter = new BufferedWriter(new FileWriter("newfile.txt", true));
            bwriter.write(String.format("Name: %-8s Password: %-8s \n", userdts.getuserName(), userdts.getPassword()));
            bwriter.close();
            System.out.println(ANSI_GREEN+"You have successfully created a New Account \n"+ANSI_RESET);
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LinkedList<UserDetails> LoadContent() {

        try {
            BufferedReader breader = new BufferedReader(new FileReader("newfile.txt"));
            String line = breader.readLine();
            while (line != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length >= 4) {
                    String name = tokens[1];
                    Object password = tokens[3];

                    UserDetails newudetails = new UserDetails(name, password);
                    userlist.add(newudetails);
                }
                line = breader.readLine();
            }
            breader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userlist;
    }

    public void PrintDetails() {
        for (UserDetails newlist : userlist) {
            System.out.printf("Name: %-7s %-8s\n", newlist.getuserName(), newlist.getPassword());
        }
    }
}

